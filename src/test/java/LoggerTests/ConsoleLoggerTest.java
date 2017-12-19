package LoggerTests;

import Filers.FileUpdater;
import Helpers.FileHelper;
import Loggers.ConsoleLogger;
import Loggers.Logger;
import Mocks.MockPrintStreamDealer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleLoggerTest {
    private String directory = System.getProperty("user.dir") + "/src/test/java/Mocks";
    private String fullPath = directory + "/logs";
    private FileUpdater fileUpdater;
    private PrintStream printStream;
    private Logger logger;

    @BeforeEach
    void setup() {
        fileUpdater = new FileUpdater(directory);
        printStream = MockPrintStreamDealer.printStream;
        logger = new ConsoleLogger(fileUpdater, printStream);
    }

    @AfterEach
    void tearDown() {
        FileHelper.delete(fullPath);
    }

    @Test
    void logReturnsALogEntryWithATimeStamp() {
        //Given
        String entry = "This is an entry";
        String partOfTheYearInATimeStamp = "20";
        //When
        logger.log(entry);
        //Then
        assertTrue(MockPrintStreamDealer.getEntry().contains(entry));
        assertTrue(MockPrintStreamDealer.getEntry().contains(partOfTheYearInATimeStamp));
    }

    @Test
    void logAppendsLogEntriesToALogFileInThePublicDirectory() {
        //Given
        String entry = "This is an entry";
        String partOfTheYearInATimeStamp = "20";
        //When
        logger.log(entry);
        //Then
        assertTrue(FileHelper.read(fullPath).contains(entry));
        assertTrue(FileHelper.read(fullPath).contains(partOfTheYearInATimeStamp));
    }

}