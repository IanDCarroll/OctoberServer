package Mocks;

import FunctionalCore.Request;

public class MockRequestDealer {
    public static Request getRootRequest() {
        Request request = new Request();
        request.setMethod("GET");
        request.setUri("/");
        request.setHttpV("HTTP/1.1");
        request.setBody("".getBytes());
        return request;
    }

    public static Request getRequest(String uri) {
        Request request = new Request();
        request.setMethod("GET");
        request.setUri(uri);
        request.setHttpV("HTTP/1.1");
        request.setBody("".getBytes());
        return request;
    }

    public static Request headHeaderRequest() {
        Request request = new Request();
        request.setMethod("HEAD");
        request.setUri("/");
        request.setUriParams(new String[]{ "param1=value1", "param2=value2" });
        request.setHttpV("HTTP/1.1");
        request.setHeaders(new String[]{ "Content-Type: text/plain", "Content-Length: 34" });
        request.setBody("This represents a well-formed body".getBytes());
        return request;
    }

    public static Request optionsRequest() {
        Request request = new Request();
        request.setMethod("OPTIONS");
        request.setUri("/");
        return request;
    }

    public static Request putRequest(String uri) {
        Request request = new Request();
        request.setMethod("PUT");
        request.setUri(uri);
        request.setHttpV("HTTP/1.1");
        request.setHeaders(new String[]{ "Content-Type: text/plain", "Content-Length: 34" });
        request.setBody("This represents a well--formed PUT".getBytes());
        return request;
    }

    public static Request postRequest(String uri) {
        Request request = new Request();
        request.setMethod("POST");
        request.setUri(uri);
        request.setHttpV("HTTP/1.1");
        request.setHeaders(new String[]{ "Content-Type: text/plain", "Content-Length: 34" });
        request.setBody("This represents a well-formed POST".getBytes());
        return request;
    }

    public static Request deleteRequest(String uri) {
        Request request = new Request();
        request.setMethod("DELETE");
        request.setUri(uri);
        return request;
    }

    public static Request patchRequest(String uri, String ifMatch) {
        Request request = new Request();
        request.setMethod("PATCH");
        request.setUri(uri);
        request.setHeaders(new String[]{ "If-Match: " + ifMatch });
        return request;
    }

    public static Request partialRequest(String uri) {
        Request request = new Request();
        request.setMethod("GET");
        request.setUri(uri);
        request.setHttpV("HTTP/1.1");
        request.setHeaders(new String[]{ "Range: bytes=2-8" });
        return request;
    }

    public static Request badPartialRequest(String uri) {
        Request request = new Request();
        request.setMethod("GET");
        request.setUri(uri);
        request.setHttpV("HTTP/1.1");
        request.setHeaders(new String[]{ "Range: bytes=3-2" });
        return request;
    }

    public static Request authRequest(String uri) {
        Request request = new Request();
        request.setMethod("GET");
        request.setUri(uri);
        request.setHttpV("HTTP/1.1");
        request.setHeaders(new String[]{ "Authorization: Basic YWRtaW46aHVudGVyMg==" });
        return request;
    }
}
