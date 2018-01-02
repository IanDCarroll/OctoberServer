package FunctionalCoreTests.ControllerTests.ResponseGenerationTests.ResponseSetterTests.HeaderSetterTests;

import FunctionalCore.Controller.ResponseGeneration.Response;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.ETagHeaderSetter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ETagHeaderSetterTest {
    ETagHeaderSetter subject;

    @BeforeEach
    void setup() {
        subject = new ETagHeaderSetter();
    }

    @Test
    void setSetCookieSetsTheSetCookieHeader() {
        //Given
        Response emptyResponse = new Response();
        String ifMatch = "bada55ba5ec5c0de";
        //When
        Response actual = subject.setETag(emptyResponse, ifMatch);
        //Then
        String expected = "ETag: " + ifMatch;
        assertTrue(new String(actual.getResponse()).contains(expected));
    }

    @Test
    void generateCookieGeneratesAStringThatIsNotEmpty() {
        //Given
        String ifMatch = "bada55ba5ec5c0de";
        //When
        String actual = subject.generateETag(ifMatch);
        //Then
        assertEquals(ifMatch, actual);
    }

}