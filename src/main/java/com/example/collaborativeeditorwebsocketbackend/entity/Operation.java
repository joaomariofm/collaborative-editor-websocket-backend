package com.example.collaborativeeditorwebsocketbackend.entity;

import java.util.Arrays;

public class Operation {
    private String type;
    private char[] value;
    private int position;
    private int version;
    private String userId;
    private IOperation iOperation;

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(char[] value) {
        this.value = value;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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
            case "insert" -> ((text, operation) -> text.add(operation.getPosition(), operation.getValue()[0]));
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
                ", version=" + version +
                ", userId='" + userId + '\'' +
                ", iOperation=" + iOperation +
                '}';
    }
}
