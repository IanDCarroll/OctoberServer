package ServerShellTests;

import Mocks.MockSocketDealer;
import org.junit.jupiter.api.Test;
import ServerShell.SocketWriter;

import java.nio.channels.AsynchronousSocketChannel;

import static org.junit.jupiter.api.Assertions.*;

class SocketWriterTest {
    SocketWriter subject = new SocketWriter();
    AsynchronousSocketChannel socket = MockSocketDealer.writeSocket;

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