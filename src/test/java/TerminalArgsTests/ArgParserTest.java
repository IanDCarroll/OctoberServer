package TerminalArgsTests;

import TerminalArgs.*;
import TerminalArgs.ArgParser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArgParserTest {
    private ArgParser subject = new HashLoopArgParser();
    private static String portFlag = "-p";
    private static String directoryFlag = "-d";
    private static String configFlag = "-c";
    private static String port = "1701";
    private static String directory = System.getProperty("user.dir") + "/target";
    private static String configFile = System.getProperty("user.dir") + "/src/test/java/Mocks/mock_routes.yml";

    @Test
    void argParserGetsDefaultPort5000() {
        //Given
        String[] args = {};
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
        String[] args = {};
        subject.setArgs(args);
        //When
        String actual = subject.getDirectory();
        //Then
        String expected = System.getProperty("user.dir");
        assertEquals(expected, actual);
    }

    @Test
    void argParserGetsADefaultConfigFileWhenNoConfigIsSpecified() {
        //Given
        String[] args = {};
        subject.setArgs(args);
        //When
        String actual = subject.getConfigFile();
        //Then
        String expected = "src/main/java/routes_config.yml";
        assertEquals(expected, actual);
    }

    @Test
    void argParserSetsPortAndDirectory() {
        //Given
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
}