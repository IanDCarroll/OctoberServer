package FunctionalCoreTests.ControllerTests.SubControllerTests;

import Factory.ServerFactory;
import FunctionalCore.Controller.SubControllers.RedirectionController;
import FunctionalCore.Request;
import Mocks.MockRequestDealer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class RedirectionControllerTest {
    private int port = 5000;
    private String directory = System.getProperty("user.dir") + "/src/test/java/Mocks";
    private String configFile = directory + "/mock_routes.yml";
    RedirectionController subject;
    LinkedHashMap<String, String> properties;
    LinkedHashMap<String, LinkedHashMap<String, String>> routes;

    @BeforeEach
    void setup() {
        properties = new LinkedHashMap<>();
        routes = new LinkedHashMap<>();
        ServerFactory factory = new ServerFactory(port, directory, configFile);
        subject = factory.buildRedirectionController();
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