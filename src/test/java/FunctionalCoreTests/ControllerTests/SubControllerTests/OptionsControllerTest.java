package FunctionalCoreTests.ControllerTests.SubControllerTests;

import Factory.ServerFactory;
import FunctionalCore.Controller.SubControllers.OptionsController;
import FunctionalCore.Request;
import Mocks.MockRequestDealer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class OptionsControllerTest {
    private int port = 5000;
    private String directory = System.getProperty("user.dir") + "/src/test/java/Mocks";
    private String configFile = directory + "/mock_routes.yml";
    OptionsController subject;
    LinkedHashMap<String, String> properties;
    LinkedHashMap<String, LinkedHashMap<String, String>> routes;

    @BeforeEach
    void setup() {
        properties = new LinkedHashMap<>();
        routes = new LinkedHashMap<>();
        ServerFactory factory = new ServerFactory(port, directory, configFile);
        subject = factory.buildOptionsController();
    }

    @Test
    void relevantReturnsTrueIfTheMethodIsNotAllowed() {
        //Given
        String uri = "/not-allowed";
        properties.put("allowed-methods", "OPTIONS,HEAD");
        routes.put(uri, properties);
        Request request = MockRequestDealer.getRequest(uri);
        //When
        boolean actual = subject.relevant(request, routes);
        //Then
        assertTrue(actual);
    }

    @Test
    void relevantReturnsTrueIfTheMethodIsOptions() {
        //Given
        String uri = "/";
        properties.put("allowed-methods", "OPTIONS,HEAD");
        routes.put(uri, properties);
        Request request = MockRequestDealer.optionsRequest();
        //When
        boolean actual = subject.relevant(request, routes);
        //Then
        assertTrue(actual);
    }

    @Test
    void relevantReturnsFalseIfTheMethodIsAllowedAndIsNotOptions() {
        //Given
        String uri = "/";
        properties.put("allowed-methods", "GET");
        routes.put(uri, properties);
        Request request = MockRequestDealer.getRequest(uri);
        //When
        boolean actual = subject.relevant(request, routes);
        //Then
        assertFalse(actual);
    }

    @Test
    void generateReturnsA200IfTheMethodIsAllowed() {
        //Given
        String uri = "/";
        properties.put("allowed-methods", "GET");
        routes.put(uri, properties);
        Request request = MockRequestDealer.getRequest(uri);
        //When
        byte[] actual = subject.generate(request, routes);
        //Then
        String expected = "200 OK";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generateReturnsAnAllowHeaderWithIts200Response() {
        String uri = "/";
        String methods = "GET,OPTIONS";
        properties.put("allowed-methods", methods);
        routes.put(uri, properties);
        Request request = MockRequestDealer.getRequest(uri);
        //When
        byte[] actual = subject.generate(request, routes);
        //Then
        String expected = "Allow: " + methods;
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generateReturnsA405IfTheMethodIsNotAllowed() {
        String uri = "/";
        properties.put("allowed-methods", "HEAD");
        routes.put(uri, properties);
        Request request = MockRequestDealer.getRequest(uri);
        //When
        byte[] actual = subject.generate(request, routes);
        //Then
        String expected = "405 Method Not Allowed";
        assertTrue(new String(actual).contains(expected));
    }

}