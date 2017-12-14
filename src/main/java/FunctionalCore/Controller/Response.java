package FunctionalCore.Controller;

import java.util.LinkedList;

public class Response {
    private final String sp = " ";
    private final String nl = "\n";
    private String startLine;
    private LinkedList<String> headers;
    private byte[] body;

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

    public void setHeaders(String[] headers) {
        try {
            setValidHeaderArray(headers);
        } catch(ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Response Object received an odd Array length");
        }
    }

    private void setValidHeaderArray(String[] headers) {
        final String separator = ":" + sp;
        for (int i = 0; i < headers.length; i = i+2) {
            String header = nl + headers[i] + separator + headers[i+1];
            this.headers.add(header);
        }
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
