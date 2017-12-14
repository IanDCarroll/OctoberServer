package FilerTests;

import Filers.Appender;
import Filers.FileUpdater;
import Helpers.FileHelper;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FileUpdaterTest {
    private static final byte[] content = "Original content".getBytes();
    private static String publicDir = System.getProperty("user.dir") + "/src/test/java/Mocks";
    private static final String uri = "/mock_file";
    private static final String fullPath = publicDir + uri;
    private static Appender subject = new FileUpdater(publicDir);

    @BeforeAll
    static void setup() {
        FileHelper.make(fullPath, content);
    }

    @AfterAll
    static void tearDown() {
        FileHelper.delete(fullPath);
    }

    @Test
    void appendAddsBytesToTheEndOfAFile() {
        //Given
        byte[] appendment = "this is a killer appendment, bro".getBytes();
        //When
        subject.append(uri, appendment);
        //Then
        assertTrue(FileHelper.read(fullPath).contains(new String(appendment)));
    }

    @Test
    void appendDoesNotDisturbOriginalFileContent() {
        //Given
        byte[] secondAappendment = "this is another killer appendment, bro".getBytes();
        //When
        subject.append(uri, secondAappendment);
        //Then
        assertTrue(FileHelper.read(fullPath).contains(new String(content)));
    }

    @Test
    void appendCanBeAppliedAgainAndAgainToKeepAddingContent() {
        //Given
        byte[] appendment3 = "this is a sick appendment, bro".getBytes();
        byte[] appendment4 = "this is a lit appendment, bro".getBytes();
        byte[] appendment5 = "this is a boss appendment, bro".getBytes();
        //When
        subject.append(uri, appendment3);
        subject.append(uri, appendment4);
        subject.append(uri, appendment5);
        //Then
        assertTrue(FileHelper.read(fullPath).contains(new String(appendment3)));
        assertTrue(FileHelper.read(fullPath).contains(new String(appendment4)));
        assertTrue(FileHelper.read(fullPath).contains(new String(appendment5)));
    }

    @Test
    void appendDoesNotDisturbEarlierAppendments() {
        //Given
        String earlierAppendments = FileHelper.read(uri);
        byte[] finalAppendment = "Dang, dude! This is the end!".getBytes();
        //When
        subject.append(uri, finalAppendment);
        //Then
        assertTrue(FileHelper.read(fullPath).contains(earlierAppendments));
    }

}