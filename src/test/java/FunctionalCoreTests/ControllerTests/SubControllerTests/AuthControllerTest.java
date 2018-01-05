package FunctionalCoreTests.ControllerTests.SubControllerTests;

import FunctionalCore.Controller.SubControllers.AuthController;
import FunctionalCore.Controller.ResponseGeneration.AuthGenerator;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.AuthenticateHeaderSetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.StartLineSetter;
import FunctionalCore.Request;
import Mocks.MockRequestDealer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class AuthControllerTest {
    AuthController subject;
    LinkedHashMap<String, String> properties = new LinkedHashMap<>();
    {
        properties.put("authorization", "admin:hunter2");
    }
    LinkedHashMap<String, LinkedHashMap<String, String>> routes = new LinkedHashMap<>();

    @BeforeEach
    void setup() {
        routes = new LinkedHashMap<>();
        StartLineSetter startLineSetter = new StartLineSetter();
        AuthenticateHeaderSetter authenticateHeaderSetter = new AuthenticateHeaderSetter();
        AuthGenerator generator = new AuthGenerator(startLineSetter, authenticateHeaderSetter);
        subject = new AuthController(generator);
    }

    @Test
    void relevantReturnsTrueIfRouteRequiresAuthorization() {
        //Given
        String uri = "/restricted";
        routes.put(uri, properties);
        Request request = MockRequestDealer.getRequest(uri);
        //When
        boolean actual = subject.relevant(request, routes);
        //Then
        assertTrue(actual);
    }

    @Test
    void relevantReturnsFalseIfRouteDoesNotRequireAuthorization() {
        //Given
        String uri = "/unrestricted";
        routes.put(uri, new LinkedHashMap<>());
        Request request = MockRequestDealer.getRequest(uri);
        //When
        boolean actual = subject.relevant(request, routes);
        //Then
        assertFalse(actual);
    }

    @Test
    void relevantReturnsFalseIfRequestHasAuthorization() {
        //Given
        String uri = "/restricted";
        routes.put(uri, properties);
        Request request = MockRequestDealer.authRequest(uri);
        //When
        boolean actual = subject.relevant(request, routes);
        //Then
        assertFalse(actual);
    }

    @Test
    void relevantReturnsTrueIfRequestHasBadAuthorization() {
        //Given
        String uri = "/restricted";
        routes.put(uri, properties);
        Request request = MockRequestDealer.badAuthRequest(uri);
        //When
        boolean actual = subject.relevant(request, routes);
        //Then
        assertTrue(actual);
    }

    @Test
    void generateReturnsA401ResponseIfNoAuthorizationHeaderIsFound() {
        //Given
        String uri = "/restricted";
        routes.put(uri, properties);
        Request request = MockRequestDealer.getRequest(uri);
        //When
        byte[] actual = subject.generate(request, routes);
        //Then
        String expected = "401 Unauthorized";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generateReturnsA403ResponseIfAuthorizationHeaderIsBad() {
        //Given
        String uri = "/restricted";
        routes.put(uri, properties);
        Request request = MockRequestDealer.badAuthRequest(uri);
        //When
        byte[] actual = subject.generate(request, routes);
        //Then
        String expected = "403 Forbidden";
        assertTrue(new String(actual).contains(expected));
    }
}