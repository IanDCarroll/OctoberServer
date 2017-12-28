package FunctionalCoreTests.ControllerTests.ResponseGenerationTests;

import FunctionalCore.Controller.ResponseGeneration.AuthenticateHeaderSetter;
import FunctionalCore.Controller.ResponseGeneration.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticateHeaderSetterTest {
    AuthenticateHeaderSetter subject;

    @BeforeEach
    void setup() {
        subject = new AuthenticateHeaderSetter();
    }

    @Test
    void setWWWAuthReturnsAResponseWithAnAuthenticateHeader() {
        //Given
        Response emptyResponse = new Response();
        //When
        Response actual = subject.setWWWAuth(emptyResponse);
        //Then
        String expected = "WWW-Authenticate: Basic realm=\"Access to URI\"";
        assertTrue(new String(actual.getResponse()).contains(expected));
    }

}