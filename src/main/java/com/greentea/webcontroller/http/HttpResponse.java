package com.greentea.webcontroller.http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class HttpResponse {
    protected static final String NEW_LINE = "\r\n";
    protected static final String HEADER_DELIMITER = ": ";

    protected final Map<String, String> headers;
    protected String body;
    protected int statusCode;
    protected String status;

    public HttpResponse() {
        this.headers = new HashMap<>();
        addHeader("Connection", "Close");
        this.body = "";
        this.statusCode = 200;
        this.status = "Ok";
    }

    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public void addHeaders(Map<String, String> headers) {
        this.headers.putAll(headers);
    }

    public byte[] getBytes() {
        return getMessage().getBytes();
    }

    public String getMessage() {
        StringBuilder builder = new StringBuilder();
        buildHttpHeader(builder);
        for (var entry : getHeaders().entrySet()) {
            buildHeader(builder, entry.getKey(), entry.getValue());
        }
        return builder.append(NEW_LINE).append(getBody()).toString();
    }

    private void buildHttpHeader(StringBuilder builder) {
        builder.append("HTTP/").append(getHttpVersion()).append(" ").append(getStatusAndCode()).append(NEW_LINE);
    }

    private void buildHeader(StringBuilder builder, String name, String value) {
        builder.append(name).append(HEADER_DELIMITER).append(value).append(NEW_LINE);
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.headers.put("Content-Length", String.valueOf(body.length()));
        this.body = body;
    }

    public abstract String getHttpVersion();

    public String getStatusAndCode() {
        return getStatus() + " " + getStatusCode();
    }

    public String getStatus() {
        return this.status;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public boolean hasHeader(String header) {
        return this.getHeader(header) != null;
    }

    public String getHeader(String header) {
        return this.headers.get(header);
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(this.headers);
    }

    public void setStatus(int code, String status) {
        this.status = status;
        this.statusCode = code;
    }
}