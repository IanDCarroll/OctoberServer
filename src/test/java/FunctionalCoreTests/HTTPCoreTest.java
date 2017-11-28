package FunctionalCoreTests;

import FunctionalCore.Controller;
import FunctionalCore.HTTPCore;
import FunctionalCore.Parser;
import FunctionalCore.ResponseGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class HTTPCoreTest {
    HTTPCore subject;

    @BeforeEach
    void setup() {
        Parser parser = new Parser();
        ResponseGenerator responseGenerator = new ResponseGenerator();
        LinkedHashMap routes = new LinkedHashMap();
        Controller controller = new Controller(responseGenerator, routes);
        subject = new HTTPCore(parser, controller);
    }

    @Test
    void coreReturnsA200ResponseWhenTheRootIsRequested() {
        //Given
        byte[] request = "GET / HTTP/1.1\r\n\r\n".getBytes();
        //When
        String actual = new String(subject.craftResponseTo(request));
        //Then
        String expected = "HTTP/1.1 200 OK";
        assertTrue(actual.contains(expected));
    }
}