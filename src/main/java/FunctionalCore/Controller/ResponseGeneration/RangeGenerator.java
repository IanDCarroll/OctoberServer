package FunctionalCore.Controller.ResponseGeneration;

import Filers.FileClerk;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.BodySetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.RangeHeaderSetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.StartLineSetter;

public class RangeGenerator {
    private Response response;
    private StartLineSetter startLineSetter;
    private BodySetter bodySetter;
    private RangeHeaderSetter rangeHeaderSetter;

    public enum Code {
        RANGE_NOT_SATISFIABLE   (new String[]{"416", "Range Not Satisfiable"}),
        PARTIAL_CONTENT(new String[]{"206", "Partial Content"});
        public String[] tuple;

        Code(String[] tuple) {
            this.tuple = tuple;
        }
    }

    public RangeGenerator(StartLineSetter startLineSetter, BodySetter bodySetter, RangeHeaderSetter rangeHeaderSetter) {
        this.startLineSetter = startLineSetter;
        this.bodySetter = bodySetter;
        this.rangeHeaderSetter = rangeHeaderSetter;
    }

    public byte[] generate(Code code, String uri, int[] rangeTuple) {
        response = new Response();
        startLineSetter.setStartLine(response, code.tuple);
        bodySetter.setBody(response, uri, rangeTuple);
        rangeHeaderSetter.setRange(response, uri, rangeTuple);
        return response.getResponse();
    }

    public byte[] generate(Code code, String uri) {
        response = new Response();
        startLineSetter.setStartLine(response, code.tuple);
        rangeHeaderSetter.setRange(response, uri);
        return response.getHead();
    }
}
