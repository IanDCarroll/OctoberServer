package FunctionalCoreTests.ControllerTests.SubControllerTests;

import Factory.ServerFactory;
import FunctionalCore.Controller.SubControllers.CookieController;
import FunctionalCore.Request;
import Mocks.MockRequestDealer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class CookieControllerTest {
    private int port = 5000;
    private String directory = System.getProperty("user.dir") + "/src/test/java/Mocks";
    private String configFile = directory + "/mock_routes.yml";
    CookieController subject;
    LinkedHashMap<String, String> properties;
    LinkedHashMap<String, LinkedHashMap<String, String>> routes;

    @BeforeEach
    void setup() {
        properties = new LinkedHashMap<>();
        routes = new LinkedHashMap<>();
        ServerFactory factory = new ServerFactory(port, directory, configFile);
        subject = factory.buildCookieController();
    }

    @Test
    void relevantReturnsTrueIfThereIsASetACookiePropertyInRoutes() {
        //Given
        String uri = "/flagged-for-cookie-setting";
        properties.put("set-a-cookie", "");
        routes.put(uri, properties);
        Request request = MockRequestDealer.getRequest(uri);
        //When
        boolean actual = subject.relevant(request, routes);
        //Then
        assertTrue(actual);
    }

    @Test
    void relevantReturnsTrueIfThereIsAWatchForCookiePropertyInRoutesAndACookieHeaderInTheRequest() {
        //Given
        String uri = "/flagged-for-cookie-watching";
        properties.put("watch-for-cookie", "");
        routes.put(uri, properties);
        Request request = MockRequestDealer.cookieRequest(uri);
        //When
        boolean actual = subject.relevant(request, routes);
        //Then
        assertTrue(actual);
    }

    @Test
    void relevantReturnsFalseIfThereIsAWatchForCookiePropertyInRoutesButNoCookieHeaderInTheRequest() {
        //Given
        String uri = "/flagged-for-cookie-watching";
        properties.put("watch-for-cookie", "");
        routes.put(uri, properties);
        Request request = MockRequestDealer.getRequest(uri);
        //When
        boolean actual = subject.relevant(request, routes);
        //Then
        assertFalse(actual);
    }

    @Test
    void relevantReturnsFalseIfThereIsACookieHeaderInTheRequestButNoWatchForCookiePropertyInRoutes() {
        //Given
        String uri = "/ignoring-cookies";
        properties.put("dude-dont-watch-for-cookies", "");
        routes.put(uri, properties);
        Request request = MockRequestDealer.cookieRequest(uri);
        //When
        boolean actual = subject.relevant(request, routes);
        //Then
        assertFalse(actual);
    }

    @Test
    void relevantReturnsFalseIfThereAreNoCookiePropertyFlagsEvenIfTheRequestHasCookies() {
        //Given
        String uri = "/not-flagged";
        properties.put("allowed-methods", "OPTIONS,HEAD");
        routes.put(uri, properties);
        Request request = MockRequestDealer.cookieRequest(uri);
        //When
        boolean actual = subject.relevant(request, routes);
        //Then
        assertFalse(actual);
    }

    @Test
    void generateGeneratesA200Response() {
        //Given
        String uri = "/flagged-for-cookies";
        properties.put("set-a-cookie", "");
        routes.put(uri, properties);
        Request request = MockRequestDealer.cookieRequest(uri);
        //When
        byte[] actual = subject.generate(request, routes);
        //Then
        String expected = "200 OK";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generateIncludesMMMMChocolateInTheBodyIfRouteHasAWatchForCookieProperty() {
        //Given
        String uri = "/flagged-for-cookies";
        properties.put("watch-for-cookie", "");
        routes.put(uri, properties);
        Request request = MockRequestDealer.cookieRequest(uri);
        //When
        byte[] actual = subject.generate(request, routes);
        //Then
        String expected = "\r\n\r\nmmmm chocolate";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generateIncludesASetCookieHeaderIfRouteHasASetACookieProperty() {
        //Given
        String uri = "/flagged-for-cookies";
        properties.put("set-a-cookie", "");
        routes.put(uri, properties);
        Request request = MockRequestDealer.getRequest(uri);
        //When
        byte[] actual = subject.generate(request, routes);
        //Then
        String expected = "Set-Cookie:";
        assertTrue(new String(actual).contains(expected));
    }
}