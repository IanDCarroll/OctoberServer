package clArgsTests;

import clArgs.PortSetter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PortSetterTest {
    private static final int PORT_MIN = 0;
    private static final int PORT_MAX = 65535;

    @Test
    void argParserSetsPortToAnyArbitraryIntInTheRangeOfValidPorts() {

        //Given
        String port = "1701";
        //When
        int actual = PortSetter.setPort(port);
        //Then
        int expected = Integer.parseInt(port);
        assertEquals(expected, actual);
    }

    @Test
    void argParserThrowsANumberFormatErrorIfPortCannotBeParsed() {
        //Given
        String port = "Throw An Error";
        //Then
        Throwable exception = assertThrows(NumberFormatException.class, () -> {
            PortSetter.setPort(port);
        });
        //And
        String expected = PortSetter.portNumberFormatErrorMessage(port);
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void argParserThrowsAnIllegalArgumentExceptionIfPortNumberIsTooLow() {
        //Given
        String port = "-1";
        //Then
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            PortSetter.setPort(port);
        });
        //And
        String expected = PortSetter.portOutOfRangeMessage(port, String.valueOf(PORT_MIN), String.valueOf(PORT_MAX));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void argParserThrowsAnIllegalArgumentExceptionIfPortNumberIsTooHigh() {
        //Given
        String port = "65536";
        //Then
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            PortSetter.setPort(port);
        });
        //And
        String expected = PortSetter.portOutOfRangeMessage(port, String.valueOf(PORT_MIN), String.valueOf(PORT_MAX));
        assertEquals(expected, exception.getMessage());
    }
}