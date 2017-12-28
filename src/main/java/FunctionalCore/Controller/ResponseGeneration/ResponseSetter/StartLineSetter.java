package FunctionalCore.Controller.ResponseGeneration.ResponseSetter;

import FunctionalCore.Controller.ResponseGeneration.Response;

public class StartLineSetter {
    public Response setStartLine(Response response, String[] codeTuple) {
        response.setStartLine(codeTuple[0], codeTuple[1]);
        return response;
    }
}
