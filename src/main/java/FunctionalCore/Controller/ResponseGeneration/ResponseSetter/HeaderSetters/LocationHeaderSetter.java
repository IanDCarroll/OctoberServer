package FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters;

import FunctionalCore.Controller.ResponseGeneration.Response;

public class LocationHeaderSetter {
    public Response setLocation(Response response, String location) {
        response.setHeader(Response.Header.LOCATION, location);
        return response;
    }
}
