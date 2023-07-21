package com.example.collaborativeeditorwebsocketbackend.utils;


public class CalculateNewCursorPositionUtil {
    public static int calculateNewCursorPosition(int cursorPosition, int textLength, int newTextLength) {
        var diff = newTextLength - textLength;
        return cursorPosition + diff;
    }
}
