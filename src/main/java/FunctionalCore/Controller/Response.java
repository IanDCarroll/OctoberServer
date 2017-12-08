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
        String headers = buildHeaderString();
        final String crlf = "\r\n\r\n";
        String head = startLine + headers + crlf;
        return uniteRequest(head, body);
    }

    private String buildHeaderString() {
        StringBuilder headerString = new StringBuilder();
        String[] headersArray = headers.toArray(new String[headers.size()]);
        for (String header : headersArray) {
            headerString.append(header);
        }
        return headerString.toString();
    }

    private byte[] uniteRequest(String headString, byte[] body) {
        byte[] head = headString.getBytes();
        byte[] union = new byte[head.length + body.length];
        int fromStart = 0;
        System.arraycopy(head, fromStart, union, fromStart, head.length);
        System.arraycopy(body, fromStart, union, head.length, body.length);
        return union;
    }

    public void setStartLine(String code, String message) {
        final String httpV = "HTTP/1.1";
        startLine = httpV + sp + code + sp + message + nl;
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
            String header = headers[i] + separator + headers[i+1] + nl;
            this.headers.add(header);
        }
    }

    public void setBody(byte[] body) { this.body = body; }

    public void setBody(String[] params) {

    }

    public void setBody(byte[] body, String[] params) {}
}