package FunctionalCoreTests.ControllerTests.ResponseGenerationTests;

import Filers.FileClerk;
import FunctionalCore.Controller.ResponseGeneration.ETagGenerator;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.BodySetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.ETagHeaderSetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.StartLineSetter;
import FunctionalCore.Controller.ResponseGeneration.SuccessGenerator;
import FunctionalCore.Request;
import Helpers.FileHelper;
import Mocks.MockRequestDealer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ETagGeneratorTest {
    ETagGenerator subject;
    private String publicDir = System.getProperty("user.dir") + "/src/test/java/Mocks";

    @BeforeEach
    void setup() {
        FileClerk fileClerk = new FileClerk(publicDir);
        StartLineSetter startLineSetter = new StartLineSetter();
        BodySetter bodySetter = new BodySetter(fileClerk);
        ETagHeaderSetter eTagHeaderSetter = new ETagHeaderSetter();
        subject = new ETagGenerator(startLineSetter, bodySetter, eTagHeaderSetter);
    }

    @Test
    void generateReturnsA204StartLine() {
        //Given
        String ifMatch = "c001c0de";
        byte[] content = "Original content".getBytes();
        String uri = "/a-file-with-a-body";
        Request request = MockRequestDealer.eTagRequest(uri, ifMatch);
        String fullPath = publicDir + uri;
        FileHelper.make(fullPath, content);
        //When
        byte[] actual = subject.generate(SuccessGenerator.Code.NO_CONTENT, request);
        //Then
        FileHelper.delete(fullPath);
        String expected = "204 No Content";
        assertTrue(new String(actual).contains(expected));
    }

    @Test
    void generateReturnsAnETagHeader() {
        //Given
        String ifMatch = "c001c0de";
        byte[] content = "Original content".getBytes();
        String uri = "/a-file-with-a-body";
        Request request = MockRequestDealer.eTagRequest(uri, ifMatch);
        String fullPath = publicDir + uri;
        FileHelper.make(fullPath, content);
        //When
        byte[] actual = subject.generate(SuccessGenerator.Code.NO_CONTENT, request);
        //Then
        FileHelper.delete(fullPath);
        String expected = "ETag: " + ifMatch;
        assertTrue(new String(actual).contains(expected));
    }

}