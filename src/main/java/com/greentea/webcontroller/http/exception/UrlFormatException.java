package com.greentea.webcontroller.http.exception;

public class UrlFormatException extends Exception {
    public UrlFormatException(String message) {
        super(message);
    }

    public UrlFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}