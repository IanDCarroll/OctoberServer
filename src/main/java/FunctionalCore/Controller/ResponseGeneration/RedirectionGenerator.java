package FunctionalCore.Controller.ResponseGeneration;

public class RedirectionGenerator {
    private StartLineSetter startLineSetter;
    public enum Code {
        FOUND                   (new String[]{"302", "Found"});
        public String[] tuple;
        Code(String[] tuple) {
            this.tuple = tuple;
        }
    }

    public RedirectionGenerator() {
        this.startLineSetter = new StartLineSetter();
    }

    public byte[] generate(Code code, String redirectToThisUri) {
        Response response = new Response();
        startLineSetter.setStartLine(response, code.tuple);
        response.setHeader(Response.Header.LOCATION, redirectToThisUri);
        return response.getHead();
    }
}
