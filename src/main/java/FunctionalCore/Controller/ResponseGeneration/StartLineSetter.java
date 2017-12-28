package FunctionalCore.Controller.ResponseGeneration;

public class StartLineSetter {
    public Response setStartLine(Response response, String[] codeTuple) {
        response.setStartLine(codeTuple[0], codeTuple[1]);
        return response;
    }
}
