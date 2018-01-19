package FunctionalCoreTests.ControllerTests;

import Factory.ServerFactory;
import FunctionalCore.Controller.*;
import FunctionalCore.Request;
import Helpers.FileHelper;
import Mocks.MockRequestDealer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private int port = 5000;
    private String directory = System.getProperty("user.dir") + "/src/test/java/Mocks";
    private String configFile = directory + "/mock_routes.yml";
    private Controller subject;

    @BeforeEach
    void init() {
        ServerFactory factory = new ServerFactory(port, directory, configFile);
        subject = factory.buildController();
    }

    @Test
    void getAppropriateResponseTakesARequestObjectAndReturnsAnAppropriateByteArray() {
        //Given
        Request request = MockRequestDealer.getRootRequest();
        //When
        byte[] actual = subject.getAppropriateResponse(request);
        //Then
        String expected = "HTTP/1.1 200 OK";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void getAppropriateResponseReturnsA404IfTheUriIsNotFoundInRoutes() {
        //Given (no route in mocks for "/not-found")
        String uri = "/not-found";
        Request request = MockRequestDealer.getRequest(uri);
        //When
        byte[] actual = subject.getAppropriateResponse(request);
        //Then
        String expected = "HTTP/1.1 404 Not Found";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void getAppropriateResponseReturns405IfTheMethodIsNotAllowed() {
        //Given
        String uri = "/options-only";
        Request request = MockRequestDealer.getRequest(uri);
        //When
        byte[] actual = subject.getAppropriateResponse(request);
        //Then
        String expected = "HTTP/1.1 405 Method Not Allowed";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void getAppropriateResponseReturnsOnlyTheHeadForAHEADRequest() {
        //Given
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
        Request request = MockRequestDealer.optionsRequest();
        //When
        byte[] actual = subject.getAppropriateResponse(request);
        //Then
        String expected = "Allow: GET,OPTIONS";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void getAppropriateResponseReturnsTheBodyForAGETRequest() {
        //Given
        String uri = "/a-file-to-get";
        String fullPath = directory + uri;
        byte[] content = "Original content".getBytes();
        FileHelper.make(fullPath, content);
        Request request = MockRequestDealer.getRequest(uri);
        //When
        byte[] actual = subject.getAppropriateResponse(request);
        //Then
        FileHelper.delete(fullPath);
        String expected = new String(content);
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void getAppropriateResponseDeletesTheBodyForADELETERequest() {
        //Given
        String uri = "/this-will-be-deleted";
        String fullPath = directory + uri;
        byte[] content = "Original content".getBytes();
        FileHelper.make(fullPath, content);
        Request get = MockRequestDealer.getRequest(uri);
        Request delete = MockRequestDealer.deleteRequest(uri);
        //When
        byte[] beforeDelete = subject.getAppropriateResponse(get);
        byte[] duringDelete = subject.getAppropriateResponse(delete);
        byte[] afterDelete = subject.getAppropriateResponse(get);
        //Then
        FileHelper.delete(fullPath);
        String expected = new String(content);
        assertTrue(new String(beforeDelete).contains(expected));
        assertFalse(new String(duringDelete).contains(expected));
        assertFalse(new String(afterDelete).contains(expected));
    }

    @Test
    void getAppropriateResponseReplacesTheBodyForAPUTRequest() {
        //Given
        String uri = "/this-will-be-replaced";
        String fullPath = directory + uri;
        byte[] content = "Original content".getBytes();
        FileHelper.make(fullPath, content);
        Request get = MockRequestDealer.getRequest(uri);
        Request put = MockRequestDealer.putRequest(uri);
        //When
        byte[] beforePut = subject.getAppropriateResponse(get);
        byte[] duringPut = subject.getAppropriateResponse(put);
        byte[] afterPut = subject.getAppropriateResponse(get);
        //Then
        FileHelper.delete(fullPath);
        String expectedBefore = new String(content);
        String expectedAfter = new String(put.getBody());
        assertTrue(new String(beforePut).contains(expectedBefore));
        assertTrue(new String(duringPut).contains(expectedAfter));
        assertFalse(new String(duringPut).contains(expectedBefore));
        assertTrue(new String(afterPut).contains(expectedAfter));
        assertFalse(new String(afterPut).contains(expectedBefore));
    }

    @Test
    void getAppropriateResponseAppendsTheBodyForAPOSTRequest() {
        //Given
        String uri = "/this-will-be-added-to";
        String fullPath = directory + uri;
        byte[] content = "Original content".getBytes();
        FileHelper.make(fullPath, content);
        Request get = MockRequestDealer.getRequest(uri);
        Request post = MockRequestDealer.postRequest(uri);
        //When
        byte[] beforePost = subject.getAppropriateResponse(get);
        byte[] duringPost1 = subject.getAppropriateResponse(post);
        byte[] afterPost1 = subject.getAppropriateResponse(get);
        byte[] duringPost2 = subject.getAppropriateResponse(post);
        byte[] afterPosts = subject.getAppropriateResponse(get);
        //Then
        FileHelper.delete(fullPath);
        String postBody = new String(post.getBody());
        String expectedBefore = new String(content);
        String expectedAfter1 = expectedBefore + postBody;
        String expectedAfter2 = expectedAfter1 + postBody;
        assertTrue(new String(beforePost).contains(expectedBefore));
        assertTrue(new String(duringPost1).contains(expectedAfter1));
        assertTrue(new String(afterPost1).contains(expectedAfter1));
        assertTrue(new String(duringPost2).contains(expectedAfter2));
        assertTrue(new String(afterPosts).contains(expectedAfter2));
    }

    @Test
    void getAppropriateResponseReturns206IfThereIsARangeHeader() {
        //Given
        String uri = "/a-file-for-rangers";
        String fullPath = directory + uri;
        byte[] content = "Original content".getBytes();
        FileHelper.make(fullPath, content);
        Request request = MockRequestDealer.partialRequest(uri);
        //When
        byte[] actual = subject.getAppropriateResponse(request);
        //Then
        FileHelper.delete(fullPath);
        String expected = "HTTP/1.1 206 Partial Content";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void getAppropriateResponseReturns416IfTheRangeHeaderIsNotGood() {
        //Given
        String uri = "/a-file-for-rangers";
        String fullPath = directory + uri;
        byte[] content = "Original content".getBytes();
        FileHelper.make(fullPath, content);
        Request request = MockRequestDealer.badPartialRequest(uri);
        //When
        byte[] actual = subject.getAppropriateResponse(request);
        //Then
        FileHelper.delete(fullPath);
        String expected = "HTTP/1.1 416 Range Not Satisfiable";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void getAppropriateResponseReturns302IfTheRedirectUriIsNotAnEmptyString() {
        //Given
        String uri = "/a-uri-marked-for-redirection";
        Request request = MockRequestDealer.getRequest(uri);
        //When
        byte[] actual = subject.getAppropriateResponse(request);
        //Then
        String expected = "HTTP/1.1 302 Found";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void getAppropriateResponseReturns200IfRequestIsAuthorized() {
        //Given
        String uri = "/a-uri-that-needs-authorization";
        Request request = MockRequestDealer.authRequest(uri);
        //When
        byte[] actual = subject.getAppropriateResponse(request);
        //Then
        String expected = "HTTP/1.1 200 OK";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void getAppropriateResponseReturns401IfRequestHasNoAuthHeader() {
        //Given
        String uri = "/a-uri-that-needs-authorization";
        Request request = MockRequestDealer.getRequest(uri);
        //When
        byte[] actual = subject.getAppropriateResponse(request);
        //Then
        String expected = "HTTP/1.1 401 Unauthorized";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void getAppropriateResponseReturns403IfRequestHasIncorrectAuthHeader() {
        //Given
        String uri = "/a-uri-that-wont-pass-authorization";
        Request request = MockRequestDealer.authRequest(uri);
        //When
        byte[] actual = subject.getAppropriateResponse(request);
        //Then
        String expected = "HTTP/1.1 403 Forbidden";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void getAppropriateResponseReturns418IfRequestIsForCoffee() {
        //Given
        String uri = "/coffee";
        Request request = MockRequestDealer.getRequest(uri);
        //When
        byte[] actual = subject.getAppropriateResponse(request);
        //Then
        String expected = "HTTP/1.1 418 I'm a teapot";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void getAppropriateResponseReturns204IfRequestMethodIsPatch() {
        //Given
        String uri = "/patch-this";
        String fullPath = directory + uri;
        String ifMatch = "900dE7a9";
        Request request = MockRequestDealer.patchRequest(uri, ifMatch);
        //When
        byte[] actual = subject.getAppropriateResponse(request);
        //Then
        FileHelper.delete(fullPath);
        String startLine = "204 No Content";
        assertTrue(new String(actual).contains(startLine));
        assertTrue(new String(actual).contains(ifMatch));
    }

    @Test
    void getAppropriateResponseReturns400ForBadParams() {
        //Given
        String uri = "/";
        Request request = MockRequestDealer.badParamRequest(uri);
        //When
        byte[] actual = subject.getAppropriateResponse(request);
        //Then
        String expected = "400 Bad Request";
        assertTrue(new String(actual).contains(expected));
    }

}