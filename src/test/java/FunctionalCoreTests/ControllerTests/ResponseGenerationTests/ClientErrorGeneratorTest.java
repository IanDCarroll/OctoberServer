package FunctionalCoreTests.ControllerTests.ResponseGenerationTests;

import Filers.FileClerk;
import FunctionalCore.Controller.ResponseGeneration.ClientErrorGenerator;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.StartLineSetter;
import Helpers.FileHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientErrorGeneratorTest {
    private ClientErrorGenerator subject;
    private String publicDir = System.getProperty("user.dir") + "/src/test/java/Mocks";

    @BeforeEach
    void init() {
        FileClerk fileClerk = new FileClerk(publicDir);
        StartLineSetter startLineSetter = new StartLineSetter();
        subject = new ClientErrorGenerator(startLineSetter);
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