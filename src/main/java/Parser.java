public class Parser {
    // takes a string and returns a request object
    // used as a decorator between ClientSocket and Controller
    Request request;

    public Parser() {
        this.request = new Request();
    }

    public boolean exists() { return true; }
}
