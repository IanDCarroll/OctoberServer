package FunctionalCoreTests.ControllerTests.SubControllerTests;

import FunctionalCore.Controller.SubControllers.RedirectionController;
import FunctionalCore.Controller.ResponseGeneration.RedirectionGenerator;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.LocationHeaderSetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.StartLineSetter;
import FunctionalCore.Request;
import Mocks.MockRequestDealer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class RedirectionControllerTest {
    RedirectionController subject;
    LinkedHashMap<String, String> properties;
    LinkedHashMap<String, LinkedHashMap<String, String>> routes;

    @BeforeEach
    void setup() {
        properties = new LinkedHashMap<>();
        routes = new LinkedHashMap<>();
        StartLineSetter startLineSetter = new StartLineSetter();
        LocationHeaderSetter locationHeaderSetter = new LocationHeaderSetter();
        RedirectionGenerator redirectionGenerator = new RedirectionGenerator(startLineSetter, locationHeaderSetter);
        subject = new RedirectionController(redirectionGenerator);
    }

    @Test
    void relevantReturnsTrueIfTheRouteHasARedirectionProperty() {
        //Given
        String uri = "/flagged-for-redirection";
        properties.put("redirect-uri", "/redirect-to-here");
        routes.put(uri, properties);
        Request request = MockRequestDealer.getRequest(uri);
        //When
        boolean actual = subject.relevant(request, routes);
        //Then
        assertTrue(actual);
    }

    @Test
    void relevantReturnsFalseIfTheRouteDoesNOtHaveARedirectProperty() {
        //Given
        String uri = "/not-flagged";
        properties.put("allowed-methods", "OPTIONS,HEAD");
        routes.put(uri, properties);
        Request request = MockRequestDealer.getRequest(uri);
        //When
        boolean actual = subject.relevant(request, routes);
        //Then
        assertFalse(actual);
    }

   @Test
    void generateReturnsA302Response() {
        //Given
        String uri = "/flagged-for-redirection";
        properties.put("redirect-uri", "/redirect-to-here");
        routes.put(uri, properties);
        Request request = MockRequestDealer.getRequest(uri);
        //When
        byte[] actual = subject.generate(request, routes);
        //Then
        String expected = "302 Found";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generateReturnsTheRedirectUri() {
        //Given
        String uri = "/flagged-for-redirection";
        String redirectUri = "/redirect-to-here";
        properties.put("redirect-uri", redirectUri);
        routes.put(uri, properties);
        Request request = MockRequestDealer.getRequest(uri);
        //When
        byte[] actual = subject.generate(request, routes);
        //Then
        assertTrue(new String(actual).contains(redirectUri));
    }
}