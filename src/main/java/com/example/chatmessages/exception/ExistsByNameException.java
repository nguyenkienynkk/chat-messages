package com.example.chatmessages.exception;

public class ExistsByNameException extends RuntimeException {

    public ExistsByNameException() {
        super();
    }

    public ExistsByNameException(String message) {
        super(message);
    }

    public ExistsByNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistsByNameException(Throwable cause) {
        super(cause);
    }

}
