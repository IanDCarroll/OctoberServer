package FunctionalCore.Controller.ResponseGeneration;

public class AuthenticateHeaderSetter {
    private final String meansOfAuthentication = "Basic realm=\"Access to URI\"";

    public Response setWWWAuth(Response response) {
        response.setHeader(Response.Header.WWW_AUTHENTICATE, meansOfAuthentication);
        return response;
    }
}
