package ServerShellTests;

import FunctionalCore.Core;
import Mocks.MockCoreDealer;
import Mocks.MockSocketDealer;
import ServerShell.AsynchronousResponder;
import ServerShell.SocketReader;
import ServerShell.SocketWriter;
import org.junit.jupiter.api.Test;

import java.nio.channels.AsynchronousSocketChannel;

import static org.junit.jupiter.api.Assertions.*;

class AsynchronousResponderTest {
    AsynchronousSocketChannel socket = MockSocketDealer.readWriteSocket;
    SocketReader reader = new SocketReader();
    Core mockCore = MockCoreDealer.core;
    SocketWriter writer = new SocketWriter();
    AsynchronousResponder subject = new AsynchronousResponder(reader, mockCore, writer);


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