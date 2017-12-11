package FunctionalCore.Controller;

public class ResponseGenerator {
    Response response;

    public byte[] generate200(String[] params) {
        response = new Response();
        setResponseHead(new String[]{"200", "OK"});
        setParams(params);
        return response.getResponse();
    }

    public byte[] generate404() {
        response = new Response();
        setResponseHead(new String[]{ "404", "Not Found" });
        return response.getResponse();
    }

    private void setResponseHead(String[] codeTuple) {
        response.setStartLine(codeTuple[0], codeTuple[1]);
    }

    public void setParams(String[] params) {
        response.setBody(params);
    }
}
