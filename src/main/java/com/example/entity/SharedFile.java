package com.example.entity;

import java.util.Vector;

public final class SharedFile {
    private static SharedFile instace;
    public Vector<Character> text;

    private SharedFile(Vector<Character> text) {
        this.text = text;
    }

    public synchronized static SharedFile init() {
        Vector<Character> text = new Vector<>();
        text.add('E');
        text.add('d');
        text.add('i');
        text.add('t');
        text.add('o');
        text.add('r');

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

    public void setText(Operation operation) {
        System.out.println("operation inside setText: " + operation);
    }
}
