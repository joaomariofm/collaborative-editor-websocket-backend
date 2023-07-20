package com.example.collaborativeeditorwebsocketbackend.service;

import com.example.collaborativeeditorwebsocketbackend.entity.Operation;
import com.example.collaborativeeditorwebsocketbackend.entity.SharedFile;

public class DoOperationRunnable implements Runnable{
    private final Operation operation;

    public DoOperationRunnable(Operation operation) {
        this.operation = operation;
    }

    @Override
    public void run() {
        SharedFile.getInstance().setText(operation);
    }
}
