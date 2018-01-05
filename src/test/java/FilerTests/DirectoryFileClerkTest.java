package FilerTests;

import Filers.DirectoryFileClerk;
import Filers.FileClerk;
import Helpers.FileHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectoryFileClerkTest {
    private static final String publicDir = System.getProperty("user.dir") + "/src/test/java/Mocks";
    private static final String root = "/";
    private static final String fileName = "mock_file";
    private static final String uri = root + fileName;
    private static final String fullPath = publicDir + uri;
    private static final byte[] content = "Original content".getBytes();
    private DirectoryFileClerk subject;

    @BeforeEach
    void setup() {
        subject = new DirectoryFileClerk(publicDir);
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
    void checkoutReturnsTheContentsOfADirectory() {
        //Given nothing
        //When
        byte[] actual = subject.checkout(root);
        //Then
        assertTrue(new String(actual).contains(fileName));
    }

    @Test
    void checkoutReturnsTheContentsOfADirectoryAsAnHTMLLink() {
        //Given nothing
        //When
        byte[] actual = subject.checkout(root);
        //Then
        String expected = "<a href=\"" + uri + "\">" + fileName + "</a>";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void checkoutReturnsTheOpeningOfAnHTMLDocument() {
        //Given nothing
        //When
        byte[] actual = subject.checkout(root);
        //Then
        String expected = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<title></title>\n" +
                "</head>\n" +
                "<body>";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void checkoutReturnsTheClosingOfAnHTMLDocument() {
        //Given nothing
        //When
        byte[] actual = subject.checkout(root);
        //Then
        String expected = "\n</body>\n</html>";
        assertTrue(new String(actual).contains(expected));
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