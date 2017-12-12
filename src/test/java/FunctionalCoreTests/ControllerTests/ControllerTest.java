package FunctionalCoreTests.ControllerTests;

import FunctionalCore.Controller.Controller;
import FunctionalCore.Request;
import FunctionalCore.Controller.ResponseGenerator;
import Mocks.MockRequestDealer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private Controller subject;
    private LinkedHashMap mockRoutes;

    @BeforeEach
    void init() {
        ResponseGenerator responseGenerator = new ResponseGenerator();
        mockRoutes = new LinkedHashMap();
        subject = new Controller(responseGenerator, mockRoutes);
    }

    @Test
    void getAppropriateResponseTakesARequestObjectAndReturnsAnAppropriateByteArray() {
        //Given
        mockRoutes.put("/", "GET");
        Request request = MockRequestDealer.getRootRequest();
        //When
        byte[] actual = subject.getAppropriateResponse(request);
        //Then
        String expected = "HTTP/1.1 200 OK";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void getAppropriateResponseReturnsA404IfTheUriIsNotFoundInRoutes() {
        //Given
        mockRoutes.put("/not-found", "unused info for this operation");
        Request request = MockRequestDealer.getRootRequest();
        //When
        byte[] actual = subject.getAppropriateResponse(request);
        //Then
        String expected = "HTTP/1.1 404 Not Found";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void getAppropriateResponseReturns405IfTheMethodIsNotAllowed() {
        //Given
        mockRoutes.put("/", "POST");
        Request request = MockRequestDealer.getRootRequest();
        //When
        byte[] actual = subject.getAppropriateResponse(request);
        //Then
        String expected = "HTTP/1.1 405 Method Not Allowed";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void getAppropriateResponseReturnsAnAllowHeaderIfTheMethodIsNotAllowed() {
        //Given
        mockRoutes.put("/", "POST PUT EXTERMINATE");
        Request request = MockRequestDealer.getRootRequest();
        //When
        byte[] actual = subject.getAppropriateResponse(request);
        //Then
        String expected = "Allow: POST, PUT, EXTERMINATE";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void getAppropriateResponseReturnsOnlyTheHeadOfARequest() {
        //Given
        mockRoutes.put("/", "HEAD");
        Request request = MockRequestDealer.headHeaderRequest();
        //When
        byte[] actual = subject.getAppropriateResponse(request);
        //Then
        String unExpected = "param1";
        System.out.println(new String(actual));
        assertFalse(new String(actual).contains(unExpected));
    }
}