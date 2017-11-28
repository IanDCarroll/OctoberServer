package TerminalArgsTests;

import TerminalArgs.PortChecker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PortCheckerTest {
    private static final int PORT_MIN = 0;
    private static final int PORT_MAX = 65535;

    @Test
    void argParserSetsPortToAnyArbitraryIntInTheRangeOfValidPorts() {

        //Given
        String port = "1701";
        //When
        int actual = PortChecker.validatePort(port);
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
            PortChecker.validatePort(port);
        });
        //And
        String expected = PortChecker.portNumberFormatErrorMessage(port);
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void argParserThrowsAnIllegalArgumentExceptionIfPortNumberIsTooLow() {
        //Given
        String port = "-1";
        //Then
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            PortChecker.validatePort(port);
        });
        //And
        String expected = PortChecker.portOutOfRangeMessage(port, String.valueOf(PORT_MIN), String.valueOf(PORT_MAX));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void argParserThrowsAnIllegalArgumentExceptionIfPortNumberIsTooHigh() {
        //Given
        String port = "65536";
        //Then
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            PortChecker.validatePort(port);
        });
        //And
        String expected = PortChecker.portOutOfRangeMessage(port, String.valueOf(PORT_MIN), String.valueOf(PORT_MAX));
        assertEquals(expected, exception.getMessage());
    }
}