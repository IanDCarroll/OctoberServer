package FunctionalCore.Controller.ResponseGeneration;

public class AllowHeaderSetter {
    public Response setAllow(Response response, String allowed) {
        response.setHeader(Response.Header.ALLOW, allowed);
        return response;
    }
}
