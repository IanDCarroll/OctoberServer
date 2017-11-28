package FunctionalCore;

public class ResponseGenerator {
    // parent class of all response generators
    // each response inherits from response generator for all common behavior
    // Interface Segregation handled by a lib/filereader for those that need such things
    public Response response;

    public ResponseGenerator() {
        this.response = new Response();
    }

    public boolean exists() { return true; }
}
