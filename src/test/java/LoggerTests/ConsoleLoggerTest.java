package LoggerTests;

import Loggers.ConsoleLogger;
import Loggers.Logger;
import Mocks.MockPrintStreamDealer;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleLoggerTest {
    @Test
    void logReturnsALogEntryWithATimeStamp() {
        //Given
        PrintStream printStream = MockPrintStreamDealer.printStream;
        Logger logger = new ConsoleLogger(printStream);
        String entry = "This is an entry";
        String partOfTheYearInATimeStamp = "20";
        //When
        logger.log(entry);
        //Then
        assertTrue(MockPrintStreamDealer.getEntry().contains(entry));
        assertTrue(MockPrintStreamDealer.getEntry().contains(partOfTheYearInATimeStamp));
    }

}