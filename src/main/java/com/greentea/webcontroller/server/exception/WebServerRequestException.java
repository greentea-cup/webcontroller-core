package com.greentea.webcontroller.server.exception;

public class WebServerRequestException extends WebServerException {
    public WebServerRequestException() {
        super();
    }

    public WebServerRequestException(String message) {
        super(message);
    }

    public WebServerRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebServerRequestException(Throwable cause) {
        super(cause);
    }
}