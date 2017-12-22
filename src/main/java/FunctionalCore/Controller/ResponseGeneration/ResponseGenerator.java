package FunctionalCore.Controller.ResponseGeneration;

import Filers.FileClerk;

import java.util.Arrays;

public class ResponseGenerator {
    FileClerk fileClerk;
    public enum Code {
        OK                      (new String[]{"200", "OK"}),
        PARTIAL_CONTENT         (new String[]{"206", "Partial Content"}),
        FOUND                   (new String[]{"302", "Found"}),
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

    public ResponseGenerator(FileClerk fileClerk) {
        this.fileClerk = fileClerk;
    }

    Response response;

    public byte[] generate200(String uri, String[] params) {
        response = new Response();
        setResponseStartLine(Code.OK.tuple);
        setParamsWithBody(uri, params);
        return response.getResponse();
    }

    public byte[] generate200Head(String uri) {
        response = new Response();
        setResponseStartLine(Code.OK.tuple);
        setBody(uri);
        return response.getHead();
    }

    public byte[] generate200Options(String permittedMethods) {
        response = new Response();
        setResponseStartLine(Code.OK.tuple);
        response.setHeader(Response.Header.ALLOW, permittedMethods);
        return response.getHead();
    }

    public byte[] generate206(String uri, int start, int end) {
        response = new Response();
        setResponseStartLine(Code.PARTIAL_CONTENT.tuple);
        setBody(uri, start, end);
        response.setHeader(Response.Header.CONTENT_RANGE, rangeValue(uri, start, end));
        return response.getResponse();
    }

    public byte[] generate302(String redirectToThisUri) {
        response = new Response();
        setResponseStartLine(Code.FOUND.tuple);
        response.setHeader(Response.Header.LOCATION, redirectToThisUri);
        return response.getHead();
    }

    public byte[] generate401() {
        response = new Response();
        setResponseStartLine(Code.UNAUTHORIZED.tuple);
        response.setHeader(Response.Header.WWW_AUTHENTICATE, "Basic realm=\"Access to URI\"");
        return response.getHead();
    }

    public byte[] generate403() {
        response = new Response();
        setResponseStartLine(Code.FORBIDDEN.tuple);
        return response.getHead();
    }

    public byte[] generate404() {
        response = new Response();
        setResponseStartLine(Code.NOT_FOUND.tuple);
        return response.getHead();
    }

    public byte[] generate405(String permittedMethods) {
        response = new Response();
        setResponseStartLine(Code.METHOD_NOT_ALLOWED.tuple);
        response.setHeader(Response.Header.ALLOW, permittedMethods);
        return response.getHead();
    }

    public byte[] generate416(String uri) {
        response = new Response();
        setResponseStartLine(Code.RANGE_NOT_SATISFIABLE.tuple);
        response.setHeader(Response.Header.CONTENT_RANGE, rangeValue(uri));
        return response.getHead();
    }

    public byte[] generate418() {
        response = new Response();
        setResponseStartLine(Code.IM_A_TEAPOT.tuple);
        setBody(Code.IM_A_TEAPOT.tuple[1].getBytes());
        return response.getResponse();
    }

    private void setResponseStartLine(String[] codeTuple) {
        response.setStartLine(codeTuple[0], codeTuple[1]);
    }

    private String rangeValue(String uri, int start, int end) {
        return "bytes " + String.valueOf(start) + "-" + String.valueOf(end) + "/" + fileLengthOf(uri);
    }

    private String rangeValue(String uri) {
        return "bytes */" + fileLengthOf(uri);
    }

    private String fileLengthOf(String uri) {
        return String.valueOf(fileClerk.checkout(uri).length);
    }

    public void setParamsWithBody(String uri, String[] params) {
        response.setBody(params);
        setBody(uri);
    }

    private void setBody(String uri) {
        int start = 0;
        int end = fileClerk.checkout(uri).length;
        setBody(uri, start, end);
    }

    private void setBody(String uri, int start, int end) {
        byte[] body = Arrays.copyOfRange(fileClerk.checkout(uri), start, end);
        setBody(body);
    }

    private void setBody(byte[] body) {
        response.setBody(body);
        setBasicHeaders(response.bodyLength());
    }

    private void setBasicHeaders(String length) {
        response.setHeader(Response.Header.CONTENT_LENGTH, length);
        response.setHeader(Response.Header.CONTENT_TYPE, "text/plain");
    }
}
