package com.greentea.webcontroller.server.exception;

public class WebServerException extends Exception {
    public WebServerException() {
        super();
    }

    public WebServerException(String message) {
        super(message);
    }

    public WebServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebServerException(Throwable cause) {
        super(cause);
    }
}