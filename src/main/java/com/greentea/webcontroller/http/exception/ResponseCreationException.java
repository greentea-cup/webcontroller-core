package com.greentea.webcontroller.http.exception;

public class ResponseCreationException extends Exception {
    public ResponseCreationException() {
        super();
    }

    public ResponseCreationException(String message) {
        super(message);
    }

    public ResponseCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResponseCreationException(Throwable cause) {
        super(cause);
    }
}