package ServerShell;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

public class SocketWriter {
    public void send(byte[] response, AsynchronousSocketChannel clientConnection) {
        clientConnection.write(ByteBuffer.wrap(response));
    }
}
