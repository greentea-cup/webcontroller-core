package com.greentea.webcontroller.server.exception;

public class WebServerCloseException extends WebServerException {
    public WebServerCloseException() {
        super();
    }

    public WebServerCloseException(String message) {
        super(message);
    }

    public WebServerCloseException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebServerCloseException(Throwable cause) {
        super(cause);
    }
}