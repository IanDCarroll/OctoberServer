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
        //Given
        String[] emptyParams = {};
        //When
        byte[] actual = subject.generate200(emptyParams);
        //Then
        String expected = "HTTP/1.1 200 OK";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generate200GeneratesAResponseWithParams() {
        //Given
        String[] params = { "Cool-Param=pretty cool",
                "Awesome-Param=could be more awesome",
                "Radical-Param=totally rad" };
        //When
        byte[] actual = subject.generate200(params);
        //Then
        String expected = "Cool-Param = pretty cool\n" +
                "Awesome-Param = could be more awesome\n" +
                "Radical-Param = totally rad\n";
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