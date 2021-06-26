package com.greentea.webcontroller.server.exception;

public class WebServerResponseException extends WebServerException {
    public WebServerResponseException() {
        super();
    }

    public WebServerResponseException(String message) {
        super(message);
    }

    public WebServerResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebServerResponseException(Throwable cause) {
        super(cause);
    }
}