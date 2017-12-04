package Mocks;

import FunctionalCore.Request;

public class MockRequestDealer {
    public static Request getRootRequest = new Request();
    {
        getRootRequest.setMethod("GET");
        getRootRequest.setUri("/");
        getRootRequest.setHttpV("HTTP/1.1");
        getRootRequest.setBody("".getBytes());
    }
}
