package com.greentea.webcontroller.server.exception;

public class WebServerInterruptedException extends WebServerException {
    public WebServerInterruptedException() {
        super();
    }

    public WebServerInterruptedException(String message) {
        super(message);
    }

    public WebServerInterruptedException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebServerInterruptedException(Throwable cause) {
        super(cause);
    }
}