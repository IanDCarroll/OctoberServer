package FunctionalCoreTests.ControllerTests.ResponseGenerationTests;

import Factory.ServerFactory;
import FunctionalCore.Controller.ResponseGeneration.ETagGenerator;
import FunctionalCore.Controller.ResponseGeneration.SuccessGenerator;
import FunctionalCore.Request;
import Helpers.FileHelper;
import Mocks.MockRequestDealer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ETagGeneratorTest {
    private int port = 5000;
    private String directory = System.getProperty("user.dir") + "/src/test/java/Mocks";
    private String configFile = directory + "/mock_routes.yml";
    ETagGenerator subject;

    @BeforeEach
    void setup() {
        ServerFactory factory = new ServerFactory(port, directory, configFile);
        subject = factory.buildETagGenerator();
    }

    @Test
    void generateReturnsA204StartLine() {
        //Given
        String ifMatch = "c001c0de";
        byte[] content = "Original content".getBytes();
        String uri = "/a-file-with-a-body";
        Request request = MockRequestDealer.eTagRequest(uri, ifMatch);
        String fullPath = directory + uri;
        FileHelper.make(fullPath, content);
        //When
        byte[] actual = subject.generate(SuccessGenerator.Code.NO_CONTENT, request);
        //Then
        FileHelper.delete(fullPath);
        String expected = "204 No Content";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generateReturnsAnETagHeader() {
        //Given
        String ifMatch = "c001c0de";
        byte[] content = "Original content".getBytes();
        String uri = "/a-file-with-a-body";
        Request request = MockRequestDealer.eTagRequest(uri, ifMatch);
        String fullPath = directory + uri;
        FileHelper.make(fullPath, content);
        //When
        byte[] actual = subject.generate(SuccessGenerator.Code.NO_CONTENT, request);
        //Then
        FileHelper.delete(fullPath);
        String expected = "ETag: " + ifMatch;
        assertTrue(new String(actual).contains(expected));
    }

}