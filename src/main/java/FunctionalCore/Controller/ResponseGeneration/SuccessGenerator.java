package FunctionalCore.Controller.ResponseGeneration;

import Filers.FileClerk;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.*;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.AllowHeaderSetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.ETagHeaderSetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.RangeHeaderSetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.SetCookieHeaderSetter;

public class SuccessGenerator {
    private Response response;
    private StartLineSetter startLineSetter;
    private BodySetter bodySetter;
    private RangeHeaderSetter rangeHeaderSetter;
    private AllowHeaderSetter allowHeaderSetter;
    private SetCookieHeaderSetter setCookieHeaderSetter;
    private ETagHeaderSetter eTagHeaderSetter;

    public enum Code {
        OK(new String[]{"200", "OK"}),
        NO_CONTENT(new String[]{"204", "No Content"}),
        PARTIAL_CONTENT(new String[]{"206", "Partial Content"});
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
        this.setCookieHeaderSetter = new SetCookieHeaderSetter();
        this.eTagHeaderSetter = new ETagHeaderSetter();
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

    public byte[] generate(Code code, String uri, String ifMatch) {
        response = new Response();
        startLineSetter.setStartLine(response, code.tuple);
        eTagHeaderSetter.setETag(response, ifMatch);
        bodySetter.setBody(response, uri);
        return response.getResponse();
    }

    public byte[] generate(Code code, String uri, int[] rangeTuple) {
        response = new Response();
        startLineSetter.setStartLine(response, code.tuple);
        bodySetter.setBody(response, uri, rangeTuple);
        rangeHeaderSetter.setRange(response, uri, rangeTuple);
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

    public byte[] generateSetCookie(Code code) {
        response = new Response();
        startLineSetter.setStartLine(response, code.tuple);
        setCookieHeaderSetter.setSetCookie(response);
        bodySetter.setBody(response, "Eat".getBytes());
        return response.getResponse();
    }

    public byte[] generateGetCookie(Code code, String uri) {
        String[] cookie = {"mmmm chocolate"};
        return generate(code, uri, cookie);
    }
}
