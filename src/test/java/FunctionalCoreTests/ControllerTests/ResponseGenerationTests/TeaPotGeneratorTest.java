package FunctionalCoreTests.ControllerTests.ResponseGenerationTests;

import Filers.FileClerk;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.BodySetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.StartLineSetter;
import FunctionalCore.Controller.ResponseGeneration.TeaPotGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeaPotGeneratorTest {
    private TeaPotGenerator subject;

    @BeforeEach
    void setup() {
        StartLineSetter startLineSetter = new StartLineSetter();
        String publicDir = System.getProperty("user.dir") + "/src/test/java/Mocks";
        FileClerk fileClerk = new FileClerk(publicDir);
        BodySetter bodySetter = new BodySetter(fileClerk);
        subject = new TeaPotGenerator(startLineSetter, bodySetter);
    }

    @Test
    void generate418ReturnsA418StartLine() {
        //Given nothing
        //When
        byte[] actual = subject.generate(TeaPotGenerator.Code.IM_A_TEAPOT);
        //Then
        String expected = "418 I'm a teapot";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generate418ReturnsA418MessageBody() {
        //Given nothing
        //When
        byte[] actual = subject.generate(TeaPotGenerator.Code.IM_A_TEAPOT);
        //Then
        String expected = "\r\n\r\nI'm a teapot";
        assertTrue(new String(actual).contains(expected));
    }

}