package FunctionalCoreTests.ControllerTests.ResponseGenerationTests;

import Factory.ServerFactory;
import FunctionalCore.Controller.ResponseGeneration.SuccessGenerator;
import Helpers.FileHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SuccessGeneratorTest {
    private int port = 5000;
    private String directory = System.getProperty("user.dir") + "/src/test/java/Mocks";
    private String configFile = directory + "/mock_routes.yml";
    private SuccessGenerator subject;

    @BeforeEach
    void init() {
        ServerFactory factory = new ServerFactory(port, directory, configFile);
        subject = factory.buildSuccessGenerator();
    }

    @Test
    void generateGeneratesA200Response() {
        //Given
        String root = "/";
        //When
        byte[] actual = subject.generate(SuccessGenerator.Code.OK, root);
        //Then
        String expected = "HTTP/1.1 200 OK";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generateGeneratesAResponseWithParams() {
        //Given
        String root = "/";
        String[] params = { "Cool-Param=pretty cool",
                "Awesome-Param=could be more awesome",
                "Radical-Param=totally rad" };
        //When
        byte[] actual = subject.generate(SuccessGenerator.Code.OK, root, params);
        //Then
        String expected = "Cool-Param = pretty cool\n" +
                "Awesome-Param = could be more awesome\n" +
                "Radical-Param = totally rad\n";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generateReturnsTheBodyWithTheResponse() {
        //Given
        byte[] content = "Original content".getBytes();
        String uri = "/a-file-with-a-body";
        String fullPath = directory + uri;
        FileHelper.make(fullPath, content);
        String[] params = new String[0];
        //When
        byte[] actual = subject.generate(SuccessGenerator.Code.OK, uri, params);
        //Then
        FileHelper.delete(fullPath);
        String expected = new String(content);
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generateHeadReturnsTheBodyWithBasicHeaders() {
        //Given
        byte[] content = "Original content".getBytes();
        String uri = "/a-file-with-a-body";
        String fullPath = directory + uri;
        FileHelper.make(fullPath, content);
        //When
        byte[] actual = subject.generateHead(SuccessGenerator.Code.OK, uri);
        //Then
        FileHelper.delete(fullPath);
        String length = "Content-Length: " + String.valueOf(content.length);
        String type = "Content-Type: text/";
        assertTrue(new String(actual).contains(length));
        assertTrue(new String(actual).contains(type));
    }
}