package FunctionalCoreTests.ParserTests;

import FunctionalCore.Parser.ParamDecoder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParamDecoderTest {
    @Test
    void addSpacesBeforeAndAfterAssignmentOperatorDoesWhatItSays() {
        //Given
        String param = "param1=value1";
        //When
        String actual = ParamDecoder.addSpacesBeforeAndAfterAssignmentOperator(param);
        //Then
        String expected = "param1 = value1";
        assertEquals(expected, actual);
    }

    @Test
    void decodeParamDecodesAnyHexValueDelimited() {
        //Given
        String param = "This%20Beat%20Is%20%42%61%4e%41%4E%61%5A%21";
        //When
        String actual = ParamDecoder.decodeParam(param);
        //Then
        String expected = "This Beat Is BaNANaZ!";
        assertEquals(expected, actual);
    }

}