public class Controller {
    // matches a request object with a response generator via a config.yml
    ResponseGenerator responseGenerator;

    public Controller(ResponseGenerator responseGenerator) {
        System.out.println("controller initialized");
        this.responseGenerator = responseGenerator;
    }

    public boolean exists() { return true; }
}
