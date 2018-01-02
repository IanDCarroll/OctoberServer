package FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters;

import FunctionalCore.Controller.ResponseGeneration.Response;

public class ContentLengthHeaderSetter {
    public Response setContentLength(Response response, String lengthValue) {
        response.setHeader(Response.Header.CONTENT_LENGTH, lengthValue);
        return response;
    }
}
