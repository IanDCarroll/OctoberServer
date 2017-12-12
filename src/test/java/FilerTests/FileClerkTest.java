package FilerTests;

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

    @BeforeEach
    void setup() {
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
    }

    @Test
    void appendAddsToTheFileContents() {
    }

    @Test
    void rewriteReplacesTheFileContentsWithCompletelyNewContents() {
    }

    @Test
    void deleteDeletesTheContentsAlongWithTheFile() {
    }

    @Test
    void checkoutReturnsAnEmptyByteArrayWhenTheFileDoesNotExist() {
    }

}