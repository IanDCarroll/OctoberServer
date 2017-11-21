package serverShell;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.TimeUnit;

public class SocketReader {
    private static final int THIS_MANY_BYTES = 8192;
    private static final int THIS_MUCH_TIME = 20;
    private static final java.util.concurrent.TimeUnit MEASURED_BY_THIS_UNIT = TimeUnit.SECONDS;

    private ByteBuffer buffer = ByteBuffer.allocate(THIS_MANY_BYTES);

    public byte[] readRequest(AsynchronousSocketChannel clientConnection) {
        byte[] requestBytes = new byte[0];
        try {
            requestBytes = populateRequestFrom(clientConnection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestBytes;
    }

    private byte[] populateRequestFrom(AsynchronousSocketChannel clientConnection) throws Exception {
        int withThisManyBytesToRead = getTheRightAmountOfBytesToReadFrom(clientConnection);
        return aFull(new byte[withThisManyBytesToRead]);
    }

    private int getTheRightAmountOfBytesToReadFrom(AsynchronousSocketChannel clientConnection) throws Exception {
        return clientConnection.read(buffer).get(THIS_MUCH_TIME, MEASURED_BY_THIS_UNIT);
    }

    private byte[] aFull(byte[] requestBytes) {
        int bytesToRead = requestBytes.length;
        if (thereAreSome(bytesToRead)) {
            return aBufferFullOf(requestBytes, bytesToRead);
        }
        return new byte[0];
    }

    private boolean thereAreSome(int bytesToRead) {
        return bytesToRead > 0 && buffer.position() > 2;
    }

    private byte[] aBufferFullOf(byte[] requestBytes, int andWhenYouComeToTheEnd_Stop) {
        int startAtTheBeginning = 0;
        buffer.flip();
        buffer.get(requestBytes, startAtTheBeginning, andWhenYouComeToTheEnd_Stop);
        buffer.clear();
        return requestBytes;
    }
}
