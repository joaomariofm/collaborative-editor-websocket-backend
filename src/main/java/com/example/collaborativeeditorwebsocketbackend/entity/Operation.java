package com.example.collaborativeeditorwebsocketbackend.entity;

import java.util.Arrays;

public class Operation {
    private String type;
    private char[] value;
    private int position;
    private IOperation iOperation;

    public String getType() {
        return type;
    }

    public char[] getValue() {
        return value;
    }

    public int getPosition() {
        return position;
    }

    public IOperation getiOperation() {
        return iOperation;
    }

    public void setiOperation(IOperation iOperation) {
        this.iOperation = iOperation;
    }

    public static IOperation getOperationInterface(String operationType) {
        return switch (operationType) {
            case "insert" -> ((text, operation) -> text.add(operation.getValue()[0]));
            case "delete" -> ((text, operation) -> text.remove(operation.getPosition()));
            default -> null;
        };
    }

    @Override
    public String toString() {
        return "Operation{" +
                "type='" + type + '\'' +
                ", value=" + Arrays.toString(value) +
                ", position=" + position +
                ", iOperation=" + iOperation +
                '}';
    }
}
