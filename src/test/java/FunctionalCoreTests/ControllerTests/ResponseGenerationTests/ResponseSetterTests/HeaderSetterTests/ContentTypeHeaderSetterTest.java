package FunctionalCoreTests.ControllerTests.ResponseGenerationTests.ResponseSetterTests.HeaderSetterTests;

import FunctionalCore.Controller.ResponseGeneration.Response;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.ContentTypeHeaderSetter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContentTypeHeaderSetterTest {
    ContentTypeHeaderSetter subject;

    @BeforeEach
    void setup() {
        subject = new ContentTypeHeaderSetter();
    }

    @Test
    void setContentTypeReturnsAResponseWithAContentTypeHeaderTextHtmlAsDefault() {
        //Given
        Response emptyResponse = new Response();
        String testUri = "/any-old-resource";
        //When
        Response actual = subject.setContentType(emptyResponse, testUri);
        //Then
        String expected = "Content-Type: text/html";
        assertTrue(new String(actual.getResponse()).contains(expected));
    }

    @Test
    void setContentTypeReturnsAResponseWithAContentTypeHeaderTextHtmlWhenTheUriIsHTML() {
        //Given
        Response emptyResponse = new Response();
        String testUri = "/resource.html";
        //When
        Response actual = subject.setContentType(emptyResponse, testUri);
        //Then
        String expected = "Content-Type: text/html";
        assertTrue(new String(actual.getResponse()).contains(expected));
    }

    @Test
    void setContentTypeReturnsAResponseWithAContentTypeHeaderTextPlainWhenTheUriIsTxt() {
        //Given
        Response emptyResponse = new Response();
        String testUri = "/resource.txt";
        //When
        Response actual = subject.setContentType(emptyResponse, testUri);
        //Then
        String expected = "Content-Type: text/plain";
        assertTrue(new String(actual.getResponse()).contains(expected));
    }

    @Test
    void setContentTypeReturnsAResponseWithAContentTypeHeaderImageJpegWhenTheUriIsAJpeg() {
        //Given
        Response emptyResponse = new Response();
        String testUri = "/resource.jpeg";
        //When
        Response actual = subject.setContentType(emptyResponse, testUri);
        //Then
        String expected = "Content-Type: image/jpeg";
        assertTrue(new String(actual.getResponse()).contains(expected));
    }

    @Test
    void setContentTypeReturnsAResponseWithAContentTypeHeaderImagePngWhenTheUriIsAPng() {
        //Given
        Response emptyResponse = new Response();
        String testUri = "/resource.png";
        //When
        Response actual = subject.setContentType(emptyResponse, testUri);
        //Then
        String expected = "Content-Type: image/png";
        assertTrue(new String(actual.getResponse()).contains(expected));
    }

    @Test
    void setContentTypeReturnsAResponseWithAContentTypeHeaderImageGifWhenTheUriIsAGif() {
        //Given
        Response emptyResponse = new Response();
        String testUri = "/resource.gif";
        //When
        Response actual = subject.setContentType(emptyResponse, testUri);
        //Then
        String expected = "Content-Type: image/gif";
        assertTrue(new String(actual.getResponse()).contains(expected));
    }
}