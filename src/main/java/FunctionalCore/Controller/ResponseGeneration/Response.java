package FunctionalCore.Controller.ResponseGeneration;

import java.util.LinkedList;

public class Response {
    private final String sp = " ";
    private final String nl = "\n";
    private String startLine;
    private LinkedList<String> headers;
    private byte[] body;
    public enum Header {
        CONTENT_LENGTH("Content-Length"),
        CONTENT_TYPE("Content-Type"),
        CONTENT_RANGE("Content-Range"),
        WWW_AUTHENTICATE("WWW-Authenticate"),
        ALLOW("Allow"),
        LOCATION("Location"),
        SET_COOKIE("Set-Cookie");
        public String key;
        Header(String key) {
            this.key = key;
        }
    }

    public Response() {
        this.headers = new LinkedList<>();
        this.body = new byte[0];
    }

    public byte[] getResponse() {
        byte[] head = getHead();
        return uniteBytes(head, body);
    }

    public byte[] getHead() {
        String headers = buildStringFrom(getHeaders());
        final String crlf = "\r\n\r\n";
        return (startLine + headers + crlf).getBytes();
    }

    private String[] getHeaders() {
        return headers.toArray(new String[headers.size()]);
    }

    private String buildStringFrom(String[] array) {
        StringBuilder string = new StringBuilder();
        for (String item : array) {
            string.append(item);
        }
        return string.toString();
    }

    private byte[] uniteBytes(byte[] head, byte[] body) {
        byte[] union = new byte[head.length + body.length];
        int fromStart = 0;
        System.arraycopy(head, fromStart, union, fromStart, head.length);
        System.arraycopy(body, fromStart, union, head.length, body.length);
        return union;
    }

    public void setStartLine(String code, String message) {
        final String httpV = "HTTP/1.1";
        startLine = httpV + sp + code + sp + message;
    }

    public void setHeader(Header header, String value) {
        final String separator = ":" + sp;
        String formattedHeader = nl + header.key + separator + value;
        this.headers.add(formattedHeader);
    }

    public void setBody(byte[] body) { this.body = uniteBytes(this.body, body); }

    public void setBody(byte[] body, String[] params) {
        setBody(params);
        this.body = uniteBytes(this.body, body);
    }

    public void setBody(String[] params) {
        String[] formattedParams = ParamFormatter.addStyling(params);
        this.body = uniteBytes(this.body, buildStringFrom(formattedParams).getBytes());
    }

    public String bodyLength() { return String.valueOf(body.length); }
}
