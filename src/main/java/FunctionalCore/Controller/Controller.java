package FunctionalCore.Controller;

import Filers.FileClerk;
import FunctionalCore.Request;

import java.util.LinkedHashMap;

public class Controller {
    private ResponseGenerator responseGenerator;
    private LinkedHashMap<String, String> routes;
    private String publicDir = "";
    private FileClerk fileClerk;

    public Controller(ResponseGenerator responseGenerator, LinkedHashMap routes, FileClerk fileClerk) {
        this.responseGenerator = responseGenerator;
        this.routes = routes;
        this.fileClerk = fileClerk;
    }

    public byte[] getAppropriateResponse(Request request) {
        return valid(request);
    }

    private byte[] valid(Request request) {
        for (String route : routes.keySet()) {
            if (route.equals(request.getUri())) {
                return validMethod(request);
            }
        }
        return responseGenerator.generate404();
    }

    private byte[] validMethod(Request request) {
        String permittedMethods = routes.get(request.getUri());
        return (permittedMethods.contains(request.getMethod()))
                ? handleMethod(request)
                : responseGenerator.generate405(permittedMethods);
    }

    private byte[] handleMethod(Request request) {
        if (request.getMethod().equals("HEAD")) return head(request);
        if (request.getMethod().equals("OPTIONS")) return options(request);
        if (request.getMethod().equals("POST")) return post(request);
        if (request.getMethod().equals("PUT")) return put(request);
        if (request.getMethod().equals("DELETE")) return delete(request);
        return get(request);
    }

    private byte[] head(Request request) { return responseGenerator.generate200Head(request.getUri()); }

    private byte[] options(Request request) {
        String permittedMethods = routes.get(request.getUri());
        return responseGenerator.generate200Options(permittedMethods);
    }

    private byte[] post(Request request) {
        fileClerk.append(request.getUri(), request.getBody());
        return get(request);
    }

    private byte[] put(Request request) {
        fileClerk.rewrite(request.getUri(), request.getBody());
        return get(request);
    }

    private byte[] delete(Request request) {
        fileClerk.delete(request.getUri());
        return get(request);
    }

    private byte[] get(Request request) {
        return responseGenerator.generate200(request.getUri(), request.getUriParams());
    }
}
