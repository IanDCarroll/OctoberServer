package LoggerTests;

import Filers.FileUpdater;
import Helpers.FileHelper;
import Loggers.FileLogger;
import Loggers.Logger;
import Mocks.MockPrintStreamDealer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class FileLoggerTest {
    private String directory = System.getProperty("user.dir") + "/src/test/java/Mocks";
    private String fullPath = directory + "/logs";
    private FileUpdater fileUpdater;
    private Logger logger;

    @BeforeEach
    void setup() {
        fileUpdater = new FileUpdater(directory);
        logger = new FileLogger(fileUpdater);
    }

    @AfterEach
    void tearDown() {
        FileHelper.delete(fullPath);
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