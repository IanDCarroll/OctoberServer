package FunctionalCoreTests.ControllerTests;

import FunctionalCore.Controller.Controller;
import FunctionalCore.Request;
import FunctionalCore.Controller.ResponseGenerator;
import Helpers.FileHelper;
import Mocks.MockRequestDealer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private Controller subject;
    private LinkedHashMap mockRoutes;
    private String publicDir;

    @BeforeEach
    void init() {
        ResponseGenerator responseGenerator = new ResponseGenerator();
        mockRoutes = new LinkedHashMap();
        publicDir = System.getProperty("user.dir") + "/src/test/java/Mocks";
        subject = new Controller(responseGenerator, mockRoutes, publicDir);
    }

    @Test
    void getAppropriateResponseTakesARequestObjectAndReturnsAnAppropriateByteArray() {
        //Given
        mockRoutes.put("/", "GET");
        Request request = MockRequestDealer.getRootRequest();
        //When
        byte[] actual = subject.getAppropriateResponse(request);
        //Then
        String expected = "HTTP/1.1 200 OK";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void getAppropriateResponseReturnsA404IfTheUriIsNotFoundInRoutes() {
        //Given
        mockRoutes.put("/not-found", "unused info for this operation");
        Request request = MockRequestDealer.getRootRequest();
        //When
        byte[] actual = subject.getAppropriateResponse(request);
        //Then
        String expected = "HTTP/1.1 404 Not Found";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void getAppropriateResponseReturns405IfTheMethodIsNotAllowed() {
        //Given
        mockRoutes.put("/", "POST");
        Request request = MockRequestDealer.getRootRequest();
        //When
        byte[] actual = subject.getAppropriateResponse(request);
        //Then
        String expected = "HTTP/1.1 405 Method Not Allowed";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void getAppropriateResponseReturnsOnlyTheHeadForAHEADRequest() {
        //Given
        mockRoutes.put("/", "HEAD");
        Request request = MockRequestDealer.headHeaderRequest();
        //When
        byte[] actual = subject.getAppropriateResponse(request);
        //Then
        String unExpected = "param1";
        assertFalse(new String(actual).contains(unExpected));
    }

    @Test
    void getAppropriateResponseReturnsAnAllowHeaderForAnOPTIONSRequest() {
        //Given
        mockRoutes.put("/", "GET HEAD OPTIONS");
        Request request = MockRequestDealer.optionsRequest();
        //When
        byte[] actual = subject.getAppropriateResponse(request);
        //Then
        String expected = "Allow: GET,HEAD,OPTIONS";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void getAppropriateResponseReturnsTheBodyForAGETRequest() {
        //Given
        String uri = "/a-file-to-get";
        String name = publicDir + uri;
        byte[] content = "Original content".getBytes();
        FileHelper.make(name, content);
        Request request = MockRequestDealer.getRequest(uri);
        mockRoutes.put(request.getUri(), "GET");
        //When
        byte[] actual = subject.getAppropriateResponse(request);
        //Then
        FileHelper.delete(name);
        String expected = new String(content);
        //System.out.println(new String(actual));
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void getAppropriateResponseDeletesTheBodyForADELETERequest() {
        //Given
        String uri = "/this-will-be-deleted";
        String name = publicDir + uri;
        byte[] content = "Original content".getBytes();
        FileHelper.make(name, content);
        Request get = MockRequestDealer.getRequest(uri);
        Request delete = MockRequestDealer.deleteRequest(uri);
        mockRoutes.put(get.getUri(), "GET DELETE");
        //When
        byte[] beforeDelete = subject.getAppropriateResponse(get);
        byte[] duringDelete = subject.getAppropriateResponse(delete);
        byte[] afterDelete = subject.getAppropriateResponse(get);
        //Then
        FileHelper.delete(name);
        String expected = new String(content);
        assertTrue(new String(beforeDelete).contains(expected));
        assertFalse(new String(duringDelete).contains(expected));
        assertFalse(new String(afterDelete).contains(expected));
    }

    @Test
    void getAppropriateResponseReplacesTheBodyForAPUTRequest() {
        //Given
        String uri = "/this-will-be-deleted";
        String name = publicDir + uri;
        byte[] content = "Original content".getBytes();
        FileHelper.make(name, content);
        Request get = MockRequestDealer.getRequest(uri);
        Request put = MockRequestDealer.putRequest(uri);
        mockRoutes.put(get.getUri(), "GET PUT");
        //When
        byte[] beforePut = subject.getAppropriateResponse(get);
        byte[] duringPut = subject.getAppropriateResponse(put);
        byte[] afterPut = subject.getAppropriateResponse(get);
        //Then
        FileHelper.delete(name);
        String expectedBefore = new String(content);
        String expectedAfter = new String(put.getBody());
        assertTrue(new String(beforePut).contains(expectedBefore));
        assertTrue(new String(duringPut).contains(expectedAfter));
        assertFalse(new String(duringPut).contains(expectedBefore));
        assertTrue(new String(afterPut).contains(expectedAfter));
        assertFalse(new String(afterPut).contains(expectedBefore));
    }
}