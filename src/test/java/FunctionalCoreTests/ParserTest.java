package FunctionalCoreTests;

import FunctionalCore.Parser;
import FunctionalCore.Request;
import Mocks.MockRequestDealer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    public Parser subject;

    @BeforeEach
    void init() {
        subject = new Parser();
    }

    @Test
    void parseReturnsARequestObjectWithTheRightMethodFromAByteArray() {
        //Given
        byte[] source = "GET / HTTP/1.1\r\n\r\n".getBytes();
        //When
        String actual = subject.parse(source).getMethod();
        //Then
        String expected = MockRequestDealer.getRootRequest().getMethod();
        assertEquals(actual, expected);
    }

    @Test
    void parseReturnsARequestObjectWithTheRightUriFromAByteArray() {
        //Given
        byte[] source = "GET / HTTP/1.1\r\n\r\n".getBytes();
        //When
        String actual = subject.parse(source).getUri();
        //Then
        String expected = MockRequestDealer.getRootRequest().getUri();
        assertEquals(actual, expected);
    }

    @Test
    void parseReturnsARequestObjectWithTheRightHTTPVersionFromAByteArray() {
        //Given
        byte[] source = "GET / HTTP/1.1\r\n\r\n".getBytes();
        //When
        String actual = subject.parse(source).getHttpV();
        //Then
        String expected = MockRequestDealer.getRootRequest().getHttpV();
        assertEquals(actual, expected);
    }

    @Test
    void parseReturnsARequestObjectWithTheRightBodyFromAByteArray() {
        //Given
        byte[] source = "GET / HTTP/1.1\r\n\r\nThis represents a well-formed body".getBytes();
        //When
        byte[] actual = subject.parse(source).getBody();
        //Then
        byte[] expected = MockRequestDealer.getFullRequest().getBody();
        assertEquals(new String(expected), new String(actual));
    }

    @Test
    void parseReturnsARequestObjectWithAllTheHeadersFromAByteArray() {
        //Given
        byte[] source = ("GET / HTTP/1.1\n" +
                "Content-Type: text/plain\n" +
                "Content-Length: 34" +
                "\r\n\r\n" +
                "This represents a well-formed body").getBytes();
        //When
        String[] actual = subject.parse(source).getHeaders();
        //Then
        String[] expected = MockRequestDealer.getFullRequest().getHeaders();
        assertArrayEquals(expected, actual);
    }
}