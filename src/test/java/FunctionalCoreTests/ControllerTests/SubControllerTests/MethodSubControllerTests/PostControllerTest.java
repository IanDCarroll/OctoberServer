package FunctionalCoreTests.ControllerTests.SubControllerTests.MethodSubControllerTests;

import Factory.ServerFactory;
import FunctionalCore.Controller.SubControllers.MethodSubControllers.PostController;
import FunctionalCore.Request;
import Helpers.FileHelper;
import Mocks.MockRequestDealer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostControllerTest {
    private int port = 5000;
    private String directory = System.getProperty("user.dir") + "/src/test/java/Mocks";
    private String configFile = directory + "/mock_routes.yml";
    private PostController subject;

    @BeforeEach
    void setup() {
        ServerFactory factory = new ServerFactory(port, directory, configFile);
        subject = factory.buildPostController();
    }
    @Test
    void fulfillIncludesA200Response() {
        //Given
        String uri = "/a-file-to-post";
        String fullPath = directory + uri;
        byte[] content = "Original content".getBytes();
        FileHelper.make(fullPath, content);
        Request request = MockRequestDealer.postRequest(uri);
        //When
        byte[] actual = subject.fulfill(request);
        //Then
        FileHelper.delete(fullPath);
        String expected = "HTTP/1.1 200 OK";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void fulfillAppendsItsContentsToTheBody() {
        //Given
        String uri = "/a-file-to-post";
        String fullPath = directory + uri;
        byte[] content = "Original content".getBytes();
        FileHelper.make(fullPath, content);
        Request request = MockRequestDealer.postRequest(uri);
        //When
        byte[] actual = subject.fulfill(request);
        //Then
        FileHelper.delete(fullPath);
        String original = new String(content);
        String expected = "This represents a well-formed POST";
        assertTrue(new String(actual).contains(original));
        assertTrue(new String(actual).contains(expected));
    }

}