package com.example.collaborativeeditorwebsocketbackend.entity;

import com.example.collaborativeeditorwebsocketbackend.utils.TransformOperationUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

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
        ArrayList<Character> text = new ArrayList<>(Arrays.asList('E', 'd', 'i', 't', 'o', 'r'));

        System.out.println(text.toString());
        if (instance == null) {
            instance = new SharedFile(text);
        }
        return instance;
    }

    public synchronized static SharedFile getInstance() {
        return instance;
    }

    public synchronized List<Character> getText() {
        return this.textContent;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public synchronized void setText(Operation operation) {
        System.out.println("operation inside setText: " + operation);
        Operation operationTreatedWithOT = TransformOperationUtils.transformOperation(operation, operations);

        if(operationTreatedWithOT.getType().equals("insert")) {
            textContent.add(operationTreatedWithOT.getPosition(), operationTreatedWithOT.getValue()[0]);
        }else {
            textContent.remove(operationTreatedWithOT.getPosition());
        }
        System.out.println(textContent);

        this.operations.add(operationTreatedWithOT);
        this.version++;
    }
}
