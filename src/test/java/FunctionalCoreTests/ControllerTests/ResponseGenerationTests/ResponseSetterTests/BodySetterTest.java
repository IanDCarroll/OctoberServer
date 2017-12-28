package FunctionalCoreTests.ControllerTests.ResponseGenerationTests.ResponseSetterTests;

import Filers.FileClerk;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.BodySetter;
import FunctionalCore.Controller.ResponseGeneration.Response;
import Helpers.FileHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BodySetterTest {
    private final String publicDir = System.getProperty("user.dir") + "/src/test/java/Mocks";
    private final String testUri = "/test-uri";
    private final String fullPath = publicDir + testUri;
    private byte[] body = "this is a body".getBytes();
    private BodySetter subject;

    @BeforeEach
    void setup() {
        FileClerk fileClerk = new FileClerk(publicDir);
        subject = new BodySetter(fileClerk);
        FileHelper.make(fullPath, body);
    }

    @AfterEach
    void teardown() {
        FileHelper.delete(fullPath);
    }

    @Test
    void setParamsWithBodyReturnsAResponseWithASetBodyAndParams() {
        //Given
        Response emptyResponse = new Response();
        String[] params = { "this=this", "that=that" };
        //When
        Response actual = subject.setParamsWithBody(emptyResponse, testUri, params);
        //Then
        String expected = params[0] + params[1] + new String(body);
        assertTrue(new String(actual.getResponse()).contains(expected));
    }

    @Test
    void setBodySetsAbodywithAGivenURI() {
        //Given
        Response emptyResponse = new Response();
        //When
        Response actual = subject.setBody(emptyResponse, testUri);
        //Then
        String expected = new String(body);
        assertTrue(new String(actual.getResponse()).contains(expected));
    }

    @Test
    void setBodySetsABodyWithAGivenByteArray() {
        //Given
        Response emptyResponse = new Response();
        //When
        Response actual = subject.setBody(emptyResponse, body);
        //Then
        String expected = new String(body);
        assertTrue(new String(actual.getResponse()).contains(expected));
    }

    @Test
    void setBodySetsABodyWithTheRangeSpecified() {
        //Given
        Response emptyResponse = new Response();
        int[] rangeTuple = { 6, 10 };
        //When
        Response actual = subject.setBody(emptyResponse, testUri, rangeTuple);
        //Then
        String unexpected = new String(body);
        assertFalse(new String(actual.getResponse()).contains(unexpected));
        String expected = new String(Arrays.copyOfRange(body, rangeTuple[0], rangeTuple[1]));
        assertTrue(new String(actual.getResponse()).contains(expected));
    }

}