package com.example.collaborativeeditorwebsocketbackend.entity;

import java.util.Arrays;
import java.util.Vector;

public final class SharedFile {
    private static SharedFile instance;
    public Vector<Character> text;

    public int version;

    public Vector<Operation> history;

    public String currentUserId;

    private SharedFile(Vector<Character> text) {
        this.text = text;
        this.version = 0;
        this.history = new Vector<>();
    }

    public synchronized static SharedFile init() {
        Vector<Character> text = new Vector<>(Arrays.asList('E', 'd', 'i', 't', 'o', 'r'));

        System.out.println(text.toString());
        if (instance == null) {
            instance = new SharedFile(text);
        }
        return instance;
    }

    public synchronized static SharedFile getInstance() {
        return instance;
    }

    public synchronized Vector<Character> getText() {
        return this.text;
    }

    public synchronized void setText(Operation operation) {
        System.out.println("operation inside setText: " + operation);
        operation.getiOperation().doOperation(text, operation);
        System.out.println(text);

        this.history.add(operation);
        this.version++;

        // this.currentUserId
    }
}
