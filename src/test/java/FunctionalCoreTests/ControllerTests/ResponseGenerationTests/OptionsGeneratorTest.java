package FunctionalCoreTests.ControllerTests.ResponseGenerationTests;

import Factory.ServerFactory;
import FunctionalCore.Controller.ResponseGeneration.OptionsGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OptionsGeneratorTest {
    private int port = 5000;
    private String directory = System.getProperty("user.dir") + "/src/test/java/Mocks";
    private String configFile = directory + "/mock_routes.yml";
    OptionsGenerator subject;

    @BeforeEach
    void setup() {
        ServerFactory factory = new ServerFactory(port, directory, configFile);
        subject = factory.buildOptionsGenerator();
    }

    @Test
    void generateReturnsAnAllowHeader() {
        //Given
        String permittedMethods = "GET,OPTIONS,EAT_SPAGETTIOS";
        //When
        byte[] actual = subject.generate(OptionsGenerator.Code.OK, permittedMethods);
        //Then
        String expected = "Allow: " + permittedMethods;
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generateReturns200() {
        //Given
        String permittedMethods = "GET,OPTIONS,EAT_SPAGETTIOS";
        //When
        byte[] actual = subject.generate(OptionsGenerator.Code.OK, permittedMethods);
        //Then
        String expected = "200 OK";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generateReturns405() {
        //Given
        String permittedMethods = "GET,OPTIONS,EAT_SPAGETTIOS";
        //When
        byte[] actual = subject.generate(OptionsGenerator.Code.METHOD_NOT_ALLOWED, permittedMethods);
        //Then
        String expected = "405 Method Not Allowed";
        assertTrue(new String(actual).contains(expected));
    }
}