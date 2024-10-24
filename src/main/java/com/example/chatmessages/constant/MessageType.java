package com.example.chatmessages.constant;

public enum MessageType {
    TEXT,
    IMAGE,
    VIDEO;
    public static MessageType fromString(String type) {
        try {
            return MessageType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid message type: " + type);
        }
    }

}
