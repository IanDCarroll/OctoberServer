package FunctionalCore.Controller.ResponseGeneration;

import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.BodySetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.StartLineSetter;

public class TeaPotGenerator {
    private StartLineSetter startLineSetter;
    private BodySetter bodySetter;
    public enum Code {
        IM_A_TEAPOT(new String[]{"418", "I'm a teapot"});
        public String[] tuple;
        Code(String[] tuple) {
            this.tuple = tuple;
        }
    }

    public TeaPotGenerator(StartLineSetter startLineSetter, BodySetter bodySetter) {
        this.startLineSetter = startLineSetter;
        this.bodySetter = bodySetter;
    }

    public byte[] generate(Code code) {
        Response response = new Response();
        startLineSetter.setStartLine(response, code.tuple);
        bodySetter.setBody(response, code.tuple[1].getBytes());
        return response.getResponse();
    }
}
