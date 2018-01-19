package FunctionalCoreTests.ControllerTests.ResponseGenerationTests;

import Factory.ServerFactory;
import FunctionalCore.Controller.ResponseGeneration.AuthGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthGeneratorTest {
    private int port = 5000;
    private String directory = System.getProperty("user.dir") + "/src/test/java/Mocks";
    private String configFile = directory + "/mock_routes.yml";
    AuthGenerator subject;

    @BeforeEach
    void setup() {
        ServerFactory factory = new ServerFactory(port, directory, configFile);
        subject = factory.buildAuthGenerator();
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