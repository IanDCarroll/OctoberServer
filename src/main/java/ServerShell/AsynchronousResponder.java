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
        byte[] request = logger.messageLog(reader.readRequest(clientConnection));
        byte[] response = logger.messageLog(core.craftResponseTo(request));
        writer.send(response, clientConnection);
    }
}
