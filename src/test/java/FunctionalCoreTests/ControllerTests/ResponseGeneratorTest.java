package FunctionalCoreTests.ControllerTests;

import FunctionalCore.Controller.ResponseGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseGeneratorTest {
    public ResponseGenerator subject;

    @BeforeEach
    void init() {
        subject = new ResponseGenerator();
    }

    @Test
    void generate200GeneratesA200Response() {
        //Given nothing
        //When
        byte[] actual = subject.generate200();
        //Then
        String expected = "HTTP/1.1 200 OK";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generate404GeneratesA404Response() {
        //Given nothing
        //When
        byte[] actual = subject.generate404();
        //Then
        String expected = "404 Not Found";
        assertTrue(new String(actual).contains(expected));
    }
}