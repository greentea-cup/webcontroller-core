package com.greentea.webcontroller.http;

import com.greentea.webcontroller.http.exception.RequestCreationException;
import com.greentea.webcontroller.http.exception.UrlFormatException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequest {
    protected static final String DELIMITER = "\r\n\r\n";
    protected static final String NEW_LINE = "\r\n";
    protected static final String HEADER_DELIMITER = ":";
    protected static final Pattern URL_ENCODED_CHAR = Pattern.compile("%[0-9a-f]{2}", Pattern.CASE_INSENSITIVE);

    protected final String message;

    protected final HttpMethod method;
    protected final String url;
    protected final Map<String, String> headers;
    protected final String body;

    public HttpRequest(String message) throws RequestCreationException, UrlFormatException {
        this(message, false);
    }

    public HttpRequest(String message, boolean useRawUrl) throws RequestCreationException, UrlFormatException {
        if (message == null || message.isBlank()) {
            throw new RequestCreationException("message cannot be empty");
        }
        this.message = message;
        String[] parts = message.split(DELIMITER);
        String head = parts[0];
        String[] headers = head.split(NEW_LINE);
        String[] firstLine = headers[0].split(" ");
        this.method = HttpMethod.valueOf(firstLine[0]);
        String rawUrl = firstLine[1];
        this.url = useRawUrl ? rawUrl : processUrl(rawUrl);
        this.headers = Collections.unmodifiableMap(new HashMap<>() {{
            for (int i = 1; i < headers.length; i++) {
                String[] headerPart = headers[i].split(HEADER_DELIMITER, 2);
                put(headerPart[0].trim(), headerPart[1].trim());
            }
        }});
        String bodyLengthHeader = this.getHeader("Content-Length");
        int length = bodyLengthHeader == null ? 0 : Integer.parseInt(bodyLengthHeader);
        this.body = parts.length > 1 ? parts[1].trim().substring(0, length) : "";
    }

    protected String processUrl(String url) throws UrlFormatException {
        Matcher matcher = URL_ENCODED_CHAR.matcher(url);
        StringBuilder builder = new StringBuilder();
        String c;
        while (matcher.find()) {
            c = matcher.group(0).substring(1);
            try {
                matcher.appendReplacement(builder, Character.toString(Integer.parseInt(c, 16)));
            }
            catch (NumberFormatException e) {
                throw new UrlFormatException("Unknown url encoded char", e);
            }
        }
        matcher.appendTail(builder);
        return builder.toString();
    }

    public String getHeader(String header) {
        return getHeaders().get(header);
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public String getMessage() {
        return this.message;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public String getUrl() {
        return this.url;
    }

    public String getBody() {
        return this.body;
    }

    public boolean hasHeader(String header) {
        return getHeader(header) != null;
    }
}