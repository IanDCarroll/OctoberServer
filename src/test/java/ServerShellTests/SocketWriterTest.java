package ServerShellTests;

import Mocks.MockSocketDealer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ServerShell.SocketWriter;

import java.nio.channels.AsynchronousSocketChannel;

import static org.junit.jupiter.api.Assertions.*;

class SocketWriterTest {
    SocketWriter subject;
    AsynchronousSocketChannel socket;

    @BeforeEach
    void setup() {
        subject = new SocketWriter();
        socket = MockSocketDealer.socket;
    }

    @AfterEach
    void tearDown() {
        MockSocketDealer.response = "";
    }

    @Test
    void sendSendsAGivenByteArrayOverTheWire() {
        //Given
        String message = "Hey now";
        //When
        subject.send(message.getBytes(), socket);
        //Then
        assertEquals(message, MockSocketDealer.response);
    }

    @Test
    void closeClosesASocketChannel() {
        //When
        subject.close(socket);
        //Then
        assertFalse(socket.isOpen());
    }
}