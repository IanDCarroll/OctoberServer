package TerminalArgsTests;

import TerminalArgs.LengthChecker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LengthCheckerTest {
    @Test
    void argParserThrowsAnIllegalArgumentExceptionIfThenumberOfAgrsDoesNotMatchTheNumberParsed() {
        //Given
        int argsLenght = 4;
        int argsAccountedFor = 2;
        //Then
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            LengthChecker.checkForLeftovers(argsLenght, argsAccountedFor);
        });
        //And
        assertEquals(LengthChecker.usageMessage(), exception.getMessage());
    }

    @Test
    void checkLengthThrowsAnIllegalArgumentExceptionIfThereIsOneArg() {
        //Given
        String portFlag = "-p";
        String[] args = { portFlag };
        //Then
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            LengthChecker.validateLength(args.length);
        });
        //And
        assertEquals(LengthChecker.usageMessage(), exception.getMessage());
    }

    @Test
    void checkLengthThrowsAnIllegalArgumentExceptionIfThereAreThreeArgs() {
        //Given
        String portFlag = "-p";
        String port = "1701";
        String directoryFlag = "-d";
        String[] args = { portFlag, port, directoryFlag };
        //Then
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            LengthChecker.validateLength(args.length);
        });
        //And
        assertEquals(LengthChecker.usageMessage(), exception.getMessage());
    }

    @Test
    void checkLengthThrowsAnIllegalArgumentExceptionIfThereAreFiveArgs() {
        //Given
        String portFlag = "-p";
        String port = "1701";
        String directoryFlag = "-d";
        String directory = "/target";
        String configFlag = "-c";
        String[] args = { portFlag, port, directoryFlag, directory, configFlag };
        //Then
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            LengthChecker.validateLength(args.length);
        });
        //And
        assertEquals(LengthChecker.usageMessage(), exception.getMessage());
    }

    @Test
    void checkLengthThrowsAnIllegalArgumentExceptionIfThereAreMoreThanSixArgs() {
        //Given
        String portFlag = "-p";
        String port = "1701";
        String directoryFlag = "-d";
        String directory = "/target";
        String configFlag = "-c";
        String configFile = "src/main/java/mock_routes_config.yml";
        String extra = "Throw an error";
        String[] args = { portFlag, port, directoryFlag, directory, configFlag, configFile, extra };
        //Then
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            LengthChecker.validateLength(args.length);
        });
        //And
        assertEquals(LengthChecker.usageMessage(), exception.getMessage());
    }

}