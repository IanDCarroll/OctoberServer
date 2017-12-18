package FunctionalCoreTests.ControllerTests;

import FunctionalCore.Controller.HeaderGenerator;
import FunctionalCore.Controller.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeaderGeneratorTest {
    Response response;

    @BeforeEach
    void setup() {
        response = new Response();
    }

    @Test
    void setBasicsSetsContentLengthAndContentTypeWhenNoBodyIsSet() {
        //Given no set Body
        //When
        HeaderGenerator.setBasics(response);
        //Then
        String length = "Content-Length: 0";
        String type = "Content-Type: text/plain";
        assertTrue(new String(response.getHead()).contains(length));
        assertTrue(new String(response.getHead()).contains(type));
    }

    @Test
    void setBasicsSetsContentLengthAndContentTypeWhenABodyIsSet() {
        //Given
        response.setBody("Ragnarok".getBytes());
        //When
        HeaderGenerator.setBasics(response);
        //Then
        String length = "Content-Length: 8";
        String type = "Content-Type: text/plain";
        assertTrue(new String(response.getHead()).contains(length));
        assertTrue(new String(response.getHead()).contains(type));
    }

    @Test
    void setAllowSetsAVaildAllowHeader() {
        //Given
        String permitted = "GET PUT COAGULATE";
        //When
        HeaderGenerator.setAllow(response, permitted);
        //Then
        String allow = "Allow: GET,PUT,COAGULATE";
        assertTrue(new String(response.getHead()).contains(allow));
    }

    @Test
    void setContentRangeSetsAContentRangeHeaderWithAStartAndEnd() {
        //Given
        byte[] fullBody = "dead men tell no tales".getBytes();
        int start = 5;
        int end = 10;
        String length = String.valueOf(fullBody.length);
        //When
        HeaderGenerator.setContentRange(response, start, end, length);
        //Then
        String contentRange = "Content-Range: bytes 5-10/22";
        assertTrue(new String(response.getHead()).contains(contentRange));
    }

    @Test
    void setContentRangeSetsAContentRangeHeaderWithoutAStartAndEnd() {
        //Given
        byte[] fullBody = "dead men tell no tales".getBytes();
        String length = String.valueOf(fullBody.length);
        //When
        HeaderGenerator.setContentRange(response, length);
        //Then
        String contentRange = "Content-Range: bytes */22";
        assertTrue(new String(response.getHead()).contains(contentRange));
    }

    @Test
    void setLocationSetsALocationHeaderWithASpecifiedUri() {
        //Given
        String redirectToThisUri = "/redirect-to-here";
        //When
        HeaderGenerator.setLocation(response, redirectToThisUri);
        //Then
        String location = "Location: " + redirectToThisUri;
        assertTrue(new String(response.getHead()).contains(location));
    }
}