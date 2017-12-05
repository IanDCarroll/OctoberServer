package FunctionalCoreTests.ParserTests;

import FunctionalCore.Parser.ByteSplitter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ByteSplitterTest {
    private static final byte[] pattern = "\r\n\r\n".getBytes();

    @Test
    void splitByPatternSpitsAByteArrayAtTheFirstMatchItFinds() {
        //Given
        String line1 = "ancient pond";
        String line2 = "a frog jumps in";
        String line3 = "sound of water";
        byte[] sample = (line1 + "\r\n\r\n" + line2 + "\r\n\r\n" + line3).getBytes();
        //When
        byte[][] actual = ByteSplitter.splitByPattern(sample, pattern);
        //Then
        byte[][] expected = { line1.getBytes(), (line2 + "\r\n\r\n" + line3).getBytes() };
        assertArrayEquals(actual, expected);
    }

    @Test
    void splitByPatternReturnsTheOriginalByteArrayPlusAnEmptyOneInA2DArrayIfNoMatchIsFound() {
        //Given
        String line1 = "ancient pond";
        String line2 = "a frog jumps in";
        String line3 = "sound of water";
        byte[] sample = (line1 + "\r\n\n" + line2 + "\r\r\n" + line3).getBytes();
        //When
        byte[][] actual = ByteSplitter.splitByPattern(sample, pattern);
        //Then
        byte[][] expected = { sample, new byte[0] };
        assertArrayEquals(actual, expected);
    }

}