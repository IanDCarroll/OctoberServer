package FunctionalCore.Controller;

import Filers.FileClerk;
import Filers.FileUpdater;

public class ResponseGenerator {
    private final String[] ok200 = { "200", "OK" };
    Response response;

    public byte[] generate200(String name, String[] params) {
        response = new Response();
        setResponseHead(ok200);
        setParams(params);
        FileClerk fileClerk = new FileClerk();
        byte[] body = fileClerk.checkout(name);
        response.setBody(body);
        return response.getResponse();
    }

    public byte[] generate200() {
        response = new Response();
        setResponseHead(ok200);
        return response.getResponse();
    }

    public byte[] generate200(String permittedMethods) {
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

    private void setResponseHead(String[] codeTuple) {
        response.setStartLine(codeTuple[0], codeTuple[1]);
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
