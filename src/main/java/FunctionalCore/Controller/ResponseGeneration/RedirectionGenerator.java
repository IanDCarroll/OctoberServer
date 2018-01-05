package FunctionalCore.Controller.ResponseGeneration;

import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.LocationHeaderSetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.StartLineSetter;

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

    public RedirectionGenerator(StartLineSetter startLineSetter, LocationHeaderSetter locationHeaderSetter) {
        this.startLineSetter = startLineSetter;
        this.locationHeaderSetter = locationHeaderSetter;
    }

    public byte[] generate(Code code, String redirectToThisUri) {
        Response response = new Response();
        startLineSetter.setStartLine(response, code.tuple);
        locationHeaderSetter.setLocation(response, redirectToThisUri);
        return response.getHead();
    }
}
