package FunctionalCore.Controller;

public class ResponseGenerator {
    Response response;

    public byte[] generate200() {
        final String[] code200 = { "200", "OK" };
        setResponseHead(code200);
        return response.getResponse();
    }

    public byte[] generate404() {
        final String[] code404 = { "404", "Not Found" };
        setResponseHead(code404);
        return response.getResponse();
    }

    private void setResponseHead(String[] codeTuple) {
        response = new Response();
        response.setStartLine(codeTuple[0], codeTuple[1]);
    }
}
