package FunctionalCore.Controller.ResponseGeneration;

import Filers.FileClerk;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.AllowHeaderSetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.BodySetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.RangeHeaderSetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.StartLineSetter;

public class SuccessGenerator {
    private Response response;
    private StartLineSetter startLineSetter;
    private BodySetter bodySetter;
    private RangeHeaderSetter rangeHeaderSetter;
    private AllowHeaderSetter allowHeaderSetter;

    public enum Code {
        OK                      (new String[]{"200", "OK"}),
        PARTIAL_CONTENT         (new String[]{"206", "Partial Content"});
        public String[] tuple;
        Code(String[] tuple) {
            this.tuple = tuple;
        }
    }

    public SuccessGenerator(FileClerk fileClerk) {
        this.startLineSetter = new StartLineSetter();
        this.bodySetter = new BodySetter(fileClerk);
        this.rangeHeaderSetter = new RangeHeaderSetter(fileClerk);
        this.allowHeaderSetter = new AllowHeaderSetter();
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

    public byte[] generateOptions(Code code, String permittedMethods) {
        response = new Response();
        startLineSetter.setStartLine(response, code.tuple);
        allowHeaderSetter.setAllow(response, permittedMethods);
        return response.getHead();
    }

    public byte[] generate(Code code, String uri, int[] rangeTuple) {
        response = new Response();
        startLineSetter.setStartLine(response, code.tuple);
        bodySetter.setBody(response, uri, rangeTuple);
        rangeHeaderSetter.setRange(response, uri, rangeTuple);
        return response.getResponse();
    }
}
