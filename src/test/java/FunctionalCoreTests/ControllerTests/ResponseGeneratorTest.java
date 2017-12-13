package FunctionalCoreTests.ControllerTests;

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
        subject = new ResponseGenerator();
    }

    @Test
    void generate200GeneratesA200Response() {
        //Given
        String root = publicDir + "/";
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
        String root = publicDir + "/";
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
        String expected = "Allow: GET,OPTIONS,EXFOLIATE\n";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generate200ReturnsTheBodyWithTheResponse() {
        //Given
        byte[] content = "Original content".getBytes();
        String name = publicDir + MockRequestDealer.getRequest("/a-file-with-a-body");
        FileHelper.make(name, content);
        String[] params = new String[0];
        //When
        byte[] actual = subject.generate200(name, params);
        //Then
        FileHelper.delete(name);
        String expected = new String(content);
        assertTrue(new String(actual).contains(expected));
    }
}