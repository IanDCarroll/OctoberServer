package FunctionalCoreTests.ControllerTests.ResponseGenerationTests;

import FunctionalCore.Controller.ResponseGeneration.Response;
import FunctionalCore.Controller.ResponseGeneration.StartLineSetter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StartlineSetterTest {
    StartLineSetter subject;

    @BeforeEach
    void setup() {
        subject = new StartLineSetter();
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