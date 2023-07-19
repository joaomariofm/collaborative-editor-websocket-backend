package com.example.controller;

import com.example.entity.Message;
import com.example.entity.SharedFile;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class TestingController {
    @MessageMapping("/test")
    public void testingMessage(Message message) {
        SharedFile sharedFile = SharedFile.getInstance();
        System.out.println("the text from the sharedFile" + sharedFile.text);
        System.out.println("the message sent from the client: " + message.getMessage());
        System.out.println("the number sent from the client: " + message.getNumber());
    }
}
