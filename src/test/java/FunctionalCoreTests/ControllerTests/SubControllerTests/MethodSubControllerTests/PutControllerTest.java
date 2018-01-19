package FunctionalCoreTests.ControllerTests.SubControllerTests.MethodSubControllerTests;

import Factory.ServerFactory;
import FunctionalCore.Controller.SubControllers.MethodSubControllers.PutController;
import FunctionalCore.Request;
import Helpers.FileHelper;
import Mocks.MockRequestDealer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PutControllerTest {
    private int port = 5000;
    private String directory = System.getProperty("user.dir") + "/src/test/java/Mocks";
    private String configFile = directory + "/mock_routes.yml";
    private PutController subject;

    @BeforeEach
    void setup() {
        ServerFactory factory = new ServerFactory(port, directory, configFile);
        subject = factory.buildPutController();
    }
    @Test
    void fulfillIncludesA200Response() {
        //Given
        String uri = "/a-file-to-put";
        String fullPath = directory + uri;
        byte[] content = "Original content".getBytes();
        FileHelper.make(fullPath, content);
        Request request = MockRequestDealer.putRequest(uri);
        //When
        byte[] actual = subject.fulfill(request);
        //Then
        FileHelper.delete(fullPath);
        String expected = "HTTP/1.1 200 OK";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void fulfillReplacesTheOriginalContentsWithItsContentsInTheBody() {
        //Given
        String uri = "/a-file-to-put";
        String fullPath = directory + uri;
        byte[] content = "Original content".getBytes();
        FileHelper.make(fullPath, content);
        Request request = MockRequestDealer.putRequest(uri);
        //When
        byte[] actual = subject.fulfill(request);
        //Then
        FileHelper.delete(fullPath);
        String unexpected = new String(content);
        String expected = "This represents a well--formed PUT";
        assertFalse(new String(actual).contains(unexpected));
        assertTrue(new String(actual).contains(expected));
    }

}