package FunctionalCoreTests.ControllerTests.ResponseGenerationTests;

import Factory.ServerFactory;
import FunctionalCore.Controller.ResponseGeneration.TeaPotGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeaPotGeneratorTest {
    private int port = 5000;
    private String directory = System.getProperty("user.dir") + "/src/test/java/Mocks";
    private String configFile = directory + "/mock_routes.yml";
    private TeaPotGenerator subject;

    @BeforeEach
    void setup() {
        ServerFactory factory = new ServerFactory(port, directory, configFile);
        subject = factory.buildTeaPotGenerator();
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