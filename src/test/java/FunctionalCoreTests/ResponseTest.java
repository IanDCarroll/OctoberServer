package FunctionalCoreTests;

import FunctionalCore.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseTest {
    public Response subject;

    @BeforeEach
    void init() {
        subject = new Response();
    }

    @Test
    void sanityTest() {
        boolean actual = subject.exists();
        boolean expected = true;
        assertEquals(expected, actual);
    }
}