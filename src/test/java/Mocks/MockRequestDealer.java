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

    public static Request getRequest() {
        Request request = new Request();
        request.setMethod("GET");
        request.setUri("/get-address");
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

    public static Request putRequest() {
        Request request = new Request();
        request.setMethod("PUT");
        request.setUri("/put-address");
        request.setHttpV("HTTP/1.1");
        request.setHeaders(new String[]{ "Content-Type: text/plain", "Content-Length: 34" });
        request.setBody("This represents a well--formed PUT".getBytes());
        return request;
    }

    public static Request postRequest() {
        Request request = new Request();
        request.setMethod("POST");
        request.setUri("/post-address");
        request.setHttpV("HTTP/1.1");
        request.setHeaders(new String[]{ "Content-Type: text/plain", "Content-Length: 34" });
        request.setBody("This represents a well-formed POST".getBytes());
        return request;
    }

    public static Request deleteRequest() {
        Request request = new Request();
        request.setMethod("DELETE");
        request.setUri("/address-to-be-deleted");
        return request;
    }

    public static Request getFullRequest() {
        Request request = new Request();
        request.setMethod("GET");
        request.setUri("/mock-address");
        request.setUriParams(new String[]{ "param1=value1", "param2=value2" });
        request.setHttpV("HTTP/1.1");
        request.setHeaders(new String[]{ "Content-Type: text/plain", "Content-Length: 34" });
        request.setBody("This represents a well-formed body".getBytes());
        return request;
    }
}
