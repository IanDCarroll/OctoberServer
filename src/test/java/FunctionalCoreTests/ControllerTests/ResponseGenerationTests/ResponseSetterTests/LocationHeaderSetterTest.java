package FunctionalCoreTests.ControllerTests.ResponseGenerationTests.ResponseSetterTests;

import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.LocationHeaderSetter;
import FunctionalCore.Controller.ResponseGeneration.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationHeaderSetterTest {
    LocationHeaderSetter subject;

    @BeforeEach
    void setup() {
        subject = new LocationHeaderSetter();
    }

    @Test
    void setRangeReturnsAResponseWithAStarredRangeHeaderWhenOnlyPassedAUri() {
        //Given
        Response emptyResponse = new Response();
        String testUri = "/this-is-where-it-is";
        //When
        Response actual = subject.setLocation(emptyResponse, testUri);
        //Then
        String expected = "Location: " + testUri;
        assertTrue(new String(actual.getResponse()).contains(expected));
    }

}