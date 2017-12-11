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
        return valid(request) ? responseGenerator.generate200(request.getUriParams()) : responseGenerator.generate404();
    }

    private boolean valid(Request request) {
        for (String route : routes.keySet()) {
            if (route.equals(request.getUri())) {
                return true;
            }
        }
        return false;
    }
}
