package FunctionalCoreTests.ControllerTests.SubControllerTests;

import Factory.ServerFactory;
import FunctionalCore.Controller.SubControllers.RangeController;
import FunctionalCore.Request;
import Helpers.FileHelper;
import Mocks.MockRequestDealer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class RangeControllerTest {
    private int port = 5000;
    private String directory = System.getProperty("user.dir") + "/src/test/java/Mocks";
    private String configFile = directory + "/mock_routes.yml";
    private RangeController subject;
    private LinkedHashMap<String, String> properties;
    private LinkedHashMap<String, LinkedHashMap<String, String>> routes;
    private String uri = "/get-partial";
    private String fullPath = directory + uri;
    private byte[] content;

    @BeforeEach
    void setup() {
        uri = "/get-partial";
        content = "racecar".getBytes();
        FileHelper.make(fullPath, content);
        properties = new LinkedHashMap<>();
        routes = new LinkedHashMap<>();
        ServerFactory factory = new ServerFactory(port, directory, configFile);
        subject = factory.buildRangeController();
    }

    @AfterEach
    void tearDown() {
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
    void relevantReturnsTrueIfThereIsARangeHeaderInTheRequest() {
        //Given
        Request request = MockRequestDealer.partialRequest(uri);
        //When
        boolean actual = subject.relevant(request, routes);
        //Then
        assertTrue(actual);
    }

    @Test
    void relevantReturnsFalseIfThereIsNoRangeHeaderInTheRequest() {
        //Given
        Request request = MockRequestDealer.getRequest(uri);
        //When
        boolean actual = subject.relevant(request, routes);
        //Then
        assertFalse(actual);
    }

    @Test
    void generateGeneratesA206ResponseIfTheRangeIsGood() {
        //Given
        Request request = MockRequestDealer.partialRequest(uri);
        //When
        byte[] actual = subject.generate(request, routes);
        //Then
        String expected = "206 Partial Content";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generateGeneratesA416ResponseIfTheRangeIsBad() {
        //Given
        Request request = MockRequestDealer.badPartialRequest(uri);
        //When
        byte[] actual = subject.generate(request, routes);
        //Then
        String expected = "416 Range Not Satisfiable";
        assertTrue(new String(actual).contains(expected));
    }
}