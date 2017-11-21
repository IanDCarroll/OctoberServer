package clArgsTests;

import clArgs.*;
import clArgs.ArgParser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArgParserTest {
    private ArgParser subject = new ConditionalArgParser();
    private static final int PORT_MIN = 0;
    private static final int PORT_MAX = 65535;

    @Test
    void argParserGetsDefaultPort5000() {
        //Given
        String[] args = new String[0];
        subject.setArgs(args);
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
        subject.setArgs(args);
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
        subject.setArgs(args);
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
            subject.setArgs(args);
        });
        //And
        String expected = PortSetter.portNumberFormatErrorMessage(port);
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void argParserThrowsAnIllegalArgumentExceptionIfPortNumberIsTooLow() {
        //Given
        String portFlag = "-p";
        String port = "-1";
        String[] args = { portFlag, port };
        //Then
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            subject.setArgs(args);
        });
        //And
        String expected = PortSetter.portOutOfRangeMessage(port, String.valueOf(PORT_MIN), String.valueOf(PORT_MAX));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void argParserThrowsAnIllegalArgumentExceptionIfPortNumberIsTooHigh() {
        //Given
        String portFlag = "-p";
        String port = "65536";
        String[] args = { portFlag, port };
        //Then
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            subject.setArgs(args);
        });
        //And
        String expected = PortSetter.portOutOfRangeMessage(port, String.valueOf(PORT_MIN), String.valueOf(PORT_MAX));
        assertEquals(expected, exception.getMessage());
    }


    @Test
    void argParserSetsDirectoryToAnyValidDirectoryFoundInTheFileSystem() {
        //Given
        String directoryFlag = "-d";
        String directory = System.getProperty("user.dir") + "/target";
        String[] args = { directoryFlag, directory };
        subject.setArgs(args);
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
            subject.setArgs(args);
        });
        //And
        assertEquals(FSSetter.notInFSMessage(directory), exception.getMessage());
    }

    @Test
    void argParserGetsADefaultConfigFileWhenNoConfigIsSpecified() {
        //Given
        String configFile = "src/main/java/routes_config.yml";
        String[] args = {};
        subject.setArgs(args);
        //When
        String actual = subject.getConfigFile();
        //Then
        String expected = configFile;
        assertEquals(expected, actual);
    }

    @Test
    void argParserSetsAConfigFileWhenAnExistingConfigIsSpecified() {
        //Given
        String configFlag = "-c";
        String configFile = System.getProperty("user.dir") + "/src/main/java/routes_config.yml";
        String[] args = {configFlag, configFile };
        subject.setArgs(args);
        //When
        String actual = subject.getConfigFile();
        //Then
        String expected = configFile;
        assertEquals(expected, actual);
    }

    @Test
    void argParserThrowsAnIllegalArgumentExceptionIfConfigSpecifiedCantBeFoundInTheFileSystem() {
        //Given
        String configFlag = "-c";
        String configFile = System.getProperty("user.dir") + "/src/main/java/throw_an_error.yml";
        String[] args = {configFlag, configFile };
        //Then
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            subject .setArgs(args);
        });
        //And
        assertEquals(FSSetter.notInFSMessage(configFile), exception.getMessage());
    }

    @Test
    void argParserThrowsAnIllegalArgumentExceptionIfFlagsAreNotSpecified() {
        //Given
        String directoryFlag = "Throw an error";
        String directory = "/target";
        String[] args = { directoryFlag, directory };
        //Then
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            subject .setArgs(args);
        });
        //And
        assertEquals(ConditionalArgParser.usageMessage(), exception.getMessage());
    }

    @Test
    void argParserSetsPortAndDirectory() {
        //Given
        String portFlag = "-p";
        String port = "1701";
        String directoryFlag = "-d";
        String directory = System.getProperty("user.dir") + "/target";
        String[] args = { portFlag, port, directoryFlag, directory };
        subject.setArgs(args);
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
        subject.setArgs(args);
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
    void argParserSetsConfigAndDirectory() {
        //Given
        String configFlag = "-c";
        String configFile = System.getProperty("user.dir") + "/src/main/java/routes_config.yml";
        String directoryFlag = "-d";
        String directory = System.getProperty("user.dir") + "/target";
        String[] args = { directoryFlag, directory, configFlag, configFile };
        subject.setArgs(args);
        //When
        String actualFile = subject.getConfigFile();
        String actualDirectory = subject.getDirectory();
        //Then
        String expectedFile = configFile;
        String expectedDirectory = directory;
        assertEquals(expectedFile, actualFile);
        assertEquals(expectedDirectory, actualDirectory);
    }

    @Test
    void argParserSetsDirectoryAndConfig() {
        //Given
        String directoryFlag = "-d";
        String directory = System.getProperty("user.dir") + "/target";
        String configFlag = "-c";
        String configFile = System.getProperty("user.dir") + "/src/main/java/routes_config.yml";
        String[] args = { configFlag, configFile, directoryFlag, directory };
        subject.setArgs(args);
        //When
        String actualFile = subject.getConfigFile();
        String actualDirectory = subject.getDirectory();
        //Then
        String expectedFile = configFile;
        String expectedDirectory = directory;
        assertEquals(expectedFile, actualFile);
        assertEquals(expectedDirectory, actualDirectory);
    }

    @Test
    void argParserSetsPortAndConfig() {
        //Given
        String portFlag = "-p";
        String port = "1701";
        String configFlag = "-c";
        String configFile = System.getProperty("user.dir") + "/src/main/java/routes_config.yml";
        String[] args = { portFlag, port, configFlag, configFile };
        subject.setArgs(args);
        //When
        int actualPort = subject.getPort();
        String actualFile = subject.getConfigFile();
        //Then
        int expectedPort = Integer.parseInt(port);
        String expectedFile = configFile;
        assertEquals(expectedPort, actualPort);
        assertEquals(expectedFile, actualFile);
    }

    @Test
    void argParserSetsConfigAndPort() {
        //Given
        String configFlag = "-c";
        String configFile = System.getProperty("user.dir") + "/src/main/java/routes_config.yml";
        String portFlag = "-p";
        String port = "1701";
        String[] args = { configFlag, configFile, portFlag, port };
        subject.setArgs(args);
        //When
        int actualPort = subject.getPort();
        String actualFile = subject.getConfigFile();
        //Then
        int expectedPort = Integer.parseInt(port);
        String expectedFile = configFile;
        assertEquals(expectedPort, actualPort);
        assertEquals(expectedFile, actualFile);
    }

    @Test
    void argParserSetsPortAndDirectoryAndConfig() {
        //Given
        String portFlag = "-p";
        String port = "1701";
        String directoryFlag = "-d";
        String directory = System.getProperty("user.dir") + "/target";
        String configFlag = "-c";
        String configFile = System.getProperty("user.dir") + "/src/main/java/routes_config.yml";
        String[] args = { portFlag, port, directoryFlag, directory, configFlag, configFile };
        subject .setArgs(args);
        //When
        int actualPort = subject.getPort();
        String actualDirectory = subject.getDirectory();
        String actualConfig = subject.getConfigFile();
        //Then
        int expectedPort = Integer.parseInt(port);
        String expectedDirectory = directory;
        String expectedConfig = configFile;
        assertEquals(expectedPort, actualPort);
        assertEquals(expectedDirectory, actualDirectory);
        assertEquals(expectedConfig, actualConfig);
    }

    @Test
    void argParserSetsPortAndConfigAndDirectory() {
        //Given
        String portFlag = "-p";
        String port = "1701";
        String directoryFlag = "-d";
        String directory = System.getProperty("user.dir") + "/target";
        String configFlag = "-c";
        String configFile = System.getProperty("user.dir") + "/src/main/java/routes_config.yml";
        String[] args = { directoryFlag, directory, configFlag, configFile, portFlag, port };
        subject.setArgs(args);
        //When
        int actualPort = subject.getPort();
        String actualDirectory = subject.getDirectory();
        String actualConfig = subject.getConfigFile();
        //Then
        int expectedPort = Integer.parseInt(port);
        String expectedDirectory = directory;
        String expectedConfig = configFile;
        assertEquals(expectedPort, actualPort);
        assertEquals(expectedDirectory, actualDirectory);
        assertEquals(expectedConfig, actualConfig);
    }

    @Test
    void argParserSetsDirectoryAndPortAndConfig() {
        //Given
        String portFlag = "-p";
        String port = "1701";
        String directoryFlag = "-d";
        String directory = System.getProperty("user.dir") + "/target";
        String configFlag = "-c";
        String configFile = System.getProperty("user.dir") + "/src/main/java/routes_config.yml";
        String[] args = { directoryFlag, directory, portFlag, port, configFlag, configFile };
        subject.setArgs(args);
        //When
        int actualPort = subject.getPort();
        String actualDirectory = subject.getDirectory();
        String actualConfig = subject.getConfigFile();
        //Then
        int expectedPort = Integer.parseInt(port);
        String expectedDirectory = directory;
        String expectedConfig = configFile;
        assertEquals(expectedPort, actualPort);
        assertEquals(expectedDirectory, actualDirectory);
        assertEquals(expectedConfig, actualConfig);
    }

    @Test
    void argParserSetsDirectoryAndConfigAndPort() {
        //Given
        String portFlag = "-p";
        String port = "1701";
        String directoryFlag = "-d";
        String directory = System.getProperty("user.dir") + "/target";
        String configFlag = "-c";
        String configFile = System.getProperty("user.dir") + "/src/main/java/routes_config.yml";
        String[] args = { configFlag, configFile, directoryFlag, directory, portFlag, port };
        subject.setArgs(args);
        //When
        int actualPort = subject.getPort();
        String actualDirectory = subject.getDirectory();
        String actualConfig = subject.getConfigFile();
        //Then
        int expectedPort = Integer.parseInt(port);
        String expectedDirectory = directory;
        String expectedConfig = configFile;
        assertEquals(expectedPort, actualPort);
        assertEquals(expectedDirectory, actualDirectory);
        assertEquals(expectedConfig, actualConfig);
    }

    @Test
    void argParserSetsConfigAndPortAndDirectory() {
        //Given
        String portFlag = "-p";
        String port = "1701";
        String directoryFlag = "-d";
        String directory = System.getProperty("user.dir") + "/target";
        String configFlag = "-c";
        String configFile = System.getProperty("user.dir") + "/src/main/java/routes_config.yml";
        String[] args = { configFlag, configFile, portFlag, port, directoryFlag, directory };
        subject.setArgs(args);
        //When
        int actualPort = subject.getPort();
        String actualDirectory = subject.getDirectory();
        String actualConfig = subject.getConfigFile();
        //Then
        int expectedPort = Integer.parseInt(port);
        String expectedDirectory = directory;
        String expectedConfig = configFile;
        assertEquals(expectedPort, actualPort);
        assertEquals(expectedDirectory, actualDirectory);
        assertEquals(expectedConfig, actualConfig);
    }

    @Test
    void argParserSetsConfigAndDirectoryAndPort() {
        //Given
        String portFlag = "-p";
        String port = "1701";
        String directoryFlag = "-d";
        String directory = System.getProperty("user.dir") + "/target";
        String configFlag = "-c";
        String configFile = System.getProperty("user.dir") + "/src/main/java/routes_config.yml";
        String[] args = { configFlag, configFile,directoryFlag, directory, portFlag, port };
        subject.setArgs(args);
        //When
        int actualPort = subject.getPort();
        String actualDirectory = subject.getDirectory();
        String actualConfig = subject.getConfigFile();
        //Then
        int expectedPort = Integer.parseInt(port);
        String expectedDirectory = directory;
        String expectedConfig = configFile;
        assertEquals(expectedPort, actualPort);
        assertEquals(expectedDirectory, actualDirectory);
        assertEquals(expectedConfig, actualConfig);
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
            subject.setArgs(args);
        });
        //And
        assertEquals(ConditionalArgParser.usageMessage(), exception.getMessage());
    }

    @Test
    void argParserThrowsAnIllegalArgumentExceptionIfThereIsOneArg() {
        //Given
        String portFlag = "-p";
        String[] args = { portFlag };
        //Then
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            subject.setArgs(args);
        });
        //And
        assertEquals(ConditionalArgParser.usageMessage(), exception.getMessage());
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
            subject.setArgs(args);
        });
        //And
        assertEquals(ConditionalArgParser.usageMessage(), exception.getMessage());
    }

    @Test
    void argParserThrowsAnIllegalArgumentExceptionIfThereAreFiveArgs() {
        //Given
        String portFlag = "-p";
        String port = "1701";
        String directoryFlag = "-d";
        String directory = "/target";
        String configFlag = "-c";
        String[] args = { portFlag, port, directoryFlag, directory, configFlag };
        //Then
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            subject.setArgs(args);
        });
        //And
        assertEquals(ConditionalArgParser.usageMessage(), exception.getMessage());
    }

    @Test
    void argParserThrowsAnIllegalArgumentExceptionIfThereAreMoreThanSixArgs() {
        //Given
        String portFlag = "-p";
        String port = "1701";
        String directoryFlag = "-d";
        String directory = "/target";
        String configFlag = "-c";
        String configFile = "src/main/java/routes_config.yml";
        String extra = "Throw an error";
        String[] args = { portFlag, port, directoryFlag, directory, configFlag, configFile, extra };
        //Then
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            subject.setArgs(args);
        });
        //And
        assertEquals(ConditionalArgParser.usageMessage(), exception.getMessage());
    }
}