package LoggerTests;

import Loggers.Formatter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FormatterTest {
    @Test
    void stampAddsAStringOfMetaDataToTheLogEntry() {
        //Given
        String entry = "This is totally some report from the system";
        String stamp = "This is totally a stamp of metaData to add to the entry";
        //When
        String actual = Formatter.stamp(entry, stamp);
        //Then
        String expected = stamp + "\n" + entry + "\n";
        assertEquals(expected, actual);
    }

}