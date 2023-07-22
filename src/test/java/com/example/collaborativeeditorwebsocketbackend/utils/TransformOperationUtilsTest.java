package com.example.collaborativeeditorwebsocketbackend.utils;

import static org.junit.jupiter.api.Assertions.*;

import com.example.collaborativeeditorwebsocketbackend.entity.Operation;
import org.junit.jupiter.api.Test;

class TransformOperationUtilsTest {
    @Test
    public void testCloneMethod() {
        Operation operation = new Operation("asd", new char[]{'a'}, 1, 1, "asd");
        var copy = Operation.createOperationClone(operation);
        System.out.println(operation);
        System.out.println(copy);
    }
}