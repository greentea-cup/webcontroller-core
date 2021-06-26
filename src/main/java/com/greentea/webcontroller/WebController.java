package com.greentea.webcontroller;

import com.greentea.webcontroller.server.AbstractWebServer;
import com.greentea.webcontroller.server.exception.WebServerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for connection management
 */
public final class WebController {
    private static final Logger LOGGER = LogManager.getLogger();

    private WebController() {}

    public static Thread startConnection(AbstractWebServer server) {
        return startConnection(server, LOGGER);
    }

    public static Thread startConnection(AbstractWebServer server, Logger logger) {
        return new Thread(() -> runConnector(server, logger));
    }

    private static void runConnector(AbstractWebServer server, Logger logger) {
        try {
            server.run();
        }
        catch (WebServerException e) {
            logger.error(e);
        }
    }

    public static Thread runInfiniteConnection(AbstractWebServer server) {
        return runInfiniteConnection(server, LOGGER);
    }

    public static Thread runInfiniteConnection(AbstractWebServer server, Logger logger) {
        return new Thread(() -> runConnectorInfinite(server, logger));
    }

    private static void runConnectorInfinite(AbstractWebServer server, Logger logger) {
        while (true) { runConnector(server, logger); }
    }
}