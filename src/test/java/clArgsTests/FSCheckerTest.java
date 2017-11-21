package clArgsTests;

import clArgs.FSChecker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FSCheckerTest {

    @Test
    void checkDirReturnsTrueIfDirectoryIsFoundInTheFileSystem() {
        //Given
        String directory = System.getProperty("user.dir") + "/src/test/java/clArgsTests";
        //When
        boolean isItLegit = FSChecker.checkDir(directory);
        //Then
        assertTrue(isItLegit);
    }

    @Test
    void checkDirReturnsFalseIfDirectoryIsNotFoundInTheFileSystem() {
        //Given
        String directory = "This is totally not a directory";
        //When
        boolean isItLegit = FSChecker.checkDir(directory);
        //Then
        assertFalse(isItLegit);
    }

    @Test
    void checkFileReturnsTrueIfFileIsFoundInTheFileSystem() {
        //Given
        String file = System.getProperty("user.dir") + "/src/test/java/clArgsTests/FSCheckerTest.java";
        //When
        boolean isItLegit = FSChecker.checkFile(file);
        //Then
        assertTrue(isItLegit);
    }

    @Test
    void checkFileReturnsFalseIfFileIsNotFoundInTheFileSystem() {
        //Given
        String file = "This is totally not a file";
        //When
        boolean isItLegit = FSChecker.checkFile(file);
        //Then
        assertFalse(isItLegit);
    }

    @Test
    void checkFileReturnsFalseIfFileIsActuallyADirectory() {
        //Given
        String file = System.getProperty("user.dir") + "/src/test/java/clArgsTests";
        //When
        boolean isItLegit = FSChecker.checkFile(file);
        //Then
        assertFalse(isItLegit);
    }

    @Test
    void checkDirReturnsFalseIfDirectoryIsActuallyAFile() {
        //Given
        String file = System.getProperty("user.dir") + "/src/test/java/clArgsTests/FSCheckerTest.java";
        //When
        boolean isItLegit = FSChecker.checkDir(file);
        //Then
        assertFalse(isItLegit);
    }

}