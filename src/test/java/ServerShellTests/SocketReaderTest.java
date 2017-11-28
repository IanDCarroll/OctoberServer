package ServerShellTests;

import Mocks.MockSocketDealer;
import org.junit.jupiter.api.Test;
import serverShell.SocketReader;

import java.nio.channels.AsynchronousSocketChannel;

import static org.junit.jupiter.api.Assertions.*;

class SocketReaderTest {
    SocketReader subject = new SocketReader();
    AsynchronousSocketChannel socket = MockSocketDealer.socket;

    @Test
    void sendSendsAGivenByteArrayOverTheWire() {
        //Given
        MockSocketDealer.request = "This is a request";
        //When
        byte[] actual = subject.readRequest(socket);
        //Then
        assertEquals(MockSocketDealer.request, new String(actual));
    }

    @Test
    void withTheLengthFromThe_Socket_GetsTheRightAmountOfBytesToRead() {
        //Given
        MockSocketDealer.request = "four";
        int actual = 0;
        //When
        try { actual = subject.withTheLengthFromThe(socket); } catch (Exception e) {}
        //Then
        int expected = MockSocketDealer.request.getBytes().length;
        assertEquals(expected, actual);
    }

}