package FunctionalCore.Controller;

import Filers.FileClerk;

public class ResponseGenerator {
    FileClerk fileClerk;

    public ResponseGenerator() {
        this.fileClerk = new FileClerk();
    }

    private final String[] ok200 = { "200", "OK" };
    Response response;

    public byte[] generate200(String name, String[] params) {
        response = new Response();
        setResponseHead(name, ok200);
        setParams(params);
        byte[] body = fileClerk.checkout(name);
        response.setBody(body);
        return response.getResponse();
    }

    public byte[] generate200(String name) {
        response = new Response();
        setResponseHead(name, ok200);
        return response.getResponse();
    }

    public byte[] generate200(String name, String permittedMethods) {
        response = new Response();
        setResponseHead(ok200);
        setAllowHeader(permittedMethods);
        return response.getResponse();
    }

    public byte[] generate404() {
        response = new Response();
        setResponseHead(new String[]{ "404", "Not Found" });
        return response.getResponse();
    }

    public byte[] generate405(String permittedMethods) {
        response = new Response();
        setResponseHead(new String[]{ "405", "Method Not Allowed" });
        setAllowHeader(permittedMethods);
        return response.getResponse();
    }

    private void setResponseHead(String name, String[] codeTuple) {
        setResponseHead(codeTuple);
        setBasicHeaders(name);
    }

    private void setResponseHead(String[] codeTuple) {
        response.setStartLine(codeTuple[0], codeTuple[1]);
    }

    private void setBasicHeaders(String name) {
        String length = fileClerk.lengthOf(name);
        response.setHeaders(new String[]{ "Content-Length", length, "Content-Type", "text/plain" });
    }

    public void setParams(String[] params) {
        response.setBody(params);
    }

    private void setAllowHeader(String permittedMethods) {
        String commaSeparatedPermissions = permittedMethods.replace(" ", ",");
        String[] allowHeader = { "Allow", commaSeparatedPermissions };
        response.setHeaders(allowHeader);
    }
}
