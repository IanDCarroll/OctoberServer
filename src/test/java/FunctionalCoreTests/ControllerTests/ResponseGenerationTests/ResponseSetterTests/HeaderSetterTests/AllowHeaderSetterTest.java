package FunctionalCoreTests.ControllerTests.ResponseGenerationTests.ResponseSetterTests.HeaderSetterTests;

import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.AllowHeaderSetter;
import FunctionalCore.Controller.ResponseGeneration.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AllowHeaderSetterTest {
    AllowHeaderSetter subject;

    @BeforeEach
    void setup() {
        subject = new AllowHeaderSetter();
    }

    @Test
    void setAllowReturnsAResponseWithAnAllowHeaderAndWhatItAllows() {
        //Given
        Response emptyResponse = new Response();
        String allowed = "GET,OPTIONS,UNICORNIFY";
        //When
        Response actual = subject.setAllow(emptyResponse, allowed);
        //Then
        String expected = "Allow: " + allowed;
        assertTrue(new String(actual.getResponse()).contains(expected));
    }

}