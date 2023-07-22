package com.example.collaborativeeditorwebsocketbackend.entity;

import java.util.Arrays;

public class Operation {
    private String type;
    private char[] value;
    private int position;
    private int version;
    private String userId;

    public Operation(String type, char[] value, int position, int version, String userId) {
        this.type = type;
        this.value = value;
        this.position = position;
        this.version = version;
        this.userId = userId;
    }

    public Operation() {

    }

    public static Operation createOperationClone(Operation operation) {
        return new Operation(operation.getType(), operation.getValue(), operation.getPosition(), operation.getVersion(), operation.getUserId());
    }

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

    @Override
    public String toString() {
        return "Operation{" +
                "type='" + type + '\'' +
                ", value=" + Arrays.toString(value) +
                ", position=" + position +
                ", version=" + version +
                ", userId='" + userId + '\'' +
                '}';
    }
}
