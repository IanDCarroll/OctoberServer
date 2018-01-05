package FunctionalCore.Controller.ResponseGeneration;

import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.*;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.ETagHeaderSetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.SetCookieHeaderSetter;

public class SuccessGenerator {
    Response response;
    StartLineSetter startLineSetter;
    BodySetter bodySetter;

    public enum Code {
        OK(new String[]{"200", "OK"}),
        NO_CONTENT(new String[]{"204", "No Content"});
        public String[] tuple;

        Code(String[] tuple) {
            this.tuple = tuple;
        }
    }

    public SuccessGenerator(StartLineSetter startLineSetter, BodySetter bodySetter) {
        this.startLineSetter = startLineSetter;
        this.bodySetter = bodySetter;
    }

    public byte[] generate(Code code, String uri) {
        response = new Response();
        startLineSetter.setStartLine(response, code.tuple);
        bodySetter.setBody(response, uri);
        return response.getResponse();
    }

    public byte[] generate(Code code, String uri, String[] params) {
        response = new Response();
        startLineSetter.setStartLine(response, code.tuple);
        bodySetter.setParamsWithBody(response, uri, params);
        return response.getResponse();
    }


    public byte[] generateHead(Code code, String uri) {
        response = new Response();
        startLineSetter.setStartLine(response, code.tuple);
        bodySetter.setBody(response, uri);
        return response.getHead();
    }
}
