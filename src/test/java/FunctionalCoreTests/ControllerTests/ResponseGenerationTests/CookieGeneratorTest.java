package FunctionalCoreTests.ControllerTests.ResponseGenerationTests;

import Factory.ServerFactory;
import FunctionalCore.Controller.ResponseGeneration.CookieGenerator;
import FunctionalCore.Controller.ResponseGeneration.SuccessGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CookieGeneratorTest {
    private int port = 5000;
    private String directory = System.getProperty("user.dir") + "/src/test/java/Mocks";
    private String configFile = directory + "/mock_routes.yml";
    CookieGenerator subject;

    @BeforeEach
    void setup() {
        ServerFactory factory = new ServerFactory(port, directory, configFile);
        subject = factory.buildCookieGenerator();
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