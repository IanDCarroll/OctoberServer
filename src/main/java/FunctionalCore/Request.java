package FunctionalCore;

import java.util.LinkedList;

public class Request {
    private static final String notSet = "Not Set";
    private String method = notSet;
    private String uri = notSet;
    private LinkedList<String> uriParams = new LinkedList<>();
    private String httpV = notSet;
    private LinkedList<String> headers = new LinkedList<>();
    private byte[] body = notSet.getBytes();


    public void setMethod(String method) { this.method = method; }
    public String getMethod() { return method; }

    public void setUri(String uri) { this.uri = uri; }
    public String getUri() { return uri; }

    public void setUriParams(String[] values) {
        for (String uriParam : values) {
            uriParams.add(uriParam);
        }
    }
    public String[] getUriParams() { return uriParams.toArray(new String[uriParams.size()]); }

    public void setHttpV(String httpV) { this.httpV = httpV; }
    public String getHttpV() { return httpV; }

    public void setHeaders(String[] values) {
        for (String header : values) {
            headers.add(header);
        }
    }
    public String[] getHeaders() { return headers.toArray(new String[headers.size()]); }

    public void setBody(byte[] body) { this.body = body; }
    public byte[] getBody() { return body; }

    public String record() { return method + " " + uri + " " + httpV; }
}
