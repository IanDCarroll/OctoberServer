package ServerShell;

import FunctionalCore.Core;

import java.awt.*;
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
        System.out.println("recieved this:");
        System.out.println(new String(request));
        byte[] response = responseCrafter.craftResponseTo(request);
        System.out.println("sending this:");
        System.out.println(new String(response));
        writer.send(response, clientConnection);
    }
}
