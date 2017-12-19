package FunctionalCoreTests.ControllerTests;

import FunctionalCore.Controller.AuthValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthValidatorTest {
    private final String authHeader = "Authorization: Basic YWRtaW46aHVudGVyMg==";
    private AuthValidator subject;


    @BeforeEach
    void setup() {
        subject = new AuthValidator();
    }

    @Test
    void validReturnsTrueIfTheTwoStringsAreTheSame() {
        //Given
        String equalString = "This String is the same as the other one.";
        String fromConfig = equalString;
        String fromRequest = equalString;
        //When
        boolean actual = subject.valid(fromConfig, fromRequest);
        //Then
        assertTrue(actual);
    }

    @Test
    void validReturnsFalseIfTheTwoStringsAreNotTheSame() {
        //Given
        String fromConfig= "This String is not the same as the other one.";
        String fromRequest ="this is a different string";
        //When
        boolean actual = subject.valid(fromConfig, fromRequest);
        //Then
        assertFalse(actual);
    }

    @Test
    void getAuthHeaderReturnsTheAuthHeaderFromAListOfHeaders() {
        //Given
        String[] headers = { "header1: value1", authHeader, "header3: value3" };
        //When
        String actual = subject.getAuthHeader(headers);
        //Then
        assertEquals(authHeader, actual);
    }

    @Test
    void getAuthHeaderReturnsAnEmptyStringIfNoAuthHeaderIsFound() {
        //Given
        String[] headers = { "header1: value1", "header2: value2", "header3: value3" };
        //When
        String actual = subject.getAuthHeader(headers);
        //Then
        String expected = "";
        assertEquals(expected, actual);
    }

    @Test
    void getAuthReturnsTheDecodedAuthValueAsAString() {
        //Given an encoded authorization
        //When
        String actual = subject.getAuth(authHeader);
        //Then
        String expected = "admin:hunter2";
        assertEquals(expected, actual);
    }

}