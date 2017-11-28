package FunctionalCoreTests;

import FunctionalCore.Controller;
import FunctionalCore.ResponseGenerator;
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
    void sanityTest() {
        boolean actual = subject.exists();
        boolean expected = true;
        assertEquals(expected, actual);
    }
}