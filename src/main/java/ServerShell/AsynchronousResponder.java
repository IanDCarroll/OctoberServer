package ServerShell;

import FunctionalCore.Core;
import Loggers.Logger;

import java.awt.*;
import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;

public class AsynchronousResponder {
    private SocketReader reader;
    private Core core;
    private SocketWriter writer;
    private Logger logger;

    public AsynchronousResponder(SocketReader reader, Core core, SocketWriter writer, Logger logger) {
        this.reader = reader;
        this.core = core;
        this.writer = writer;
        this.logger = logger;
    }

    public void respondTo(AsynchronousSocketChannel clientConnection) {
        byte[] request = reader.readRequest(clientConnection);
        byte[] response = core.craftResponseTo(request);
        writer.send(response, clientConnection);
        close(clientConnection);
    }

    public void close(AsynchronousSocketChannel clientConnection) {
        try {
            clientConnection.close();
        } catch (IOException e) {
            logger.log("IOException caught while closing the client connection.");
        }
    }
}
