package FunctionalCoreTests.ControllerTests.ResponseGenerationTests.ResponseSetterTests.HeaderSetterTests;

import FunctionalCore.Controller.ResponseGeneration.Response;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.ContentLengthHeaderSetter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContentLengthHeaderSetterTest {
    ContentLengthHeaderSetter subject;

    @BeforeEach
    void setUp() {
        subject = new ContentLengthHeaderSetter();
    }

    @Test
    void setContentLengthReturnsAResponseWithAContentLengthHeaderWhenPassedALengthString() {
        //Given
        Response emptyResponse = new Response();
        String lengthValue = "525600";
        //When
        Response actual = subject.setContentLength(emptyResponse, lengthValue);
        //Then
        String expected = "Content-Length: " + lengthValue;
        assertTrue(new String(actual.getResponse()).contains(expected));
    }
}