package com.example.collaborativeeditorwebsocketbackend.service;

import com.example.collaborativeeditorwebsocketbackend.entity.Operation;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class DoOperationService {
    private final SimpMessagingTemplate messagingTemplate;

    public DoOperationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void handleMultipleOperation(Operation operation)  {
        Thread thread = new Thread(new DoOperationRunnable(operation, messagingTemplate));
        thread.start();
        //thread.join();
    }
}
