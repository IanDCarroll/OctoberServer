package FunctionalCoreTests;

import FunctionalCore.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestTest {
    public Request subject;
    public final String notSet = "Not Set";

    @BeforeEach
    void init() {
        subject = new Request();
    }

    @Test
    void setMethodSetsTheHttpMethod() {
        //Given
        String method = "GET";
        //When
        subject.setMethod(method);
        //Then
        assertEquals(method, subject.getMethod());
    }

    @Test
    void setUriSetsTheUri() {
        //Given
        String uri = "/any-arbitrary-string";
        //When
        subject.setUri(uri);
        //Then
        assertEquals(uri, subject.getUri());
    }

    @Test
    void setHttpVSetsTheHttpV() {
        //Given
        String httpV = "HTTP/1.1";
        //When
        subject.setHttpV(httpV);
        //Then
        assertEquals(httpV, subject.getHttpV());
    }

    @Test
    void setBodySetsTheBody() {
        //Given
        byte[] body = "Any Arbitrary Array of Bytes".getBytes();
        //When
        subject.setBody(body);
        //Then
        assertArrayEquals(body, subject.getBody());
    }

    @Test
    void addUriParamAddsOneUriParamTotheLinkedListOfParams() {
        //Given
        String[] expected = { "A string representing an arbitrary uriParam" };
        //When
        subject.setUriParams(expected);
        //Then
        assertArrayEquals(expected, subject.getUriParams());
    }

    @Test
    void addUriParamCanBeCalledAnArbitraryNumberOfTimesToPopulateTheLinkedListOfParams() {
        //Given
        String[] expected = { "param1", "param2", "param3" };
        //When
        subject.setUriParams(expected);
        //Then
        assertArrayEquals(expected, subject.getUriParams());
    }

    @Test
    void setHeadersAddsOneHeaderToTheLinkedListOfHeaders() {
        //Given
        String[] expected = { "A string representing an arbitrary header" };
        //When
        subject.setHeaders(expected);
        //Then
        assertArrayEquals(expected, subject.getHeaders());
    }

    @Test
    void setHeadersAcceptsAnArbitraryNumberOfHeaders() {
        //Given
        String[] expected = { "header1", "header2", "header3" };
        //When
        subject.setHeaders(expected);
        //Then
        assertArrayEquals(expected, subject.getHeaders());
    }

    @Test
    void getMethodGetsANotSetMessageIfItHasntBeenSet() {
        //When
        String actual = subject.getMethod();
        //Then
        assertEquals(notSet, actual);
    }

    @Test
    void getUriGetsANotSetMessageIfItHasntBeenSet() {
        //When
        String actual = subject.getUri();
        //Then
        assertEquals(notSet, actual);
    }

    @Test
    void getUriParamsReturnsAnEmptyStringArrayIfNoParamsWereAdded() {
        //Given
        String[] expected = {};
        //When
        String[] actual = subject.getUriParams();
        //Then
        assertArrayEquals(expected, actual);
    }

    @Test
    void getHttpVGetsANotSetMessageIfItHasntBeenSet() {
        //When
        String actual = subject.getHttpV();
        //Then
        assertEquals(notSet, actual);
    }

    @Test
    void getHeadersReturnsAnEmptyStringArrayIfNoHeadersWereAdded() {
        //Given
        String[] expected = {};
        //When
        String[] actual = subject.getHeaders();
        //Then
        assertArrayEquals(expected, actual);
    }

    @Test
    void getBodyGetsANotSetMessageIfItHasntBeenSet() {
        //Given
        byte[] expected = notSet.getBytes();
        //When
        byte[] actual = subject.getBody();
        //Then
        assertArrayEquals(expected, actual);
    }
}