package com.greentea.webcontroller.server.exception;

public class WebServerStartException extends WebServerException {
    public WebServerStartException() {
        super();
    }

    public WebServerStartException(String message) {
        super(message);
    }

    public WebServerStartException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebServerStartException(Throwable cause) {
        super(cause);
    }
}