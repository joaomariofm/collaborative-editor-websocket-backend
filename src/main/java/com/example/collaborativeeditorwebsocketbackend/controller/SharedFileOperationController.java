package com.example.collaborativeeditorwebsocketbackend.controller;

import com.example.collaborativeeditorwebsocketbackend.entity.Operation;
import com.example.collaborativeeditorwebsocketbackend.service.DoOperationService;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class SharedFileOperationController {
    private final DoOperationService operationService;

    public SharedFileOperationController(DoOperationService operationService) {
        this.operationService = operationService;
    }

    @MessageMapping("/doOperation")
    public void doOperation(Operation operation) {
        operationService.handleMultipleOperation(operation);
        System.out.println("operation note after execution: " + operation.getType() + " " + operation.getPosition() + " " + operation.getValue()[0]);
    }
}
