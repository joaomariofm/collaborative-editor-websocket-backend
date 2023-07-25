package com.example.collaborativeeditorwebsocketbackend.service;

import com.example.collaborativeeditorwebsocketbackend.entity.Operation;
import com.example.collaborativeeditorwebsocketbackend.entity.SharedFile;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class DoOperationRunnable implements Runnable{
    private final Operation operation;
    private final SimpMessagingTemplate messagingTemplate;

    public DoOperationRunnable(Operation operation, SimpMessagingTemplate messagingTemplate) {
        this.operation = operation;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void run() {
        operation.setVersionText(SharedFile.getInstance().textContent);
        messagingTemplate.convertAndSend("/topic/versionUpdate", operation);
        SharedFile.getInstance().setText(operation);
    }
}
