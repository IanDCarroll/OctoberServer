package FunctionalCoreTests.ControllerTests;

import FunctionalCore.Controller.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseTest {
    Response subject;

    @BeforeEach
    void setup() {
        this.subject = new Response();
    }

    @Test
    void getResponseReturnsAValidByteArray() {
        //Given
        String[] code = { "999", "Legit Response" };
        String[] headers = { "Chill-Header", "chill out of ten",
                            "Sick-Header", "do you even lift?" };
        byte[] body = "Shall I compare thee to a winter's day?".getBytes();
        //When
        subject.setStartLine( code[0], code[1]);
        subject.setHeaders(headers);
        subject.setBody(body);
        //Then
        byte[] actual = subject.getResponse();
        String expected = "HTTP/1.1 999 Legit Response\n" +
                "Chill-Header: chill out of ten\n" +
                "Sick-Header: do you even lift?\n" +
                "\r\n\r\n" +
                "Shall I compare thee to a winter's day?";
        assertEquals(expected, new String(actual));
    }

    @Test
    void setStartLineBuildsAStartLineFromACodeAndMessage() {
        //Given
        String[] codeTuple = { "999", "Legit Response" };
        //When
        subject.setStartLine(codeTuple[0], codeTuple[1]);
        //Then
        byte[] expected = "HTTP/1.1 999 Legit Response\n".getBytes();
        byte[] actual = subject.getResponse();
        assertTrue(new String(actual).contains(new String(expected)));
    }

    @Test
    void setHeadersBuildsHeaders() {
        //Given
        String[] headerInput = { "Cool-Header", "pretty cool",
                "Awesome-Header", "could be more awesome",
                "Radical-Header", "totally rad" };
        //When
        subject.setHeaders(headerInput);
        //Then
        byte[] expected = ("Cool-Header: pretty cool\n" +
                "Awesome-Header: could be more awesome\n" +
                "Radical-Header: totally rad").getBytes();
        byte[] actual = subject.getResponse();
        assertTrue(new String(actual).contains(new String(expected)));
    }

    @Test
    void setHeadersLetsYouKnowIfYouGaveItOddInput() {
        //Given
        String[] oddInput = { "Cool-Header", "pretty cool",
                "Awesome-Header", "could be more awesome",
                "Radical-Header" };
        //When
        Throwable exception = assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            subject.setHeaders(oddInput);
        });
        //Then
        String expected = "Response Object received an odd Array length";
        assertEquals(expected, exception.getMessage());
    }
}