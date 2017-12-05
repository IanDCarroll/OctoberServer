package FunctionalCoreTests.ParserTests;

import FunctionalCore.Parser.Lumberjack;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LumberjackTest {
    @Test
    void splitByNewLineSplitsAAStringWithAnArbitraryNumberOfNewlines() {
        //Given
        String line1 = "ancient pond";
        String line2 = "a frog jumps in";
        String line3 = "sound of water";
        String sample = line1 + "\n" + line2 + "\n" + line3;
        //When
        String[] actual = Lumberjack.splitByNewLine(sample);
        //Then
        String[] expected = { line1, line2, line3 };
        assertArrayEquals(actual, expected);
    }

    @Test
    void splitBySpaceSplitsAStringWithAnArbitraryNumberOfSpaces() {
        //Given
        String line1 = "Principle";
        String line2 = "Process";
        String line3 = "Result";
        String sample = line1 + " " + line2 + " " + line3;
        //When
        String[] actual = Lumberjack.splitBySpace(sample);
        //Then
        String[] expected = { line1, line2, line3 };
        assertArrayEquals(actual, expected);
    }

    @Test
    void splitByCRLFSpitsAByteArrayAtTheFirstCRLFItFinds() {
        //Given
        String line1 = "ancient pond";
        String line2 = "a frog jumps in";
        String line3 = "sound of water";
        byte[] sample = (line1 + "\r\n\r\n" + line2 + "\r\n\r\n" + line3).getBytes();
        //When
        byte[][] actual = Lumberjack.splitByCRLF(sample);
        //Then
        byte[][] expected = { line1.getBytes(), (line2 + "\r\n\r\n" + line3).getBytes() };
        assertArrayEquals(actual, expected);
    }

    @Test
    void splitByCRLFReturnsTheOriginalByteArrayPlusAnEmptyOneInA2DArrayIfNoCRLFIsFound() {
        //Given
        String line1 = "ancient pond";
        String line2 = "a frog jumps in";
        String line3 = "sound of water";
        byte[] sample = (line1 + "\r\n\n" + line2 + "\r\r\n" + line3).getBytes();
        //When
        byte[][] actual = Lumberjack.splitByCRLF(sample);
        //Then
        byte[][] expected = { sample, new byte[0] };
        assertArrayEquals(actual, expected);
    }

}