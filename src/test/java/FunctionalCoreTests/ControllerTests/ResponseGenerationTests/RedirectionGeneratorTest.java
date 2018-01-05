package FunctionalCoreTests.ControllerTests.ResponseGenerationTests;

import FunctionalCore.Controller.ResponseGeneration.RedirectionGenerator;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.LocationHeaderSetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.StartLineSetter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RedirectionGeneratorTest {
    private RedirectionGenerator subject;

    @BeforeEach
    void setup() {
        StartLineSetter startLineSetter = new StartLineSetter();
        LocationHeaderSetter locationHeaderSetter = new LocationHeaderSetter();
        subject = new RedirectionGenerator(startLineSetter, locationHeaderSetter);
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