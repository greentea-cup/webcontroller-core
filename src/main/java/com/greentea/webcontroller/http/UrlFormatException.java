package com.greentea.webcontroller.http;

public class UrlFormatException extends Exception {
    public UrlFormatException(String message) {
        super(message);
    }

    public UrlFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}