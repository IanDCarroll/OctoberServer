package ServerShellTests;

import FunctionalCore.Core;
import Mocks.MockCoreDealer;
import Mocks.MockSocketDealer;
import ServerShell.AsynchronousResponder;
import ServerShell.SocketReader;
import ServerShell.SocketWriter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.channels.AsynchronousSocketChannel;

import static org.junit.jupiter.api.Assertions.*;

class AsynchronousResponderTest {
    AsynchronousSocketChannel socket;
    SocketReader reader;
    Core core;
    SocketWriter writer;
    AsynchronousResponder subject;

    @BeforeEach
    void setup() {
        socket = MockSocketDealer.socket;
        reader = new SocketReader();
        core = MockCoreDealer.core;
        writer = new SocketWriter();
        subject = new AsynchronousResponder(reader, core, writer);

    }

    @AfterEach
    void tearDown() {
        MockSocketDealer.request = "";
    }

    @Test
    void respondToReadsARequestAndSendsAFormulatedResponse() {
        //Given
        MockSocketDealer.request = MockCoreDealer.correctRequest;
        //When
        subject.respondTo(socket);
        //Then
        assertEquals(MockCoreDealer.desiredResponse, MockSocketDealer.response);
    }

}