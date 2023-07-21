package com.example.collaborativeeditorwebsocketbackend.entity;

import java.util.Vector;

public class SharedFileSnapShot {
    public Vector<Character> textContent;

    public int version;

    public String currentUserId;

    public SharedFileSnapShot(Vector<Character> textContent, int version, String currentUserId) {
        this.textContent = textContent;
        this.version = version;
        this.currentUserId = currentUserId;
    }
}
