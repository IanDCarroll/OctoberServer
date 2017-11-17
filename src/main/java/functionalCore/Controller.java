package functionalCore;

import java.util.LinkedHashMap;

public class Controller {
    private ResponseGenerator responseGenerator;
    private LinkedHashMap routes;

    public Controller(ResponseGenerator responseGenerator, LinkedHashMap routes) {
        this.responseGenerator = responseGenerator;
        this.routes = routes;
    }

    public boolean exists() { return true; }
}
