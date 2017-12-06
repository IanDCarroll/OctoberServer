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
