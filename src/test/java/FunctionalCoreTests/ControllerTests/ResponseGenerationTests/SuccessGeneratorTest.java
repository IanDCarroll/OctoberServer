package FunctionalCoreTests.ControllerTests.ResponseGenerationTests;

import Filers.FileClerk;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.BodySetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.ETagHeaderSetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.SetCookieHeaderSetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.StartLineSetter;
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
        StartLineSetter startLineSetter = new StartLineSetter();
        BodySetter bodySetter = new BodySetter(fileClerk);
        SetCookieHeaderSetter setCookieHeaderSetter = new SetCookieHeaderSetter();
        ETagHeaderSetter eTagHeaderSetter = new ETagHeaderSetter();
        subject = new SuccessGenerator(startLineSetter, bodySetter, setCookieHeaderSetter, eTagHeaderSetter);
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
        String type = "Content-Type: text/";
        assertTrue(new String(actual).contains(length));
        assertTrue(new String(actual).contains(type));
    }

    @Test
    void generateReturnsA204StartLine() {
        //Given
        String ifMatch = "c001c0de";
        byte[] content = "Original content".getBytes();
        String uri = "/a-file-with-a-body";
        String fullPath = publicDir + uri;
        FileHelper.make(fullPath, content);
        //When
        byte[] actual = subject.generate(SuccessGenerator.Code.NO_CONTENT, uri, ifMatch);
        //Then
        FileHelper.delete(fullPath);
        String expected = "204 No Content";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generateReturnsAnEtagHeader() {
        //Given
        String ifMatch = "c001c0de";
        byte[] content = "Original content".getBytes();
        String uri = "/a-file-with-a-body";
        String fullPath = publicDir + uri;
        FileHelper.make(fullPath, content);
        //When
        byte[] actual = subject.generate(SuccessGenerator.Code.NO_CONTENT, uri, ifMatch);
        //Then
        FileHelper.delete(fullPath);
        String expected = "ETag: " + ifMatch;
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generateSetCookieReturnsASetCookieHeaderAndABodyThatSaysEat() {
        //Given nothing
        //When
        byte[] actual = subject.generateSetCookie(SuccessGenerator.Code.OK);
        //Then
        String cookie = "Set-Cookie: ";
        assertTrue(new String(actual).contains(cookie));
        String body = "Eat";
        assertTrue(new String(actual).contains(body));
    }

    @Test
    void generateGetCookieSendsBackABodyThatIncludesmmmm_chocolate() {
        //Given
        String uri = "/a-uri-that-goes-to-nowhere";
        //When
        byte[] actual = subject.generateGetCookie(SuccessGenerator.Code.OK, uri);
        //Then
        String body = "mmmm chocolate";
        assertTrue(new String(actual).contains(body));
    }

}