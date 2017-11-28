package ServerShellTests;

import Mocks.MockSocketDealer;
import org.junit.jupiter.api.Test;
import serverShell.SocketWriter;

import java.nio.channels.AsynchronousSocketChannel;

import static org.junit.jupiter.api.Assertions.*;

class SocketWriterTest {
    SocketWriter subject = new SocketWriter();
    AsynchronousSocketChannel socket = MockSocketDealer.socket;

    @Test
    void sendSendsAGivenByteArrayOverTheWire() {
        //Given
        String message = "Hey now";
        //When
        subject.send(message.getBytes(), socket);
        //Then
        assertEquals(message, MockSocketDealer.response);
    }

}