package serverShell;

import functionalCore.Core;

import java.nio.channels.AsynchronousSocketChannel;

public class AsynchronousResponder {
    private SocketReader reader;
    private Core responseCrafter;
    private SocketWriter writer;

    public AsynchronousResponder(SocketReader reader, Core responseCrafter, SocketWriter writer) {
        this.reader = reader;
        this.responseCrafter = responseCrafter;
        this.writer = writer;
    }

    public void respondTo(AsynchronousSocketChannel clientConnection) {
        byte[] request = reader.readRequest(clientConnection);
        byte[] response = responseCrafter.craftResponseTo(request);
        writer.send(response, clientConnection);
    }
}
