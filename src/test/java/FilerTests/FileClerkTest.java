package FilerTests;

import Filers.FileClerk;
import Helpers.FileHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileClerkTest {
    private static final String name = System.getProperty("user.dir") + "/src/test/java/Mocks/mock_file";
    private static final byte[] content = "Original content".getBytes();
    private File file;
    private FileClerk subject;

    @BeforeEach
    void setup() {
        subject = new FileClerk();
        FileHelper.make(name, content);
    }

    @AfterEach
    void tearDown() { FileHelper.delete(name); }

    @Test
    void checkoutReturnsTheContentsOfAFile() {
        //Given nothing
        //When
        byte[] actual = subject.checkout(name);
        //Then
        assertEquals(new String(content), new String(actual));
    }

    @Test
    void appendAddsToTheFileContents() {
        //Given
        byte[] appendment = "this is a killer appendment, bro".getBytes();
        //When
        subject.append(name, appendment);
        //Then
        String expected = new String(content) + new String(appendment);
        assertTrue(FileHelper.read(name).contains(expected));
    }

    @Test
    void rewriteReplacesTheFileContentsWithCompletelyNewContents() {
        //Given
        byte[] replacement = "this will replace the other stuff".getBytes();
        //When
        subject.rewrite(name, replacement);
        //Then
        assertEquals(new String(replacement), FileHelper.read(name));
    }

    @Test
    void deleteDeletesTheContentsAlongWithTheFile() {
        //Given nothing
        //When
        subject.delete(name);
        //Then
        assertArrayEquals(new byte[0], FileHelper.read(name).getBytes());
    }

    @Test
    void checkoutReturnsAnEmptyByteArrayWhenTheFileDoesNotExist() {
        //Given
        subject.delete(name);
        //When
        byte[] actual = subject.checkout(name);
        //Then
        assertArrayEquals(new byte[0], actual);
    }

    @Test
    void lengthOfReturnsAStringRepresentingTheNumberOfBytesTheResourceHas() {
        //Given the file from @BeforeEach
        //When
        String actual = subject.lengthOf(name);
        //Then
        String expected = String.valueOf(content.length);
        assertEquals(expected, actual);
    }

    @Test
    void lengthOfReturnsAZeroStringIfThereIsNoFile() {
        //Given
        String name = "This is not a file";
        //When
        String actual = subject.lengthOf(name);
        //Then
        String expected = "0";
        assertEquals(expected, actual);
    }

}