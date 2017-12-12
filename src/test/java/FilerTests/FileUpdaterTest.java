package FilerTests;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileUpdaterTest {
    private static final String name = System.getProperty("user.dir") + "/src/test/java/Mocks/mock_file";
    private static final byte[] content = "Original content".getBytes();
    private static File file;

    @BeforeAll
    static void setup() {
        file = new File(name);
        try {
            FileOutputStream out = new FileOutputStream(file);
            try {
                out.write(content);
                out.close();
            } catch (IOException e) { System.out.println("IOException in Filer tests"); }
        } catch (FileNotFoundException e) { System.out.println("file not found in Filer tests"); }
    }

    @AfterAll
    static void tearDown() {
        file.delete();
    }

    @Test
    void appendAddsBytesToTheEndOfAFile() {
    }

    @Test
    void appendDoesNotDisturbOriginalFileContent() {
    }

    @Test
    void appendCanBeAppliedAgainAndAgainToKeepAddingContent() {
    }

    @Test
    void appendDoesNotDisturbEarlierAppendments() {
    }

}