package com.example.collaborativeeditorwebsocketbackend.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public final class SharedFile {
    private static SharedFile instace;
    public Vector<Character> text;

    private SharedFile(Vector<Character> text) {
        this.text = text;
    }

    public synchronized static SharedFile init() {
        Vector<Character> text = new Vector<>(Arrays.asList('E', 'd', 'i', 't', 'o', 'r'));

        System.out.println(text.toString());
        if (instace == null) {
            instace = new SharedFile(text);
        }
        return instace;
    }

    public synchronized static SharedFile getInstance() {
        return instace;
    }

    public synchronized Vector<Character> getText() {
        return this.text;
    }

    public synchronized void setText(Operation operation) {
        System.out.println("operation inside setText: " + operation);
        operation.getiOperation().doOperation(text, operation);
        System.out.println(text);
    }
}
