package FunctionalCoreTests.ControllerTests;

import Filers.FileClerk;
import FunctionalCore.Controller.ResponseGenerator;
import FunctionalCore.Request;
import Helpers.FileHelper;
import Mocks.MockRequestDealer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseGeneratorTest {
    private ResponseGenerator subject;
    private String publicDir = System.getProperty("user.dir") + "/src/test/java/Mocks";

    @BeforeEach
    void init() {
        FileClerk fileClerk = new FileClerk(publicDir);
        subject = new ResponseGenerator(fileClerk);
    }

    @Test
    void generate200GeneratesA200Response() {
        //Given
        String root = "/";
        String[] emptyParams = {};
        //When
        byte[] actual = subject.generate200(root, emptyParams);
        //Then
        String expected = "HTTP/1.1 200 OK";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generate200GeneratesAResponseWithParams() {
        //Given
        String root = "/";
        String[] params = { "Cool-Param=pretty cool",
                "Awesome-Param=could be more awesome",
                "Radical-Param=totally rad" };
        //When
        byte[] actual = subject.generate200(root, params);
        //Then
        String expected = "Cool-Param = pretty cool\n" +
                "Awesome-Param = could be more awesome\n" +
                "Radical-Param = totally rad\n";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generate404GeneratesA404Response() {
        //Given nothing
        //When
        byte[] actual = subject.generate404();
        //Then
        String expected = "404 Not Found";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generate405GeneratesA405Response() {
        //Given
        String permittedMethods = "GET OPTIONS";
        //When
        byte[] actual = subject.generate405(permittedMethods);
        //Then
        String expected = "405 Method Not Allowed";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generate405IncludesAnAllowHeader() {
        //Given
        String permittedMethods = "GET OPTIONS EXFOLIATE";
        //When
        byte[] actual = subject.generate405(permittedMethods);
        //Then
        String expected = "Allow: GET,OPTIONS,EXFOLIATE";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generate200ReturnsTheBodyWithTheResponse() {
        //Given
        byte[] content = "Original content".getBytes();
        String uri = "/a-file-with-a-body";
        String fullPath = publicDir + uri;
        FileHelper.make(fullPath, content);
        String[] params = new String[0];
        //When
        byte[] actual = subject.generate200(uri, params);
        //Then
        FileHelper.delete(fullPath);
        String expected = new String(content);
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generate200HeadReturnsTheBodyWithBasicHeaders() {
        //Given
        byte[] content = "Original content".getBytes();
        String uri = "/a-file-with-a-body";
        String fullPath = publicDir + uri;
        FileHelper.make(fullPath, content);
        //When
        byte[] actual = subject.generate200Head(uri);
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
        byte[] content = "Original content".getBytes();
        String uri = "/a-file-with-a-body";
        String fullPath = publicDir + uri;
        FileHelper.make(fullPath, content);
        //When
        byte[] actual = subject.generate206(uri, 2, 8);
        //Then
        FileHelper.delete(fullPath);
        String expected = "iginal";
        assertTrue(new String(actual).contains(expected));
        assertFalse(new String(actual).contains(new String(content)));
    }

    @Test
    void generate206ReturnsA206StartLine() {
        //Given
        byte[] content = "Original content".getBytes();
        String uri = "/a-file-with-a-body";
        String fullPath = publicDir + uri;
        FileHelper.make(fullPath, content);
        //When
        byte[] actual = subject.generate206(uri, 2, 8);
        //Then
        FileHelper.delete(fullPath);
        String expected = "206 Partial Content";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generate206ReturnsAContentRangeHeader() {
        //Given
        byte[] content = "Original content".getBytes();
        String uri = "/a-file-with-a-body";
        String fullPath = publicDir + uri;
        FileHelper.make(fullPath, content);
        //When
        byte[] actual = subject.generate206(uri, 2, 8);
        //Then
        FileHelper.delete(fullPath);
        String expected = "Content-Range: bytes 2-8/" + String.valueOf(content.length);
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generate416ReturnsA416StartLine() {
        //Given
        byte[] content = "Original content".getBytes();
        String uri = "/a-file-with-a-body";
        String fullPath = publicDir + uri;
        FileHelper.make(fullPath, content);
        //When
        byte[] actual = subject.generate416(uri);
        //Then
        FileHelper.delete(fullPath);
        String expected = "416 Range Not Satisfiable";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generate416ReturnsAContentRangeHeader() {
        //Given
        byte[] content = "Original content".getBytes();
        String uri = "/a-file-with-a-body";
        String fullPath = publicDir + uri;
        FileHelper.make(fullPath, content);
        //When
        byte[] actual = subject.generate416(uri);
        //Then
        FileHelper.delete(fullPath);
        String expected = "Content-Range: bytes */" + String.valueOf(content.length);
        assertTrue(new String(actual).contains(expected));
    }
}