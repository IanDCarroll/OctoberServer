package ServerShell;

import FunctionalCore.Core;
import Loggers.Logger;

import java.awt.*;
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
        logger.log(new String(request));
        byte[] response = core.craftResponseTo(request);
        writer.send(response, clientConnection);
        logger.log(new String(response));
    }
}
