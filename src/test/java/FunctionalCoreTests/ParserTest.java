package FunctionalCoreTests;

import FunctionalCore.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    public Parser subject;

    @BeforeEach
    void init() {
        subject = new Parser();
    }

    @Test
    void sanityTest() {
        boolean actual = subject.exists();
        boolean expected = true;
        assertEquals(expected, actual);
    }
}