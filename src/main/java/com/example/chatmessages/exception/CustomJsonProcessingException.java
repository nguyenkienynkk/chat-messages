package com.example.chatmessages.exception;

public class CustomJsonProcessingException extends RuntimeException {
    public CustomJsonProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
