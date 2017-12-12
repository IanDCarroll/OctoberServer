package FunctionalCoreTests;

import FunctionalCore.Controller.Controller;
import FunctionalCore.HTTPCore;
import FunctionalCore.Parser.Parser;
import FunctionalCore.Controller.ResponseGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class HTTPCoreTest {
    private HTTPCore subject;
    private LinkedHashMap<String, String> mockRoutes;
    private String publicDir;

    @BeforeEach
    void setup() {
        Parser parser = new Parser();
        ResponseGenerator responseGenerator = new ResponseGenerator();
        mockRoutes = new LinkedHashMap();
        publicDir = System.getProperty("user.dir") + "/src/test/java/Mocks";
        Controller controller = new Controller(responseGenerator, mockRoutes, publicDir);
        subject = new HTTPCore(parser, controller);
    }

    @Test
    void coreReturnsA200ResponseWhenTheRootIsRequested() {
        //Given
        mockRoutes.put("/", "GET");
        byte[] request = "GET / HTTP/1.1\r\n\r\n".getBytes();
        //When
        String actual = new String(subject.craftResponseTo(request));
        //Then
        String expected = "HTTP/1.1 200 OK";
        assertTrue(actual.contains(expected));
    }
}