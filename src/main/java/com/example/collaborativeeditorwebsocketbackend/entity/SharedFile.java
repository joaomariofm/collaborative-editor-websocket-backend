package com.example.collaborativeeditorwebsocketbackend.entity;

import com.example.collaborativeeditorwebsocketbackend.utils.TransformOperationUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public final class SharedFile {
    private static SharedFile instance;
    public List<Character> textContent;

    public int version;

    public List<Operation> operations;

    public String currentUserId;

    private SharedFile(List<Character> text) {
        this.textContent = text;
        this.version = 0;
        this.operations = new ArrayList<>();
    }

    public synchronized static SharedFile init() {
        List<Character> text = new ArrayList<>();

        System.out.println(text.toString());
        if (instance == null) {
            instance = new SharedFile(text);
        }
        return instance;
    }

    public static synchronized SharedFile getInstance() {
        return instance;
    }


    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public synchronized void setText(Operation operation, SimpMessagingTemplate messagingTemplate) {
        System.out.println("operation inside setText: " + operation);
        Operation operationTreatedWithOT = TransformOperationUtils.transformOperation(operation, operations);
        var textContentCopy = List.copyOf(textContent);

        if(operationTreatedWithOT.getType().equals("insert")) {
            textContent.add(operationTreatedWithOT.getPosition(), operationTreatedWithOT.getValue()[0]);
        }else {
            textContent.remove(operationTreatedWithOT.getPosition());
        }
        System.out.println(textContent);

        this.operations.add(operationTreatedWithOT);
        System.out.println(this.operations);
        this.version++;
        operation.setVersionText(textContentCopy);
        messagingTemplate.convertAndSend("/topic/versionUpdate", operation);
    }
}
