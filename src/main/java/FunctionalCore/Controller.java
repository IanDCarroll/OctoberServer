package FunctionalCore;

import java.util.LinkedHashMap;

public class Controller {
    private ResponseGenerator responseGenerator;
    private LinkedHashMap<String, String> routes;

    public Controller(ResponseGenerator responseGenerator, LinkedHashMap routes) {
        this.responseGenerator = responseGenerator;
        this.routes = routes;
    }

    public byte[] getAppropriateResponse(Request request) {
        for (String route : routes.keySet()) {
            if (route.equals(request.getUri())) { return responseGenerator.generate200(); }
        }
        return responseGenerator.generate404();
    }
}
