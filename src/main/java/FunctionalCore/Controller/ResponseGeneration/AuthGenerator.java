package FunctionalCore.Controller.ResponseGeneration;

import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.AuthenticateHeaderSetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.StartLineSetter;

public class AuthGenerator {
    StartLineSetter startLineSetter;
    AuthenticateHeaderSetter authenticateHeaderSetter;
    public enum Code {
        UNAUTHORIZED            (new String[]{"401", "Unauthorized"}),
        FORBIDDEN               (new String[]{"403", "Forbidden"});
        public String[] tuple;
        Code(String[] tuple) {
            this.tuple = tuple;
        }
    }

    public AuthGenerator(StartLineSetter startLineSetter, AuthenticateHeaderSetter authenticateHeaderSetter) {
        this.startLineSetter = startLineSetter;
        this.authenticateHeaderSetter = authenticateHeaderSetter;
    }

    Response response;

    public byte[] generate401() {
        response = new Response();
        startLineSetter.setStartLine(response, Code.UNAUTHORIZED.tuple);
        authenticateHeaderSetter.setWWWAuth(response);
        return response.getHead();
    }

    public byte[] generate403() {
        response = new Response();
        startLineSetter.setStartLine(response, Code.FORBIDDEN.tuple);
        return response.getHead();
    }
}
