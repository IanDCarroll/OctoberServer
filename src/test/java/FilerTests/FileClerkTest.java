package FilerTests;

import Filers.FileClerk;
import Helpers.FileHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileClerkTest {
    private static final String publicDir = System.getProperty("user.dir") + "/src/test/java/Mocks";
    private static final String uri = "/mock_file";
    private static final String fullPath = publicDir + uri;
    private static final byte[] content = "Original content".getBytes();
    private FileClerk subject;

    @BeforeEach
    void setup() {
        subject = new FileClerk(publicDir);
        FileHelper.make(fullPath, content);
    }

    @AfterEach
    void tearDown() { FileHelper.delete(fullPath); }

    @Test
    void checkoutReturnsTheContentsOfAFile() {
        //Given nothing
        //When
        byte[] actual = subject.checkout(uri);
        //Then
        assertEquals(new String(content), new String(actual));
    }

    @Test
    void appendAddsToTheFileContents() {
        //Given
        byte[] appendment = "this is a killer appendment, bro".getBytes();
        //When
        subject.append(uri, appendment);
        //Then
        String expected = new String(content) + new String(appendment);
        assertTrue(FileHelper.read(fullPath).contains(expected));
    }

    @Test
    void rewriteReplacesTheFileContentsWithCompletelyNewContents() {
        //Given
        byte[] replacement = "this will replace the other stuff".getBytes();
        //When
        subject.rewrite(uri, replacement);
        //Then
        assertEquals(new String(replacement), FileHelper.read(fullPath));
    }

    @Test
    void deleteDeletesTheContentsAlongWithTheFile() {
        //Given nothing
        //When
        subject.delete(uri);
        //Then
        assertArrayEquals(new byte[0], FileHelper.read(fullPath).getBytes());
    }

    @Test
    void checkoutReturnsAnEmptyByteArrayWhenTheFileDoesNotExist() {
        //Given
        subject.delete(uri);
        //When
        byte[] actual = subject.checkout(uri);
        //Then
        assertArrayEquals(new byte[0], actual);
    }
}