package ServerShell;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.TimeUnit;

public class SocketReader {
    private static final int THIS_MANY_BYTES = 8192;
    private static final int THIS_MUCH_TIME = 20;
    private static final TimeUnit MEASURED_BY_THIS_UNIT = TimeUnit.SECONDS;
    private ByteBuffer buffer = ByteBuffer.allocate(THIS_MANY_BYTES);
    private byte[] request = new byte[0];

    public byte[] readRequest(AsynchronousSocketChannel clientConnection) {
        try {
            getRequestFrom(clientConnection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }

    private void getRequestFrom(AsynchronousSocketChannel clientConnection) throws Exception {
        request = new byte[withTheLengthFromThe(clientConnection)];
        fillRequest(ifTheBufferHasContent());
    }

    public int withTheLengthFromThe(AsynchronousSocketChannel clientConnection) throws Exception {
        return clientConnection.read(buffer).get(THIS_MUCH_TIME, MEASURED_BY_THIS_UNIT);
    }

    private void fillRequest(boolean thereIsSomethingToRead) {
        if (thereIsSomethingToRead) { fillRequestFromBuffer(); }
    }

    private boolean ifTheBufferHasContent() {
        return request.length > 0 && buffer.position() > 2;
    }

    private void fillRequestFromBuffer() {
        ((Buffer)buffer).flip();
        buffer.get(request);
        ((Buffer)buffer).clear();
    }
}
