package FunctionalCoreTests.ControllerTests.ResponseGenerationTests;

import FunctionalCore.Controller.ResponseGeneration.OptionsGenerator;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.AllowHeaderSetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.StartLineSetter;
import FunctionalCore.Controller.ResponseGeneration.SuccessGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OptionsGeneratorTest {
    OptionsGenerator subject;

    @BeforeEach
    void setup() {
        StartLineSetter startLineSetter = new StartLineSetter();
        AllowHeaderSetter allowHeaderSetter = new AllowHeaderSetter();
        subject = new OptionsGenerator(startLineSetter, allowHeaderSetter);
    }

    @Test
    void generateReturnsAnAllowHeader() {
        //Given
        String permittedMethods = "GET,OPTIONS,EAT_SPAGETTIOS";
        //When
        byte[] actual = subject.generate(OptionsGenerator.Code.OK, permittedMethods);
        //Then
        String expected = "Allow: " + permittedMethods;
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generateReturns200() {
        //Given
        String permittedMethods = "GET,OPTIONS,EAT_SPAGETTIOS";
        //When
        byte[] actual = subject.generate(OptionsGenerator.Code.OK, permittedMethods);
        //Then
        String expected = "200 OK";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generateReturns405() {
        //Given
        String permittedMethods = "GET,OPTIONS,EAT_SPAGETTIOS";
        //When
        byte[] actual = subject.generate(OptionsGenerator.Code.METHOD_NOT_ALLOWED, permittedMethods);
        //Then
        String expected = "405 Method Not Allowed";
        assertTrue(new String(actual).contains(expected));
    }
}