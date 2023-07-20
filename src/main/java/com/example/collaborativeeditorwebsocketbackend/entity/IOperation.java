package com.example.collaborativeeditorwebsocketbackend.entity;

import java.util.Vector;

public interface IOperation {
    void doOperation(Vector<Character> text, Operation operation);
}
