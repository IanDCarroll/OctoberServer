package FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters;

import FunctionalCore.Controller.ResponseGeneration.Response;

public class SetCookieHeaderSetter {
    public Response setSetCookie(Response response) {
        response.setHeader(Response.Header.SET_COOKIE, generateCookie());
        return response;
    }

    public String generateCookie() {
        return "This is a generated cookie";
    }
}
