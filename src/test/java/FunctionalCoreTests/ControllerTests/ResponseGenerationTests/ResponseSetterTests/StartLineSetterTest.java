package FunctionalCoreTests.ControllerTests.ResponseGenerationTests.ResponseSetterTests;

import Factory.ServerFactory;
import FunctionalCore.Controller.ResponseGeneration.Response;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.StartLineSetter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StartLineSetterTest {
    private int port = 5000;
    private String directory = System.getProperty("user.dir") + "/src/test/java/Mocks";
    private String configFile = directory + "/mock_routes.yml";
    StartLineSetter subject;

    @BeforeEach
    void setup() {
        ServerFactory factory = new ServerFactory(port, directory, configFile);
        subject = factory.buildStartLineSetter();
    }

    @Test
    void setStartLineReturnsAResponseWithASetStartLine() {
        //Given
        Response emptyResponse = new Response();
        String[] codeTuple = { "999", "Legit Response" };
        //When
        Response actual = subject.setStartLine(emptyResponse, codeTuple);
        //Then
        String expected = "999 Legit Response";
        assertTrue(new String(actual.getResponse()).contains(expected));
    }

}