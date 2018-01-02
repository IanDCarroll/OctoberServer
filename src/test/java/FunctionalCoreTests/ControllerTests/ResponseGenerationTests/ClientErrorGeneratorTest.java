package FunctionalCoreTests.ControllerTests.ResponseGenerationTests;

import Filers.FileClerk;
import FunctionalCore.Controller.ResponseGeneration.ClientErrorGenerator;
import Helpers.FileHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientErrorGeneratorTest {
    private ClientErrorGenerator subject;
    private String publicDir = System.getProperty("user.dir") + "/src/test/java/Mocks";

    @BeforeEach
    void init() {
        FileClerk fileClerk = new FileClerk(publicDir);
        subject = new ClientErrorGenerator(fileClerk);
    }

    @Test
    void generateGeneratesA404Response() {
        //Given nothing
        //When
        byte[] actual = subject.generate(ClientErrorGenerator.Code.NOT_FOUND);
        //Then
        String expected = "404 Not Found";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generateGeneratesA400Response() {
        //Given nothing
        //When
        byte[] actual = subject.generate(ClientErrorGenerator.Code.BAD_REQUEST);
        //Then
        String expected = "400 Bad Request";
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
        String permittedMethods = "GET,OPTIONS,EXFOLIATE";
        //When
        byte[] actual = subject.generate405(permittedMethods);
        //Then
        String expected = "Allow: GET,OPTIONS,EXFOLIATE";
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

    @Test
    void generate401ReturnsA401StartLine() {
        //Given nothing
        //When
        byte[] actual = subject.generate401();
        //Then
        String expected = "401 Unauthorized";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generate401ReturnsAWWWAuthenticateHeader() {
        //Given nothing
        //When
        byte[] actual = subject.generate401();
        //Then
        String expected = "WWW-Authenticate: Basic realm=";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generate403ReturnsA403StartLine() {
        //Given nothing
        //When
        byte[] actual = subject.generate(ClientErrorGenerator.Code.FORBIDDEN);
        //Then
        String expected = "403 Forbidden";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generate418ReturnsA418StartLine() {
        //Given nothing
        //When
        byte[] actual = subject.generate418();
        //Then
        String expected = "418 I'm a teapot";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generate418ReturnsA418MessageBody() {
        //Given nothing
        //When
        byte[] actual = subject.generate418();
        //Then
        String expected = "\r\n\r\nI'm a teapot";
        assertTrue(new String(actual).contains(expected));
    }
}