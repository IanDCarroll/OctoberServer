package FunctionalCoreTests.ControllerTests.SubControllerTests.MethodSubControllerTests;

import Factory.ServerFactory;
import FunctionalCore.Controller.SubControllers.MethodSubControllers.PatchController;
import FunctionalCore.Request;
import Helpers.FileHelper;
import Mocks.MockRequestDealer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatchControllerTest {
    private int port = 5000;
    private String directory = System.getProperty("user.dir") + "/src/test/java/Mocks";
    private String configFile = directory + "/mock_routes.yml";
    private PatchController subject;

    @BeforeEach
    void setup() {
        ServerFactory factory = new ServerFactory(port, directory, configFile);
        subject = factory.buildPatchController();
    }
    @Test
    void fulfillIncludesA204Response() {
        //Given
        String uri = "/a-file-to-patch";
        String fullPath = directory + uri;
        byte[] content = "Original content".getBytes();
        FileHelper.make(fullPath, content);
        String ifMatch = "900c0de";
        Request request = MockRequestDealer.patchRequest(uri, ifMatch);
        //When
        byte[] actual = subject.fulfill(request);
        //Then
        FileHelper.delete(fullPath);
        String expected = "HTTP/1.1 204 No Content";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void fulfillAppendsContentsToTheBody() {
        //Given
        String uri = "/a-file-to-patch";
        String fullPath = directory + uri;
        byte[] content = "Original content".getBytes();
        FileHelper.make(fullPath, content);
        String ifMatch = "900c0de";
        Request request = MockRequestDealer.patchRequest(uri, ifMatch);
        //When
        byte[] actual = subject.fulfill(request);
        //Then
        FileHelper.delete(fullPath);
        String original = new String(content);
        String expected = "This represents a well-formed PATCH";
        assertTrue(new String(actual).contains(original));
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void fulfillIncludesAnETag() {
        //Given
        String uri = "/a-file-to-patch";
        String fullPath = directory + uri;
        byte[] content = "Original content".getBytes();
        FileHelper.make(fullPath, content);
        String ifMatch = "900c0de";
        Request request = MockRequestDealer.patchRequest(uri, ifMatch);
        //When
        byte[] actual = subject.fulfill(request);
        //Then
        FileHelper.delete(fullPath);
        String expected = "ETag: " + ifMatch;
        assertTrue(new String(actual).contains(expected));
    }

}