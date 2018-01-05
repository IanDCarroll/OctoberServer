package FunctionalCoreTests.ControllerTests;

import Filers.FileClerk;
import FunctionalCore.Controller.ResponseGeneration.ClientErrorGenerator;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.BodySetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.StartLineSetter;
import FunctionalCore.Controller.ResponseGeneration.TeaPotGenerator;
import FunctionalCore.Controller.TeaPotController;
import FunctionalCore.Request;
import Mocks.MockRequestDealer;
import com.sun.org.apache.regexp.internal.RE;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class TeaPotControllerTest {
    TeaPotController subject;
    LinkedHashMap emptyRoutes = new LinkedHashMap<>();

    @BeforeEach
    void setup() {
        StartLineSetter startLineSetter = new StartLineSetter();
        String publicDir = System.getProperty("user.dir") + "/src/test/java/Mocks";
        FileClerk fileClerk = new FileClerk(publicDir);
        BodySetter bodySetter = new BodySetter(fileClerk);
        TeaPotGenerator generator = new TeaPotGenerator(startLineSetter, bodySetter);
        subject = new TeaPotController(generator);
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