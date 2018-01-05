package FunctionalCoreTests.ControllerTests.ResponseGenerationTests;

import FunctionalCore.Controller.ResponseGeneration.AuthGenerator;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.AuthenticateHeaderSetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.StartLineSetter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthGeneratorTest {
    AuthGenerator subject;

    @BeforeEach
    void setup() {
        StartLineSetter startLineSetter = new StartLineSetter();
        AuthenticateHeaderSetter authenticateHeaderSetter = new AuthenticateHeaderSetter();
        subject = new AuthGenerator(startLineSetter, authenticateHeaderSetter);
    }

    @Test
    void generate401ReturnsA401StartLine() {
        //Given nothing
        //When
        byte[] actual = subject.generate401();
        //Then
        String expected = "401 Unauthorized";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generate401ReturnsAWWWAuthenticateHeader() {
        //Given nothing
        //When
        byte[] actual = subject.generate401();
        //Then
        String expected = "WWW-Authenticate: Basic realm=";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generate403ReturnsA403StartLine() {
        //Given nothing
        //When
        byte[] actual = subject.generate403();
        //Then
        String expected = "403 Forbidden";
        assertTrue(new String(actual).contains(expected));
    }

}