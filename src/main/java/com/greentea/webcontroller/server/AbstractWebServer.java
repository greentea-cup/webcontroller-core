package com.greentea.webcontroller.server;

import com.greentea.webcontroller.http.HttpRequest;
import com.greentea.webcontroller.http.HttpResponse;
import com.greentea.webcontroller.http.exception.RequestCreationException;
import com.greentea.webcontroller.http.exception.ResponseCreationException;
import com.greentea.webcontroller.server.exception.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public abstract class AbstractWebServer {
    private static final Logger LOGGER = LogManager.getLogger();
    protected AsynchronousServerSocketChannel server;

    public void run() throws WebServerException {
        if (this.server == null) {
            try {
                this.server = AsynchronousServerSocketChannel.open();
            }
            catch (IOException e) {
                throw new WebServerStartException(e);
            }
			try {
                this.server.bind(new InetSocketAddress(this.getHost(), this.getPort()));
			}
			catch (IOException e) {
				LOGGER.warn("Cannot bind socket address, this exception will be ignored", e);
			}
        }
        Future<AsynchronousSocketChannel> future = this.server.accept();
        resolve(future);
    }

    public abstract String getHost();

    public abstract int getPort();

    protected void resolve(Future<AsynchronousSocketChannel> future) throws WebServerException {
        try (AsynchronousSocketChannel channel = future.get()) {
            HttpRequest request = createRequest(readFromChannel(channel));
            HttpResponse response = createResponse();
            String body = handleChannel(request, response);
            if (body != null && !body.isBlank()) {
                if (!response.hasHeader("Content-Type")) {
                    response.addHeader("Content-Type", getContentType());
                }
                response.setBody(body);
            }
            ByteBuffer resp = ByteBuffer.wrap(response.getBytes());
            while (resp.hasRemaining()) {
                channel.write(resp);
            }
        }
        catch (InterruptedException e) {
            throw new WebServerInterruptedException(e);
        }
        catch (ExecutionException e) {
            throw new WebServerRunException(e);
        }
        catch (IOException e) {
            throw new WebServerCloseException(e);
        }
        catch (RequestCreationException e) {
            throw new WebServerRequestException(e);
        }
        catch (ResponseCreationException e) {
            throw new WebServerResponseException(e);
        }
    }

    public abstract HttpRequest createRequest(String message) throws WebServerException, RequestCreationException;

    private String readFromChannel(AsynchronousSocketChannel channel) throws WebServerException {
        int size = getBufferSize();
        ByteBuffer buffer = ByteBuffer.allocate(size);
        StringBuilder builder = new StringBuilder();
        int bytesRead;
        do {
            try {
                bytesRead = channel.read(buffer).get();
                buffer.flip();
                builder.append(StandardCharsets.UTF_8.decode(buffer));
            }
            catch (ExecutionException e) {
                throw new WebServerRunException(e);
            }
            catch (InterruptedException e) {
                throw new WebServerInterruptedException(e);
            }
            finally {
                buffer.clear();
            }
        } while (bytesRead == size);
        return builder.toString();
    }

    public abstract HttpResponse createResponse() throws WebServerException, ResponseCreationException;

    public abstract String handleChannel(HttpRequest request, HttpResponse response) throws WebServerException;

    protected abstract String getContentType();

    protected abstract int getBufferSize();
}