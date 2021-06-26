package com.greentea.webcontroller.http;

public class BaseHttpResponse extends HttpResponse {
    private final String httpVersion;

    public BaseHttpResponse(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    @Override
    public String getHttpVersion() {
        return this.httpVersion;
    }
}