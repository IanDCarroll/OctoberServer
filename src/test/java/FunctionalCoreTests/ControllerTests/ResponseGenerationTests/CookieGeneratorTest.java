package FunctionalCoreTests.ControllerTests.ResponseGenerationTests;

import Filers.FileClerk;
import FunctionalCore.Controller.ResponseGeneration.CookieGenerator;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.BodySetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.SetCookieHeaderSetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.StartLineSetter;
import FunctionalCore.Controller.ResponseGeneration.SuccessGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CookieGeneratorTest {
    CookieGenerator subject;
    private String publicDir = System.getProperty("user.dir") + "/src/test/java/Mocks";

    @BeforeEach
    void setup() {
        StartLineSetter startLineSetter = new StartLineSetter();
        FileClerk fileClerk = new FileClerk(publicDir);
        BodySetter bodySetter = new BodySetter(fileClerk);
        SetCookieHeaderSetter setCookieHeaderSetter = new SetCookieHeaderSetter();
        subject = new CookieGenerator(startLineSetter, bodySetter, setCookieHeaderSetter);
    }

    @Test
    void generateSetGeneratesASuccessResponseWithASetCookieAndEat() {
        //Given nothing
        //When
        byte[] actual = subject.generateSet(SuccessGenerator.Code.OK);
        //Then
        String cookie = "Set-Cookie: ";
        assertTrue(new String(actual).contains(cookie));
        String body = "Eat";
        assertTrue(new String(actual).contains(body));
    }

    @Test
    void generateGetGeneratesMMMChocolate() {
        //Given
        String uri = "/a-uri-that-goes-to-nowhere";
        //When
        byte[] actual = subject.generateGet(SuccessGenerator.Code.OK, uri);
        //Then
        String body = "mmmm chocolate";
        assertTrue(new String(actual).contains(body));
    }

}