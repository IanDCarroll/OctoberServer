package ServerShell;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

public class SocketWriter {
    public void send(byte[] response, AsynchronousSocketChannel clientConnection) {
        clientConnection.write(ByteBuffer.wrap(response));
    }
}
