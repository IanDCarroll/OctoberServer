package FunctionalCore.Controller.ResponseGeneration;

import Filers.FileClerk;

public class ClientErrorGenerator {
    FileClerk fileClerk;
    StartLineSetter startLineSetter;
    BodySetter bodySetter;
    RangeHeaderSetter rangeHeaderSetter;
    public enum Code {
        UNAUTHORIZED            (new String[]{"401", "Unauthorized"}),
        FORBIDDEN               (new String[]{"403", "Forbidden"}),
        NOT_FOUND               (new String[]{"404", "Not Found"}),
        METHOD_NOT_ALLOWED      (new String[]{"405", "Method Not Allowed"}),
        RANGE_NOT_SATISFIABLE   (new String[]{"416", "Range Not Satisfiable"}),
        IM_A_TEAPOT             (new String[]{"418", "I'm a teapot"});
        public String[] tuple;
        Code(String[] tuple) {
            this.tuple = tuple;
        }
    }

    public ClientErrorGenerator(FileClerk fileClerk) {
        this.fileClerk = fileClerk;
        bodySetter = new BodySetter(fileClerk);
        startLineSetter = new StartLineSetter();
        rangeHeaderSetter = new RangeHeaderSetter(fileClerk);
    }

    Response response;

    public byte[] generate401() {
        response = new Response();
        startLineSetter.setStartLine(response, Code.UNAUTHORIZED.tuple);
        response.setHeader(Response.Header.WWW_AUTHENTICATE, "Basic realm=\"Access to URI\"");
        return response.getHead();
    }

    public byte[] generate(Code code) {
        response = new Response();
        startLineSetter.setStartLine(response, code.tuple);
        return response.getHead();
    }

    public byte[] generate405(String permittedMethods) {
        response = new Response();
        startLineSetter.setStartLine(response, Code.METHOD_NOT_ALLOWED.tuple);
        response.setHeader(Response.Header.ALLOW, permittedMethods);
        return response.getHead();
    }

    public byte[] generate416(String uri) {
        response = new Response();
        startLineSetter.setStartLine(response, Code.RANGE_NOT_SATISFIABLE.tuple);
        rangeHeaderSetter.setRange(response, uri);
        return response.getHead();
    }

    public byte[] generate418() {
        response = new Response();
        startLineSetter.setStartLine(response, Code.IM_A_TEAPOT.tuple);
        bodySetter.setBody(response, Code.IM_A_TEAPOT.tuple[1].getBytes());
        return response.getResponse();
    }
}
