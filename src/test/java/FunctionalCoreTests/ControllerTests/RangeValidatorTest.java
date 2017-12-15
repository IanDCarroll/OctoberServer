package FunctionalCoreTests.ControllerTests;

import Filers.FileClerk;
import FunctionalCore.Controller.RangeValidator;
import Helpers.FileHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RangeValidatorTest {
    String publicDir = System.getProperty("user.dir") + "/src/test/java/Mocks";
    String uri;
    byte[] content;
    RangeValidator subject;

    @BeforeEach
    void setup() {
        uri = "/get-partial";
        String fullPath = publicDir + uri;
        content = "racecar".getBytes();
        FileHelper.make(fullPath, content);
        FileClerk fileClerk = new FileClerk(publicDir);
        subject = new RangeValidator(fileClerk);
    }

    @AfterEach
    void tearDown() {
        String fullPath = publicDir + uri;
        FileHelper.delete(fullPath);
    }

    @Test
    void validReturnsTrueIfTheRangeIsGood() {
        //Given
        String rangeHeader = "Range: bytes=2-6";
        //When
        boolean actual = subject.valid(uri, rangeHeader);
        //Then
        assertTrue(actual);
    }

    @Test
    void validReturnsFalseIfTheRangeStartIsMoreThanTheRangeEnd() {
        //Given
        String rangeHeader = "Range: bytes=6-5";
        //When
        boolean actual = subject.valid(uri, rangeHeader);
        //Then
        assertFalse(actual);
    }

    @Test
    void validReturnsFalseIfTheRangeEndisMoreThanTheFileSize() {
        //Given
        String rangeHeader = "Range: bytes=-" + String.valueOf(content.length + 1);
        //When
        boolean actual = subject.valid(uri, rangeHeader);
        //Then
        assertFalse(actual);
    }

    @Test
    void getRangeReturnsAnIntArrayRepresentingTheStartAndEndOFTheRange() {
        //Given
        String rangeHeader = "Range: bytes=2-6";
        //When
        int[] actual = subject.getRange(uri, rangeHeader);
        //Then
        int[] expected = { 2, 6 };
        assertArrayEquals(expected, actual);
    }

    @Test
    void getRangeHandlesEmptyFirstValue() {
        //Given
        String rangeHeader = "Range: bytes=-6";
        //When
        int[] actual = subject.getRange(uri, rangeHeader);
        //Then
        int[] expected = { 0, 6 };
        assertArrayEquals(expected, actual);
    }

    @Test
    void getRangeHandlesEmptyLastValue() {
        //Given
        String rangeHeader = "Range: bytes=2-";
        //When
        int[] actual = subject.getRange(uri, rangeHeader);
        //Then
        int[] expected = { 2, content.length };
        assertArrayEquals(expected, actual);
    }
}