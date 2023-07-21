package com.example.collaborativeeditorwebsocketbackend.entity;

import java.util.Arrays;
import java.util.Vector;

public final class SharedFile {
    private static SharedFile instance;
    public Vector<Character> textContent;

    public int version;

    public Vector<Operation> operations;

    public String currentUserId;

    private SharedFile(Vector<Character> text) {
        this.textContent = text;
        this.version = 0;
        this.operations = new Vector<>();
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

    public synchronized void setText(Operation operation) {
        System.out.println("operation inside setText: " + operation);
        operation.getiOperation().doOperation(textContent, operation);
        System.out.println(textContent);

        this.operations.add(operation);
        this.version++;
        this.currentUserId = operation.getUserId();

        System.out.println("operation completed");
    }
}
