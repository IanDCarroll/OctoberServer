package FunctionalCoreTests.ControllerTests.ResponseGenerationTests;

import Filers.FileClerk;
import FunctionalCore.Controller.ResponseGeneration.SuccessGenerator;
import Helpers.FileHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SuccessGeneratorTest {
    private SuccessGenerator subject;
    private String publicDir = System.getProperty("user.dir") + "/src/test/java/Mocks";

    @BeforeEach
    void init() {
        FileClerk fileClerk = new FileClerk(publicDir);
        subject = new SuccessGenerator(fileClerk);
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
        String fullPath = publicDir + uri;
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
        String fullPath = publicDir + uri;
        FileHelper.make(fullPath, content);
        //When
        byte[] actual = subject.generateHead(SuccessGenerator.Code.OK, uri);
        //Then
        FileHelper.delete(fullPath);
        String length = "Content-Length: " + String.valueOf(content.length);
        String type = "Content-Type: text/plain";
        assertTrue(new String(actual).contains(length));
        assertTrue(new String(actual).contains(type));
    }

    @Test
    void generate206ReturnsTheBodyRange() {
        //Given
        int[] rangeTuple = { 2, 8 };
        byte[] content = "Original content".getBytes();
        String uri = "/a-file-with-a-body";
        String fullPath = publicDir + uri;
        FileHelper.make(fullPath, content);
        //When
        byte[] actual = subject.generate(SuccessGenerator.Code.PARTIAL_CONTENT, uri, rangeTuple);
        //Then
        FileHelper.delete(fullPath);
        String expected = "iginal";
        assertTrue(new String(actual).contains(expected));
        assertFalse(new String(actual).contains(new String(content)));
    }

    @Test
    void generate206ReturnsA206StartLine() {
        //Given
        int[] rangeTuple = { 2, 8 };
        byte[] content = "Original content".getBytes();
        String uri = "/a-file-with-a-body";
        String fullPath = publicDir + uri;
        FileHelper.make(fullPath, content);
        //When
        byte[] actual = subject.generate(SuccessGenerator.Code.PARTIAL_CONTENT, uri, rangeTuple);
        //Then
        FileHelper.delete(fullPath);
        String expected = "206 Partial Content";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generate206ReturnsAContentRangeHeader() {
        //Given
        int[] rangeTuple = { 2, 8 };
        byte[] content = "Original content".getBytes();
        String uri = "/a-file-with-a-body";
        String fullPath = publicDir + uri;
        FileHelper.make(fullPath, content);
        //When
        byte[] actual = subject.generate(SuccessGenerator.Code.PARTIAL_CONTENT, uri, rangeTuple);
        //Then
        FileHelper.delete(fullPath);
        String expected = "Content-Range: bytes 2-8/" + String.valueOf(content.length);
        assertTrue(new String(actual).contains(expected));
    }

}