package FunctionalCoreTests.ControllerTests.SubControllerTests;

import Filers.FileClerk;
import FunctionalCore.Controller.SubControllers.MethodController;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.BodySetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.ETagHeaderSetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.SetCookieHeaderSetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.StartLineSetter;
import FunctionalCore.Controller.ResponseGeneration.SuccessGenerator;
import FunctionalCore.Request;
import Mocks.MockRequestDealer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class MethodControllerTest {
    MethodController subject;
    LinkedHashMap<String, String> properties;
    LinkedHashMap<String, LinkedHashMap<String, String>> routes;
    private String publicDir = System.getProperty("user.dir") + "/src/test/java/Mocks";

    @BeforeEach
    void setup() {
        properties = new LinkedHashMap<>();
        routes = new LinkedHashMap<>();
        FileClerk fileClerk = new FileClerk(publicDir);
        StartLineSetter s = new StartLineSetter();
        BodySetter b = new BodySetter(fileClerk);
        SetCookieHeaderSetter c = new SetCookieHeaderSetter();
        ETagHeaderSetter e = new ETagHeaderSetter();
        SuccessGenerator successGenerator = new SuccessGenerator(s, b, c, e);
        subject = new MethodController(fileClerk, successGenerator);
    }

    @Test
    void relevantAlwaysReturnsTrueForMethodControllerItsTheEndOfTheLine() {
        //Given
        String uri = "/flagged-for-cookie-setting";
        Request request = MockRequestDealer.getRequest(uri);
        //When
        boolean actual = subject.relevant(request, routes);
        //Then
        assertTrue(actual);
    }
}