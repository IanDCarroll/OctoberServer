package FilerTests;

import Filers.FileClerk;
import Helpers.FileHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileClerkTest {
    private static final String name = System.getProperty("user.dir") + "/src/test/java/Mocks/mock_file";
    private static final byte[] content = "Original content".getBytes();
    private File file;
    private FileClerk subject;

    @BeforeEach
    void setup() {
        subject = new FileClerk();
        file = new File(name);
        try {
            FileOutputStream out = new FileOutputStream(file);
            try {
                out.write(content);
                out.close();
            } catch (IOException e) { System.out.println("IOException in Filer tests"); }
        } catch (FileNotFoundException e) { System.out.println("file not found in Filer tests"); }
    }

    @AfterEach
    void tearDown() {
        file.delete();
    }

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

}