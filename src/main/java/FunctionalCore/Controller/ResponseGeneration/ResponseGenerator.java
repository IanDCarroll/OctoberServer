package FunctionalCore.Controller.ResponseGeneration;

import Filers.FileClerk;

import java.util.Arrays;

public class ResponseGenerator {
    FileClerk fileClerk;

    public ResponseGenerator(FileClerk fileClerk) {
        this.fileClerk = fileClerk;
    }

    private final String[] ok200 = { "200", "OK" };
    Response response;

    public byte[] generate200(String uri, String[] params) {
        response = new Response();
        setResponseStartLine(ok200);
        setParamsWithBody(uri, params);
        return response.getResponse();
    }

    public byte[] generate200Head(String uri) {
        response = new Response();
        setResponseStartLine(ok200);
        setBody(uri);
        return response.getHead();
    }

    public byte[] generate200Options(String permittedMethods) {
        response = new Response();
        setResponseStartLine(ok200);
        response.setHeader(Response.Header.ALLOW, permittedMethods);
        return response.getHead();
    }

    public byte[] generate206(String uri, int start, int end) {
        response = new Response();
        setResponseStartLine(new String[]{"206", "Partial Content"});
        setBody(uri, start, end);
        response.setHeader(Response.Header.CONTENT_RANGE, rangeValue(uri, start, end));
        return response.getResponse();
    }

    public byte[] generate302(String redirectToThisUri) {
        response = new Response();
        setResponseStartLine(new String[]{"302", "Found"});
        response.setHeader(Response.Header.LOCATION, redirectToThisUri);
        return response.getHead();
    }

    public byte[] generate401() {
        response = new Response();
        setResponseStartLine(new String[]{"401", "Unauthorized"});
        response.setHeader(Response.Header.WWW_AUTHENTICATE, "Basic realm=\"Access to URI\"");
        return response.getHead();
    }

    public byte[] generate403() {
        response = new Response();
        setResponseStartLine(new String[]{"403", "Forbidden"});
        return response.getHead();
    }

    public byte[] generate404() {
        response = new Response();
        setResponseStartLine(new String[]{ "404", "Not Found" });
        return response.getHead();
    }

    public byte[] generate405(String permittedMethods) {
        response = new Response();
        setResponseStartLine(new String[]{ "405", "Method Not Allowed" });
        response.setHeader(Response.Header.ALLOW, permittedMethods);
        return response.getHead();
    }

    public byte[] generate416(String uri) {
        response = new Response();
        setResponseStartLine(new String[]{ "416", "Range Not Satisfiable" });
        response.setHeader(Response.Header.CONTENT_RANGE, rangeValue(uri));
        return response.getHead();
    }

    public byte[] generate418() {
        response = new Response();
        String message = "I'm a teapot";
        setResponseStartLine(new String[]{ "418", message });
        setBody(message.getBytes());
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
