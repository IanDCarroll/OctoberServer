package FunctionalCore.Controller.ResponseGeneration;

import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.BodySetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.SetCookieHeaderSetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.StartLineSetter;

public class CookieGenerator extends SuccessGenerator {
    SetCookieHeaderSetter setCookieHeaderSetter;

    public CookieGenerator(StartLineSetter startLineSetter,
                           BodySetter bodySetter,
                           SetCookieHeaderSetter setCookieHeaderSetter) {
        super(startLineSetter, bodySetter);
        this.setCookieHeaderSetter = setCookieHeaderSetter;
    }

    public byte[] generateSet(Code code) {
        response = new Response();
        startLineSetter.setStartLine(response, code.tuple);
        setCookieHeaderSetter.setSetCookie(response);
        bodySetter.setBody(response, "Eat".getBytes());
        return response.getResponse();
    }

    public byte[] generateGet(Code code, String uri) {
        String[] cookie = {"mmmm chocolate"};
        return generate(code, uri, cookie);
    }
}
