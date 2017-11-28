package functionalCore;

public class Parser {
    // takes a string and returns a request object
    // used as a component of the HTTPCore.
    Request request;

    public Parser() {
        this.request = new Request();
    }

    public boolean exists() { return true; }
}
