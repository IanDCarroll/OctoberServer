package LoggerTests;

import Factory.ServerFactory;
import Helpers.FileHelper;
import Loggers.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileLoggerTest {
    private int port = 5000;
    private String directory = System.getProperty("user.dir") + "/src/test/java/Mocks";
    private String configFile = directory + "/mock_routes.yml";
    private String fullPath = directory + "/logs";
    private Logger logger;

    @BeforeEach
    void setup() {
        ServerFactory factory = new ServerFactory(port, directory, configFile);
        logger = factory.buildLogger();
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