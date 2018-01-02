package FunctionalCore.Controller.ResponseGeneration.ResponseSetter;

import Filers.FileClerk;
import FunctionalCore.Controller.ResponseGeneration.Response;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.ContentLengthHeaderSetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.ContentTypeHeaderSetter;

import java.util.Arrays;

public class BodySetter {
    FileClerk fileClerk;
    ContentLengthHeaderSetter contentLengthHeaderSetter;
    ContentTypeHeaderSetter contentTypeHeaderSetter;

    public BodySetter(FileClerk fileClerk) {
        this.fileClerk = fileClerk;
        this.contentLengthHeaderSetter = new ContentLengthHeaderSetter();
        this.contentTypeHeaderSetter = new ContentTypeHeaderSetter();
    }

    public Response setParamsWithBody(Response response, String uri, String[] params) {
        response.setBody(params);
        return setBody(response, uri);
    }

    public Response setBody(Response response, String uri) {
        int[] rangeTuple = { 0, fileClerk.checkout(uri).length };
        return setBody(response, uri, rangeTuple);
    }

    public Response setBody(Response response, String uri, int[] rangeTuple) {
        byte[] body = Arrays.copyOfRange(fileClerk.checkout(uri), rangeTuple[0], rangeTuple[1]);
        return setBody(response, uri, body);
    }

    public Response setBody(Response response, byte[] body) {
        return setBody(response, "", body);
    }

    public Response setBody(Response response, String uri, byte[] body) {
        response.setBody(body);
        return setBasicHeaders(response, uri, response.bodyLength());
    }

    private Response setBasicHeaders(Response response, String uri, String length) {
        contentLengthHeaderSetter.setContentLength(response, length);
        contentTypeHeaderSetter.setContentType(response, uri);
        return response;
    }
}
