package com.example.chatmessages.exception;

public class NotExistsException extends RuntimeException {

    public NotExistsException(String message) {
        super(message);
    }

    public NotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
