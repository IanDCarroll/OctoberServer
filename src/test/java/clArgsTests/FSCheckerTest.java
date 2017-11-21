package clArgsTests;

import clArgs.FSChecker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FSCheckerTest {

    @Test
    void itsADirReturnsTrueIfDirectoryIsFoundInTheFileSystem() {
        //Given
        String directory = System.getProperty("user.dir") + "/src/test/java/clArgsTests";
        //When
        boolean isItLegit = FSChecker.itsADir(directory);
        //Then
        assertTrue(isItLegit);
    }

    @Test
    void itsADirReturnsFalseIfDirectoryIsNotFoundInTheFileSystem() {
        //Given
        String directory = "This is totally not a directory";
        //When
        boolean isItLegit = FSChecker.itsADir(directory);
        //Then
        assertFalse(isItLegit);
    }

    @Test
    void itsAFileReturnsTrueIfFileIsFoundInTheFileSystem() {
        //Given
        String file = System.getProperty("user.dir") + "/src/test/java/clArgsTests/FSCheckerTest.java";
        //When
        boolean isItLegit = FSChecker.itsAFile(file);
        //Then
        assertTrue(isItLegit);
    }

    @Test
    void itsAFileReturnsFalseIfFileIsNotFoundInTheFileSystem() {
        //Given
        String file = "This is totally not a file";
        //When
        boolean isItLegit = FSChecker.itsAFile(file);
        //Then
        assertFalse(isItLegit);
    }

    @Test
    void itsAFileReturnsFalseIfFileIsActuallyADirectory() {
        //Given
        String file = System.getProperty("user.dir") + "/src/test/java/clArgsTests";
        //When
        boolean isItLegit = FSChecker.itsAFile(file);
        //Then
        assertFalse(isItLegit);
    }

    @Test
    void itsADirReturnsFalseIfDirectoryIsActuallyAFile() {
        //Given
        String file = System.getProperty("user.dir") + "/src/test/java/clArgsTests/FSCheckerTest.java";
        //When
        boolean isItLegit = FSChecker.itsADir(file);
        //Then
        assertFalse(isItLegit);
    }

    @Test
    void validateDirReturnsTheDirIfItsReallyADir() {
        //Given
        String thingToValidate = System.getProperty("user.dir") + "/src/test/java/clArgsTests";
        //When
        String thingThatWasValidated = FSChecker.validateDir(thingToValidate);
        //Then
        assertEquals(thingToValidate, thingThatWasValidated);
    }

    @Test
    void validateDirReturnsTheFileIfItsReallyAFile() {
        //Given
        String thingToValidate = System.getProperty("user.dir") + "/src/test/java/clArgsTests/FSCheckerTest.java";
        //When
        String thingThatWasValidated = FSChecker.validateFile(thingToValidate);
        //Then
        assertEquals(thingToValidate, thingThatWasValidated);
    }

    @Test
    void validateDirThrowsAnIllegalArgumentExceptionForAnyStringIsNotADir() {
        //Given
        String thingToSet = "This is totally not valid";
        //Then
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            FSChecker.validateDir(thingToSet);
        });
        //And
        assertEquals(FSChecker.notInFSMessage(thingToSet), exception.getMessage());
    }

    @Test
    void validateFileThrowsAnIllegalArgumentExceptionForAnyStringIsNotAFile() {
        //Given
        String thingToSet = "This is totally not valid";
        //Then
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            FSChecker.validateFile(thingToSet);
        });
        //And
        assertEquals(FSChecker.notInFSMessage(thingToSet), exception.getMessage());
    }

}