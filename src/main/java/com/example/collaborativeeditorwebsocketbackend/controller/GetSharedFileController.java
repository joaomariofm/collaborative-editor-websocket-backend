package com.example.collaborativeeditorwebsocketbackend.controller;

import com.example.collaborativeeditorwebsocketbackend.entity.SharedFile;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.Vector;

@Controller
public class GetSharedFileController {
    @SubscribeMapping("/getSharedFile")
    public SharedFile getSharedFile() throws Exception {
        return SharedFile.getInstance();
    }
}
