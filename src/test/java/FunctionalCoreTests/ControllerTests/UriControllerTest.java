package FunctionalCoreTests.ControllerTests;

import FunctionalCore.Controller.ResponseGeneration.ClientErrorGenerator;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.StartLineSetter;
import FunctionalCore.Controller.UriController;
import FunctionalCore.Request;
import Mocks.MockRequestDealer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class UriControllerTest {
    UriController subject;
    LinkedHashMap<String, String> properties;
    LinkedHashMap<String, LinkedHashMap<String, String>> routes;

    @BeforeEach
    void setup() {
        properties = new LinkedHashMap<>();
        routes = new LinkedHashMap<>();
        StartLineSetter startLineSetter = new StartLineSetter();
        ClientErrorGenerator clientErrorGenerator = new ClientErrorGenerator(startLineSetter);
        subject = new UriController(clientErrorGenerator);
    }

    @Test
    void relevantReturnsTrueIfTheRequestUriDoesNotMatchARoute() {
        //Given
        String uri = "/not-found";
        routes.put("/found", properties);
        Request request = MockRequestDealer.getRequest(uri);
        //When
        boolean actual = subject.relevant(request, routes);
        //Then
        assertTrue(actual);
    }

    @Test
    void relevantReturnsTrueIfAnyUriParamsDontHaveAKeyValueDelimiter() {
        //Given
        String uri = "/found";
        routes.put(uri, properties);
        Request request = MockRequestDealer.badParamRequest(uri);
        //When
        boolean actual = subject.relevant(request, routes);
        //Then
        assertTrue(actual);
    }

    @Test
    void relevantReturnsFalseIfTheRequestUriMatchesARoute() {
        //Given
        String uri = "/found";
        routes.put(uri, properties);
        Request request = MockRequestDealer.getRequest(uri);
        //When
        boolean actual = subject.relevant(request, routes);
        //Then
        assertFalse(actual);
    }

    @Test
    void generateGeneratesA404ResponseIfNotFound() {
        //Given
        String uri = "/not-found";
        Request request = MockRequestDealer.getRequest(uri);
        //When
        byte[] actual = subject.generate(request, routes);
        //Then
        String expected = "404 Not Found";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generateGeneratesA400ResponseIfBadParams() {
        //Given
        String uri = "/found";
        routes.put(uri, properties);
        Request request = MockRequestDealer.badParamRequest(uri);
        //When
        byte[] actual = subject.generate(request, routes);
        //Then
        String expected = "400 Bad Request";
        assertTrue(new String(actual).contains(expected));
    }
}