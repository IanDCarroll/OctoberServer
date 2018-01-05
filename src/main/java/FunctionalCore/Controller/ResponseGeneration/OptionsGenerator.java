package FunctionalCore.Controller.ResponseGeneration;

import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.AllowHeaderSetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.StartLineSetter;

public class OptionsGenerator {
    private Response response;
    private StartLineSetter startLineSetter;
    private AllowHeaderSetter allowHeaderSetter;
    public enum Code {
        METHOD_NOT_ALLOWED  (new String[]{"405", "Method Not Allowed"}),
        OK(new String[]{"200", "OK"});
        public String[] tuple;

        Code(String[] tuple) {
            this.tuple = tuple;
        }
    }

    public OptionsGenerator(StartLineSetter startLineSetter, AllowHeaderSetter allowHeaderSetter) {
        this.startLineSetter = startLineSetter;
        this.allowHeaderSetter = allowHeaderSetter;
    }

    public byte[] generate(Code code, String permittedMethods) {
        response = new Response();
        startLineSetter.setStartLine(response, code.tuple);
        allowHeaderSetter.setAllow(response, permittedMethods);
        return response.getHead();
    }
}
