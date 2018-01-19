package FunctionalCoreTests.ControllerTests.ResponseGenerationTests;

import Factory.ServerFactory;
import FunctionalCore.Controller.ResponseGeneration.RedirectionGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RedirectionGeneratorTest {
    private int port = 5000;
    private String directory = System.getProperty("user.dir") + "/src/test/java/Mocks";
    private String configFile = directory + "/mock_routes.yml";
    private RedirectionGenerator subject;

    @BeforeEach
    void setup() {
        ServerFactory factory = new ServerFactory(port, directory, configFile);
        subject = factory.buildRedirectionGenerator();
    }

    @Test
    void generate302ReturnsALocationHeader() {
        //Given
        String redirectToThisUri = "/the-uri-redirect-location";
        //When
        byte[] actual = subject.generate(RedirectionGenerator.Code.FOUND, redirectToThisUri);
        //Then
        String code = RedirectionGenerator.Code.FOUND.tuple[0];
        String header = "Location: " + redirectToThisUri;
        assertTrue(new String(actual).contains(code));
        assertTrue(new String(actual).contains(header));
    }

}