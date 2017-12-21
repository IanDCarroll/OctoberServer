package FunctionalCoreTests.ControllerTests.ResponseGenerationTests;

import FunctionalCore.Controller.ResponseGeneration.Response;
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
        String expected = "HTTP/1.1 999 Legit Response" +
                "\nChill-Header: chill out of ten" +
                "\nSick-Header: do you even lift?" +
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
        byte[] expected = "HTTP/1.1 999 Legit Response".getBytes();
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

    @Test
    void setBodySetsTheBodyToAGivenByteArray() {
        //Given
        byte[] body = "Any arbitrary byte array".getBytes();
        //When
        subject.setBody(body);
        //Then
        assertTrue(new String(subject.getResponse()).contains(new String(body)));
    }

    @Test
    void setBodySetsTheBodyToAGivenStringArray() {
        //Given
        String[] params = { "Cool-Param=pretty cool",
                "Awesome-Param=could be more awesome",
                "Radical-Param=totally rad" };
        //When
        subject.setBody(params);
        //Then
        String expected = "Cool-Param = pretty cool\n" +
                "Awesome-Param = could be more awesome\n" +
                "Radical-Param = totally rad\n";
        assertTrue(new String(subject.getResponse()).contains(expected));
    }

    @Test
    void setBodySetsTheBodyToAGivenByteArrayAndAddsFormattedParams() {
        //Given
        byte[] body = "Any arbitrary byte array".getBytes();
        String[] params = { "Cool-Param=pretty cool",
                "Awesome-Param=could be more awesome",
                "Radical-Param=totally rad" };
        //When
        subject.setBody(body, params);
        //Then
        String expected = "Cool-Param = pretty cool\n" +
                "Awesome-Param = could be more awesome\n" +
                "Radical-Param = totally rad\n" +
                 new String(body);
        assertTrue(new String(subject.getResponse()).contains(expected));
    }

    @Test
    void bodyLengthReturnsAStringRepresentingTheNumberOfBytesInTheBody() {
        //Given
        subject.setBody("RAGNAROK".getBytes());
        //When
        String actual = subject.bodyLength();
        //Then
        assertEquals("8", actual);
    }

    @Test
    void bodyLengthReturnsAZeroStringIfThereIsNoBodyWasSet() {
        //Given no body set
        //When
        String actual = subject.bodyLength();
        //Then
        String expected = "0";
        assertEquals(expected, actual);
    }
}