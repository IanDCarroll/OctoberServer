package FunctionalCoreTests.ControllerTests.ResponseGenerationTests;

import Factory.ServerFactory;
import Filers.FileClerk;
import FunctionalCore.Controller.ResponseGeneration.RangeGenerator;
import Helpers.FileHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RangeGeneratorTest {
    private int port = 5000;
    private String directory = System.getProperty("user.dir") + "/src/test/java/Mocks";
    private String configFile = directory + "/mock_routes.yml";
    private RangeGenerator subject;

    @BeforeEach
    void init() {
        ServerFactory factory = new ServerFactory(port, directory, configFile);
        subject = factory.buildRangeGenerator();
    }
    
    @Test
    void generateReturnsTheBodyRange() {
        //Given
        int[] rangeTuple = { 2, 8 };
        byte[] content = "Original content".getBytes();
        String uri = "/a-file-with-a-body";
        String fullPath = directory + uri;
        FileHelper.make(fullPath, content);
        //When
        byte[] actual = subject.generate(RangeGenerator.Code.PARTIAL_CONTENT, uri, rangeTuple);
        //Then
        FileHelper.delete(fullPath);
        String expected = "iginal";
        assertTrue(new String(actual).contains(expected));
        assertFalse(new String(actual).contains(new String(content)));
    }

    @Test
    void generateReturnsA206StartLine() {
        //Given
        int[] rangeTuple = { 2, 8 };
        byte[] content = "Original content".getBytes();
        String uri = "/a-file-with-a-body";
        String fullPath = directory + uri;
        FileHelper.make(fullPath, content);
        //When
        byte[] actual = subject.generate(RangeGenerator.Code.PARTIAL_CONTENT, uri, rangeTuple);
        //Then
        FileHelper.delete(fullPath);
        String expected = "206 Partial Content";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generateReturnsAContentRangeHeader() {
        //Given
        int[] rangeTuple = { 2, 8 };
        byte[] content = "Original content".getBytes();
        String uri = "/a-file-with-a-body";
        String fullPath = directory + uri;
        FileHelper.make(fullPath, content);
        //When
        byte[] actual = subject.generate(RangeGenerator.Code.PARTIAL_CONTENT, uri, rangeTuple);
        //Then
        FileHelper.delete(fullPath);
        String expected = "Content-Range: bytes 2-8/" + String.valueOf(content.length);
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generateReturnsA416StartLine() {
        //Given
        byte[] content = "Original content".getBytes();
        String uri = "/a-file-with-a-body";
        String fullPath = directory + uri;
        FileHelper.make(fullPath, content);
        //When
        byte[] actual = subject.generate(RangeGenerator.Code.RANGE_NOT_SATISFIABLE, uri);
        //Then
        FileHelper.delete(fullPath);
        String expected = "416 Range Not Satisfiable";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generateReturnsAContentRangeHeaderWhenNoRangeTupleIsProvided() {
        //Given
        byte[] content = "Original content".getBytes();
        String uri = "/a-file-with-a-body";
        String fullPath = directory + uri;
        FileHelper.make(fullPath, content);
        //When
        byte[] actual = subject.generate(RangeGenerator.Code.PARTIAL_CONTENT, uri);
        //Then
        FileHelper.delete(fullPath);
        String expected = "Content-Range: bytes */" + String.valueOf(content.length);
        assertTrue(new String(actual).contains(expected));
    }
}