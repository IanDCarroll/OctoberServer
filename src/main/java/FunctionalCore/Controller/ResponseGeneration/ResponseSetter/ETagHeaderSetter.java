package FunctionalCore.Controller.ResponseGeneration.ResponseSetter;

import FunctionalCore.Controller.ResponseGeneration.Response;

public class ETagHeaderSetter {
    public Response setETag(Response response, String ifMatch) {
        response.setHeader(Response.Header.SET_ETAG, generateETag(ifMatch));
        return response;
    }

    public String generateETag(String ifMatch) {
        return ifMatch;
    }
}
