package ServerShell;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

public class SocketWriter {
    public void send(byte[] response, AsynchronousSocketChannel clientConnection) {
        clientConnection.write(ByteBuffer.wrap(response));
        close(clientConnection);
    }

    public void close(AsynchronousSocketChannel clientConnection) {
        try {
            clientConnection.close();
        } catch (IOException e) {
            System.out.println("IOException caught while closing the client connection.");
        }
    }
}
