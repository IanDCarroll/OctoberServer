package FunctionalCoreTests.ControllerTests.SubControllerTests;

import Factory.ServerFactory;
import FunctionalCore.Controller.SubControllers.TeaPotController;
import FunctionalCore.Request;
import Mocks.MockRequestDealer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class TeaPotControllerTest {
    private int port = 5000;
    private String directory = System.getProperty("user.dir") + "/src/test/java/Mocks";
    private String configFile = directory + "/mock_routes.yml";
    TeaPotController subject;
    LinkedHashMap emptyRoutes = new LinkedHashMap<>();

    @BeforeEach
    void setup() {
        ServerFactory factory = new ServerFactory(port, directory, configFile);
        subject = factory.buildTeaPotController();
    }

    @Test
    void relevantReturnsTrueIfRouteIsCoffee() {
        //Given
        Request request = MockRequestDealer.getRequest("/coffee");
        //When
        boolean actual = subject.relevant(request, emptyRoutes);
        //Then
        assertTrue(actual);
    }

    @Test
    void relevantReturnsFalseIfRouteIsNotCoffee() {
        //Given
        Request request = MockRequestDealer.getRequest("/just-some-bs-chicory");
        //When
        boolean actual = subject.relevant(request, emptyRoutes);
        //Then
        assertFalse(actual);
    }

    @Test
    void generateReturnsA418Response() {
        //Given
        Request request = MockRequestDealer.getRequest("/coffee");
        //When
        byte[] actual = subject.generate(request, emptyRoutes);
        //Then
        String expected = "418 I'm a teapot";
        assertTrue(new String(actual).contains(expected));
    }
}