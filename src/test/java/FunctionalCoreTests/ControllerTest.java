package FunctionalCoreTests;

import FunctionalCore.Controller;
import FunctionalCore.Request;
import FunctionalCore.ResponseGenerator;
import Mocks.MockRequestDealer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private Controller subject;

    @BeforeEach
    void init() {
        ResponseGenerator responseGenerator = new ResponseGenerator();
        LinkedHashMap mockRoutes = new LinkedHashMap();
        subject = new Controller(responseGenerator, mockRoutes);
    }

    @Test
    void getAppropriateResponseTakesARequestObjectAndReturnsAnAppropriateByteArray() {
        //Given
        Request request = MockRequestDealer.getRootRequest;
        //When
        byte[] actual = subject.getAppropriateResponse(request);
        //Then
        String expected = "HTTP/1.1 200 OK";
        assertTrue(new String(actual).contains(expected));
    }
}