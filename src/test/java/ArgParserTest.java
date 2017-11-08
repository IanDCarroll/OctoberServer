import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArgParserTest {
    private ArgParser subject;

    @Test
    void argParserGetsDefaultPort5000() {
        //Given
        String[] args = new String[0];
        subject = new ArgParser(args);
        //When
        int actual = subject.getPort();
        //Then
        int expected = 5000;
        assertEquals(expected, actual);
    }

    @Test
    void argParserGetsDefaultDirectoryPublic() {
        //Given
        String[] args = new String[0];
        subject = new ArgParser(args);
        //When
        String actual = subject.getDirectory();
        //Then
        String expected = System.getProperty("user.dir") + "/public";
        assertEquals(expected, actual);
    }

    @Test
    void argParserSetsPortToAnyArbitraryIntInTheRangeOfValidPorts() {
        //Given
        String portFlag = "-p";
        String port = "1701";
        String[] args = { portFlag, port };
        subject = new ArgParser(args);
        //When
        int actual = subject.getPort();
        //Then
        int expected = Integer.parseInt(port);
        assertEquals(expected, actual);
    }

    @Test
    void argParserThrowsANumberFormatErrorIfPortCannotBeParsed() {
        //Given
        String portFlag = "-p";
        String port = "Throw An Error";
        String[] args = { portFlag, port };
        //Then
        Throwable exception = assertThrows(NumberFormatException.class, () -> {
            subject = new ArgParser(args);
        });
        //And
        assertEquals(ArgParser.portNumberFormatErrorMessage(port), exception.getMessage());
    }

    @Test
    void argParserThrowsAnIllegalArgumentExceptionIfPortNumberIsTooLow() {
        //Given
        String portFlag = "-p";
        String port = "-1";
        String[] args = { portFlag, port };
        //Then
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            subject = new ArgParser(args);
        });
        //And
        assertEquals(ArgParser.portOurOfRangeMessage(port), exception.getMessage());
    }

    @Test
    void argParserThrowsAnIllegalArgumentExceptionIfPortNumberIsTooHigh() {
        //Given
        String portFlag = "-p";
        String port = "65536";
        String[] args = { portFlag, port };
        //Then
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            subject = new ArgParser(args);
        });
        //And
        assertEquals(ArgParser.portOurOfRangeMessage(port), exception.getMessage());
    }


    @Test
    void argParserSetsDirectoryToAnyValidDirectoryFoundInTheFileSystem() {
        //Given
        String directoryFlag = "-d";
        String directory = System.getProperty("user.dir") + "/target";
        String[] args = { directoryFlag, directory };
        subject = new ArgParser(args);
        //When
        String actual = subject.getDirectory();
        //Then
        String expected = directory;
        assertEquals(expected, actual);
    }


    @Test
    void argParserThrowsAnIllegalArgumentExceptionIfDirectorySpecifiedCantBeFoundInTheFileSystem() {
        //Given
        String directoryFlag = "-d";
        String directory = System.getProperty("user.dir") + "/Throw-an-error";
        String[] args = { directoryFlag, directory };
        //Then
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            subject = new ArgParser(args);
        });
        //And
        assertEquals(ArgParser.directoryNotInFSMessage(directory), exception.getMessage());
    }

    @Test
    void argParserThrowsAnIllegalArgumentExceptionIfFlagsAreNotSpecified() {
        //Given
        String directoryFlag = "Throw an error";
        String directory = "/target";
        String[] args = { directoryFlag, directory };
        //Then
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            subject = new ArgParser(args);
        });
        //And
        assertEquals(ArgParser.usageMessage(), exception.getMessage());
    }

    @Test
    void argParserSetsPortAndDirectory() {
        //Given
        String portFlag = "-p";
        String port = "1701";
        String directoryFlag = "-d";
        String directory = System.getProperty("user.dir") + "/target";
        String[] args = { portFlag, port, directoryFlag, directory };
        subject = new ArgParser(args);
        //When
        int actualPort = subject.getPort();
        String actualDirectory = subject.getDirectory();
        //Then
        int expectedPort = Integer.parseInt(port);
        String expectedDirectory = directory;
        assertEquals(expectedPort, actualPort);
        assertEquals(expectedDirectory, actualDirectory);
    }

    @Test
    void argParserSetsDirectoryAndPort() {
        //Given
        String directoryFlag = "-d";
        String directory = System.getProperty("user.dir") + "/target";
        String portFlag = "-p";
        String port = "1701";
        String[] args = { directoryFlag, directory, portFlag, port };
        subject = new ArgParser(args);
        //When
        int actualPort = subject.getPort();
        String actualDirectory = subject.getDirectory();
        //Then
        int expectedPort = Integer.parseInt(port);
        String expectedDirectory = directory;
        assertEquals(expectedPort, actualPort);
        assertEquals(expectedDirectory, actualDirectory);
    }

    @Test
    void argParserThrowsAnIllegalArgumentExceptionIfFlagsAreNotRight() {
        //Given
        String portFlag = "-p";
        String port = "1701";
        String directoryFlag = "Throw an error";
        String directory = "/target";
        String[] args = { portFlag, port, directoryFlag, directory };
        //Then
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            subject = new ArgParser(args);
        });
        //And
        assertEquals(ArgParser.usageMessage(), exception.getMessage());
    }

    @Test
    void argParserThrowsAnIllegalArgumentExceptionIfThereIsOneArg() {
        //Given
        String portFlag = "-p";
        String[] args = { portFlag };
        //Then
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            subject = new ArgParser(args);
        });
        //And
        assertEquals(ArgParser.usageMessage(), exception.getMessage());
    }

    @Test
    void argParserThrowsAnIllegalArgumentExceptionIfThereAreThreeArgs() {
        //Given
        String portFlag = "-p";
        String port = "1701";
        String directoryFlag = "-d";
        String[] args = { portFlag, port, directoryFlag };
        //Then
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            subject = new ArgParser(args);
        });
        //And
        assertEquals(ArgParser.usageMessage(), exception.getMessage());
    }

    @Test
    void argParserThrowsAnIllegalArgumentExceptionIfThereAreMoreThanFourArgs() {
        //Given
        String portFlag = "-p";
        String port = "1701";
        String directoryFlag = "-d";
        String directory = "/target";
        String extra = "Throw an error";
        String[] args = { portFlag, port, directoryFlag, directory, extra };
        //Then
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            subject = new ArgParser(args);
        });
        //And
        assertEquals(ArgParser.usageMessage(), exception.getMessage());
    }
}