package com.greentea.webcontroller.server.exception;

public class WebServerRunException extends WebServerException {
    public WebServerRunException() {
        super();
    }

    public WebServerRunException(String message) {
        super(message);
    }

    public WebServerRunException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebServerRunException(Throwable cause) {
        super(cause);
    }
}