package FunctionalCoreTests.ControllerTests;

import FunctionalCore.Controller.ParamFormatter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParamFormatterTest {
    @Test
    void addSpacesBeforeAndAfterAssignmentOperatorDoesWhatItSays() {
        //Given
        String param = "param1=value1";
        //When
        String actual = ParamFormatter.addSpaces(param);
        //Then
        String expected = "param1 = value1";
        assertEquals(expected, actual);
    }

}