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
    private static final String name = System.getProperty("user.dir") + "/src/test/java/Mocks/mock_file";
    private static final byte[] content = "Original content".getBytes();
    private static File file;
    private static Appender subject = new FileUpdater();

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
        //Given
        byte[] appendment = "this is a killer appendment, bro".getBytes();
        //When
        subject.append(name, appendment);
        //Then
        assertTrue(FileHelper.read(name).contains(new String(appendment)));
    }

    @Test
    void appendDoesNotDisturbOriginalFileContent() {
        //Given
        byte[] secondAappendment = "this is another killer appendment, bro".getBytes();
        //When
        subject.append(name, secondAappendment);
        //Then
        assertTrue(FileHelper.read(name).contains(new String(content)));
    }

    @Test
    void appendCanBeAppliedAgainAndAgainToKeepAddingContent() {
        //Given
        byte[] appendment3 = "this is a sick appendment, bro".getBytes();
        byte[] appendment4 = "this is a lit appendment, bro".getBytes();
        byte[] appendment5 = "this is a boss appendment, bro".getBytes();
        //When
        subject.append(name, appendment3);
        subject.append(name, appendment4);
        subject.append(name, appendment5);
        //Then
        assertTrue(FileHelper.read(name).contains(new String(appendment3)));
        assertTrue(FileHelper.read(name).contains(new String(appendment4)));
        assertTrue(FileHelper.read(name).contains(new String(appendment5)));
    }

    @Test
    void appendDoesNotDisturbEarlierAppendments() {
        //Given
        String earlierAppendments = FileHelper.read(name);
        byte[] finalAppendment = "Dang, dude! This is the end!".getBytes();
        //When
        subject.append(name, finalAppendment);
        //Then
        assertTrue(FileHelper.read(name).contains(earlierAppendments));
    }

}