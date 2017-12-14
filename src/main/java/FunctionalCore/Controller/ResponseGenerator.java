package FunctionalCore.Controller;

import Filers.FileClerk;

public class ResponseGenerator {
    FileClerk fileClerk;

    public ResponseGenerator(FileClerk fileClerk) {
        this.fileClerk = fileClerk;
    }

    private final String[] ok200 = { "200", "OK" };
    Response response;

    public byte[] generate200(String uri, String[] params) {
        response = new Response();
        setResponseStartLine(ok200);
        setParamsWithBody(uri, params);
        return response.getResponse();
    }

    public byte[] generate200Head(String uri) {
        response = new Response();
        setResponseStartLine(ok200);
        setBody(uri);
        return response.getHead();
    }

    public byte[] generate200Options(String permittedMethods) {
        response = new Response();
        setResponseStartLine(ok200);
        HeaderGenerator.setBasics(response);
        HeaderGenerator.setAllow(response, permittedMethods);
        return response.getHead();
    }

    public byte[] generate404() {
        response = new Response();
        setResponseStartLine(new String[]{ "404", "Not Found" });
        return response.getHead();
    }

    public byte[] generate405(String permittedMethods) {
        response = new Response();
        setResponseStartLine(new String[]{ "405", "Method Not Allowed" });
        HeaderGenerator.setAllow(response, permittedMethods);
        return response.getHead();
    }

    private void setResponseStartLine(String[] codeTuple) {
        response.setStartLine(codeTuple[0], codeTuple[1]);
    }

    public void setParamsWithBody(String uri, String[] params) {
        response.setBody(params);
        setBody(uri);
    }

    private void setBody(String uri) {
        byte[] body = fileClerk.checkout(uri);
        response.setBody(body);
        HeaderGenerator.setBasics(response);
    }
}
