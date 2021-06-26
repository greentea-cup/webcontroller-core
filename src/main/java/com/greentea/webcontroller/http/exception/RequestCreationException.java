package com.greentea.webcontroller.http.exception;

public class RequestCreationException extends Exception {
    public RequestCreationException() {
        super();
    }

    public RequestCreationException(String message) {
        super(message);
    }

    public RequestCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestCreationException(Throwable cause) {
        super(cause);
    }
}