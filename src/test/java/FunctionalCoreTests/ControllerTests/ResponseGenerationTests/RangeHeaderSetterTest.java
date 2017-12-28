package FunctionalCoreTests.ControllerTests.ResponseGenerationTests;

import Filers.FileClerk;
import FunctionalCore.Controller.ResponseGeneration.RangeHeaderSetter;
import FunctionalCore.Controller.ResponseGeneration.Response;
import Helpers.FileHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RangeHeaderSetterTest {
    private final String publicDir = System.getProperty("user.dir") + "/src/test/java/Mocks";
    private final String testUri = "/test-uri";
    private final String fullPath = publicDir + testUri;
    private byte[] body = "this is a body".getBytes();
    private RangeHeaderSetter subject;

    @BeforeEach
    void setup() {
        FileClerk fileClerk = new FileClerk(publicDir);
        subject = new RangeHeaderSetter(fileClerk);
        FileHelper.make(fullPath, body);
    }

    @AfterEach
    void teardown() {
        FileHelper.delete(fullPath);
    }

    @Test
    void setRangeReturnsAResponseWithAStarredRangeHeaderWhenOnlyPassedAUri() {
        //Given
        Response emptyResponse = new Response();
        //When
        Response actual = subject.setRange(emptyResponse, testUri);
        //Then
        String expected = "Content-Range: bytes */" + String.valueOf(body.length);
        assertTrue(new String(actual.getResponse()).contains(expected));
    }

    @Test
    void setRangereturnsAResponseWithAFullyDeckedOutRangeHeaderWhenPassedStartAndStopWithURI() {
        //Given
        Response emptyResponse = new Response();
        int[] rangeTuple = { 6, 10 };
        //When
        Response actual = subject.setRange(emptyResponse, testUri, rangeTuple);
        //Then
        String expected = "Content-Range: bytes " +
                String.valueOf(rangeTuple[0]) + "-" +
                String.valueOf(rangeTuple[1]) + "/" + String.valueOf(body.length);
        assertTrue(new String(actual.getResponse()).contains(expected));
    }
}