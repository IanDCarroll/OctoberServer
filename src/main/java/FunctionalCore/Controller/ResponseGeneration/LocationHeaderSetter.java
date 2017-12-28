package FunctionalCore.Controller.ResponseGeneration;

public class LocationHeaderSetter {
    public Response setLocation(Response response, String location) {
        response.setHeader(Response.Header.LOCATION, location);
        return response;
    }
}
