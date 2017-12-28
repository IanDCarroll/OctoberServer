package FunctionalCore.Controller.ResponseGeneration;

import Filers.FileClerk;

import java.util.Arrays;

public class BodySetter {
    FileClerk fileClerk;

    public BodySetter(FileClerk fileClerk) {
        this.fileClerk = fileClerk;
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
        return setBody(response, body);
    }

    public Response setBody(Response response, byte[] body) {
        response.setBody(body);
        return setBasicHeaders(response, response.bodyLength());
    }

    private Response setBasicHeaders(Response response, String length) {
        response.setHeader(Response.Header.CONTENT_LENGTH, length);
        response.setHeader(Response.Header.CONTENT_TYPE, "text/plain");
        return response;
    }
}
