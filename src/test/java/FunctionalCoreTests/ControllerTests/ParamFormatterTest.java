package FunctionalCoreTests.ControllerTests;

import FunctionalCore.Controller.ParamFormatter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParamFormatterTest {
    @Test
    void addStylingFormatsAParamForTheResponseBody() {
        //Given
        String param = "param1=value1";
        //When
        String actual = ParamFormatter.addStyling(param);
        //Then
        String expected = "param1 = value1\n";
        assertEquals(expected, actual);
    }

    @Test
    void addStylingFormatsParamsForTheResponseBody() {
        //Given
        String[] param = { "param1=value1", "param2=value2" };
        //When
        String[] actual = ParamFormatter.addStyling(param);
        //Then
        String[] expected = { "param1 = value1\n", "param2 = value2\n" };
        assertArrayEquals(expected, actual);
    }

}