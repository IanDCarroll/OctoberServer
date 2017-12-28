package FunctionalCore.Controller.ResponseGeneration.ResponseSetter;

import FunctionalCore.Controller.ResponseGeneration.Response;

public class AllowHeaderSetter {
    public Response setAllow(Response response, String allowed) {
        response.setHeader(Response.Header.ALLOW, allowed);
        return response;
    }
}
