package FunctionalCore.Controller.ResponseGeneration.ResponseSetter;

import FunctionalCore.Controller.ResponseGeneration.Response;

public class LocationHeaderSetter {
    public Response setLocation(Response response, String location) {
        response.setHeader(Response.Header.LOCATION, location);
        return response;
    }
}
