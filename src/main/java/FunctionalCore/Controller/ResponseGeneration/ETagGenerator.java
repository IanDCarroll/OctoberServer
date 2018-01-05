package FunctionalCore.Controller.ResponseGeneration;

import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.BodySetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.ETagHeaderSetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.StartLineSetter;
import FunctionalCore.Request;

public class ETagGenerator extends SuccessGenerator {
    ETagHeaderSetter eTagHeaderSetter;

    public ETagGenerator(StartLineSetter startLineSetter,
                           BodySetter bodySetter,
                           ETagHeaderSetter eTagHeaderSetter) {
        super(startLineSetter, bodySetter);
        this.eTagHeaderSetter = eTagHeaderSetter;
    }

    public byte[] generate(Code code, Request request) {
        response = new Response();
        startLineSetter.setStartLine(response, code.tuple);
        eTagHeaderSetter.setETag(response, generateETag(request));
        bodySetter.setBody(response, request.getUri());
        return response.getResponse();
    }

    private String generateETag(Request request) {
        String eTag = "badE7a9";
        String prefix = "If-Match: ";
        String header = request.getHeader(prefix);
        if (!header.isEmpty()) { eTag = header.replace(prefix, ""); }
        return eTag;
    }
}
