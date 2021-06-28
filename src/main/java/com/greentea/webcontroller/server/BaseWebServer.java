package com.greentea.webcontroller.server;

import com.greentea.webcontroller.http.BaseHttpResponse;
import com.greentea.webcontroller.http.HttpRequest;
import com.greentea.webcontroller.http.HttpResponse;
import com.greentea.webcontroller.http.exception.UrlFormatException;
import com.greentea.webcontroller.http.exception.RequestCreationException;
import com.greentea.webcontroller.server.exception.WebServerException;

public abstract class BaseWebServer extends AbstractWebServer {
    private final String host;
    private final int port;
    private final String httpVersion;

    public BaseWebServer() {
        this("localhost", 8080, "1.1");
    }

    public BaseWebServer(String host, int port, String httpVersion) {
        this.host = host;
        this.port = port;
        this.httpVersion = httpVersion;
    }

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public HttpRequest createRequest(String message) throws WebServerException, RequestCreationException {
        try {
            return new HttpRequest(message);
        }
        catch (UrlFormatException e) {
            throw new WebServerException(e);
        }
    }

    @Override
    public HttpResponse createResponse() {
        return new BaseHttpResponse(this.httpVersion);
    }

    @Override
    protected String getContentType() {
        return "text/plain; charset=utf-8";
    }

    @Override
    protected int getBufferSize() {
        return 256;
    }
}