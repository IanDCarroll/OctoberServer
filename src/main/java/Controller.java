import java.util.LinkedHashMap;

public class Controller {
    private ResponseGenerator responseGenerator;
    private LinkedHashMap routes;

    public Controller(ResponseGenerator responseGenerator, LinkedHashMap routes) {
        System.out.println("controller initialized");
        this.responseGenerator = responseGenerator;
        this.routes = routes;
    }

    public boolean exists() { return true; }
}
