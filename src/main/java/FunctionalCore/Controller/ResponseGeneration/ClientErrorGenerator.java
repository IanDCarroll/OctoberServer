package FunctionalCore.Controller.ResponseGeneration;

import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.*;

public class ClientErrorGenerator {
    StartLineSetter startLineSetter;

    public enum Code {
        BAD_REQUEST             (new String[]{"400", "Bad Request"}),
        NOT_FOUND               (new String[]{"404", "Not Found"});
        public String[] tuple;
        Code(String[] tuple) {
            this.tuple = tuple;
        }
    }

    public ClientErrorGenerator(StartLineSetter startLineSetter) {
        this.startLineSetter = startLineSetter;
    }

    Response response;

    public byte[] generate(Code code) {
        response = new Response();
        startLineSetter.setStartLine(response, code.tuple);
        return response.getHead();
    }
}
