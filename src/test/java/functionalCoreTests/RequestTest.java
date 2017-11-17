package functionalCoreTests;

import functionalCore.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestTest {
    public Request subject;

    @BeforeEach
    void init() {
        subject = new Request();
    }

    @Test
    void sanityTest() {
        boolean actual = subject.exists();
        boolean expected = true;
        assertEquals(expected, actual);
    }
}