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
        int[] range = { 2, 6 };
        //When
        boolean actual = subject.valid(uri, range);
        //Then
        assertTrue(actual);
    }

    @Test
    void validReturnsTrueIfTheRangeIsEqual() {
        //Given
        int[] range = { 6, 6 };
        //When
        boolean actual = subject.valid(uri, range);
        //Then
        assertTrue(actual);
    }

    @Test
    void validReturnsTrueIfTheRangeIsEqualToTheFileLength() {
        //Given
        int[] range = { 2, content.length };
        //When
        boolean actual = subject.valid(uri, range);
        //Then
        assertTrue(actual);
    }

    @Test
    void validReturnsFalseIfTheRangeStartIsMoreThanTheRangeEnd() {
        //Given
        int[] range = { 4, 3 };
        //When
        boolean actual = subject.valid(uri, range);
        //Then
        assertFalse(actual);
    }

    @Test
    void validReturnsFalseIfTheRangeEndIsMoreThanTheFileSize() {
        //Given
        int[] range = { 0, (content.length + 1) };
        //When
        boolean actual = subject.valid(uri, range);
        //Then
        assertFalse(actual);
    }

    @Test
    void getRangeReturnsAnIntArrayRepresentingTheStartAndEndOFTheRange() {
        //Given
        String rangeHeader = "Range: bytes=0-4";
        //When
        int[] actual = subject.getRange(uri, rangeHeader);
        //Then
        int[] expected = { 0, 4 };
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
        String rangeHeader = "Range: bytes=4-";
        //When
        int[] actual = subject.getRange(uri, rangeHeader);
        //Then
        int[] expected = { 4, content.length };
        assertArrayEquals(expected, actual);
    }

    @Test
    void getRangeHeaderGetsTheRangeHeaderFromAnArrayOfHeaders() {
        //Given
        String rangeHeader = "Range: bytes=2-6";
        String[] headers = { "Header1: value1", rangeHeader, "Header2: value2" };
        //When
        String actual = subject.getRangeHeader(headers);
        //Then
        assertEquals(rangeHeader, actual);
    }

    @Test
    void getRangeHeaderReturnsEmptyStringIfNoRangeHeaderIsFound() {
        //Given
        String[] headers = { "Header1: value1", "Header2: value2" };
        //When
        String actual = subject.getRangeHeader(headers);
        //Then
        assertEquals("", actual);
    }
}