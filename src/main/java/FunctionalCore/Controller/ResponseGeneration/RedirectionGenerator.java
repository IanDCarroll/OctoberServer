package FunctionalCore.Controller.ResponseGeneration;

public class RedirectionGenerator {
    private StartLineSetter startLineSetter;
    private LocationHeaderSetter locationHeaderSetter;
    public enum Code {
        FOUND                   (new String[]{"302", "Found"});
        public String[] tuple;
        Code(String[] tuple) {
            this.tuple = tuple;
        }
    }

    public RedirectionGenerator() {
        this.startLineSetter = new StartLineSetter();
        this.locationHeaderSetter = new LocationHeaderSetter();
    }

    public byte[] generate(Code code, String redirectToThisUri) {
        Response response = new Response();
        startLineSetter.setStartLine(response, code.tuple);
        locationHeaderSetter.setLocation(response, redirectToThisUri);
        return response.getHead();
    }
}
