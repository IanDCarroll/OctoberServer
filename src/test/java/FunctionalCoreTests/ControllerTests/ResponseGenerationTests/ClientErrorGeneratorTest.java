package FunctionalCoreTests.ControllerTests.ResponseGenerationTests;

import Factory.ServerFactory;
import FunctionalCore.Controller.ResponseGeneration.ClientErrorGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientErrorGeneratorTest {
    private int port = 5000;
    private String directory = System.getProperty("user.dir") + "/src/test/java/Mocks";
    private String configFile = directory + "/mock_routes.yml";
    private ClientErrorGenerator subject;

    @BeforeEach
    void init() {
        ServerFactory factory = new ServerFactory(port, directory, configFile);
        subject = factory.buildClientErrorGenerator();
    }

    @Test
    void generateGeneratesA404Response() {
        //Given nothing
        //When
        byte[] actual = subject.generate(ClientErrorGenerator.Code.NOT_FOUND);
        //Then
        String expected = "404 Not Found";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generateGeneratesA400Response() {
        //Given nothing
        //When
        byte[] actual = subject.generate(ClientErrorGenerator.Code.BAD_REQUEST);
        //Then
        String expected = "400 Bad Request";
        assertTrue(new String(actual).contains(expected));
    }
}