package com.example.collaborativeeditorwebsocketbackend.service;

import com.example.collaborativeeditorwebsocketbackend.entity.Operation;
import org.springframework.stereotype.Service;

@Service
public class DoOperationService {

    public void handleMultipleOperation(Operation operation) throws InterruptedException {
        Thread thread = new Thread(new DoOperationRunnable(operation));
        thread.start();
        thread.join();
    }
}
