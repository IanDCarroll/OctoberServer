package FunctionalCoreTests.ControllerTests.SubControllerTests.MethodSubControllerTests;

import Factory.ServerFactory;
import FunctionalCore.Controller.SubControllers.MethodSubControllers.HeadController;
import FunctionalCore.Request;
import Helpers.FileHelper;
import Mocks.MockRequestDealer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeadControllerTest {
    private int port = 5000;
    private String directory = System.getProperty("user.dir") + "/src/test/java/Mocks";
    private String configFile = directory + "/mock_routes.yml";
    private HeadController subject;

    @BeforeEach
    void setup() {
        ServerFactory factory = new ServerFactory(port, directory, configFile);
        subject = factory.buildHeadController();
    }
    @Test
    void fulfillIncludesA200Response() {
        //Given
        String uri = "/head-uri";
        String fullPath = directory + uri;
        byte[] content = "Original content".getBytes();
        FileHelper.make(fullPath, content);
        Request request = MockRequestDealer.headHeaderRequest();
        //When
        byte[] actual = subject.fulfill(request);
        //Then
        FileHelper.delete(fullPath);
        String expected = "HTTP/1.1 200 OK";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void fulfillDoesNotIncludeABody() {
        //Given
        String uri = "/head-uri";
        String fullPath = directory + uri;
        byte[] content = "Original content".getBytes();
        FileHelper.make(fullPath, content);
        Request request = MockRequestDealer.headHeaderRequest();
        //When
        byte[] actual = subject.fulfill(request);
        //Then
        FileHelper.delete(fullPath);
        String unexpected = new String(content);
        assertFalse(new String(actual).contains(unexpected));
    }

}