package com.example.controller;

import com.example.entity.SharedFile;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.Vector;

@Controller
public class GetSharedFileController {
    @SubscribeMapping("/getSharedFile")
    public Vector<Character> getSharedFile() throws Exception {
        return SharedFile.getInstance().getText();
    }
}
