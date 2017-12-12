package FunctionalCore.Controller;

import FunctionalCore.Request;

import java.util.LinkedHashMap;

public class Controller {
    private ResponseGenerator responseGenerator;
    private LinkedHashMap<String, String> routes;

    public Controller(ResponseGenerator responseGenerator, LinkedHashMap routes) {
        this.responseGenerator = responseGenerator;
        this.routes = routes;
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
        if (request.getMethod().equals("HEAD")) return head();
        if (request.getMethod().equals("OPTIONS")) return options(request);
        return get(request);
    }

    private byte[] head() {
        return responseGenerator.generate200(new String[0]);
    }

    private byte[] options(Request request) {
        String permittedMethods = routes.get(request.getUri());
        return responseGenerator.generate200(permittedMethods);
    }

    private byte[] get(Request request) {
        return responseGenerator.generate200(request.getUriParams());
    }
}
