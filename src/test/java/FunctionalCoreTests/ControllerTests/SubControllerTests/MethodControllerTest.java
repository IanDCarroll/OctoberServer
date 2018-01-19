package FunctionalCoreTests.ControllerTests.SubControllerTests;

import Factory.ServerFactory;
import FunctionalCore.Controller.SubControllers.MethodController;
import FunctionalCore.Request;
import Mocks.MockRequestDealer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class MethodControllerTest {
    private int port = 5000;
    private String directory = System.getProperty("user.dir") + "/src/test/java/Mocks";
    private String configFile = directory + "/mock_routes.yml";
    MethodController subject;
    LinkedHashMap<String, String> properties;
    LinkedHashMap<String, LinkedHashMap<String, String>> routes;

    @BeforeEach
    void setup() {
        properties = new LinkedHashMap<>();
        routes = new LinkedHashMap<>();
        ServerFactory factory = new ServerFactory(port, directory, configFile);
        subject = factory.buildMethodController();
    }

    @Test
    void relevantReturnsTrueIfTheRequestIsNotBad() {
        //Given
        String uri = "/flagged-for-cookie-setting";
        Request request = MockRequestDealer.getRequest(uri);
        //When
        boolean actual = subject.relevant(request, routes);
        //Then
        assertTrue(actual);
    }

    @Test
    void relevantReturnsFalseIfTheRequestHasNoHTTPVersionSet() {
        //Given
        Request request = MockRequestDealer.nullHTTPVRequest();
        //When
        boolean actual = subject.relevant(request, routes);
        //Then
        assertFalse(actual);
    }
}