package FunctionalCoreTests.ControllerTests.ResponseGenerationTests.ResponseSetterTests;

import FunctionalCore.Controller.ResponseGeneration.Response;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.SetCookieHeaderSetter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SetCookieHeaderSetterTest {
    SetCookieHeaderSetter subject;

    @BeforeEach
    void setup() {
        subject = new SetCookieHeaderSetter();
    }


    @Test
    void setSetCookieSetsTheSetCookieHeader() {
        //Given
        Response emptyResponse = new Response();
        //When
        Response actual = subject.setSetCookie(emptyResponse);
        //Then
        String expected = "Set-Cookie: ";
        assertTrue(new String(actual.getResponse()).contains(expected));
    }

    @Test
    void generateCookieGeneratesAStringThatIsNotEmpty() {
        //Given nothing
        //When
        String actual = subject.generateCookie();
        //Then
        assertFalse(actual.isEmpty());
    }
}