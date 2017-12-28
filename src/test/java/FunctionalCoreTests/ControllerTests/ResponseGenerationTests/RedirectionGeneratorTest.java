package FunctionalCoreTests.ControllerTests.ResponseGenerationTests;

import FunctionalCore.Controller.ResponseGeneration.RedirectionGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RedirectionGeneratorTest {
    private RedirectionGenerator subject;
    private String publicDir = System.getProperty("user.dir") + "/src/test/java/Mocks";

    @BeforeEach
    void setup() {
        subject = new RedirectionGenerator();
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