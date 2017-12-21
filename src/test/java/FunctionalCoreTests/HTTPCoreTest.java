package FunctionalCoreTests;

import Filers.FileClerk;
import FunctionalCore.Controller.Controller;
import FunctionalCore.HTTPCore;
import FunctionalCore.Parser.Parser;
import FunctionalCore.Controller.ResponseGeneration.ResponseGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class HTTPCoreTest {
    private HTTPCore subject;
    private LinkedHashMap<String, LinkedHashMap<String, String>> mockRoutes;
    private LinkedHashMap<String, String> mockRouteAttributes;
    private String publicDir;
    private FileClerk fileClerk;
    private ResponseGenerator responseGenerator;

    @BeforeEach
    void setup() {
        Parser parser = new Parser();
        mockRoutes = new LinkedHashMap();
        mockRouteAttributes = new LinkedHashMap();
        publicDir = System.getProperty("user.dir") + "/src/test/java/Mocks";
        fileClerk = new FileClerk(publicDir);
        responseGenerator = new ResponseGenerator(fileClerk);
        Controller controller = new Controller(responseGenerator, mockRoutes, fileClerk);
        subject = new HTTPCore(parser, controller);
    }

    @Test
    void coreReturnsA200ResponseWhenTheRootIsRequested() {
        //Given
        mockRouteAttributes.put("allowed-methods", "GET");
        mockRouteAttributes.put("redirect-uri", "");
        mockRouteAttributes.put("authorization", "");
        mockRoutes.put("/", mockRouteAttributes);
        byte[] request = "GET / HTTP/1.1\r\n\r\n".getBytes();
        //When
        String actual = new String(subject.craftResponseTo(request));
        //Then
        String expected = "HTTP/1.1 200 OK";
        assertTrue(actual.contains(expected));
    }
}