import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private Controller subject;

    @BeforeEach
    void init() {
        ResponseGenerator responseGenerator = new ResponseGenerator();
        ParseYaml parseYaml = new ParseYaml();
        LinkedHashMap routes = parseYaml.parse();
        subject = new Controller(responseGenerator, routes);
    }

    @Test
    void sanityTest() {
        boolean actual = subject.exists();
        boolean expected = true;
        assertEquals(expected, actual);
    }
}