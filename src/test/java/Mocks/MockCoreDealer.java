package Mocks;

import FunctionalCore.Core;

public class MockCoreDealer {
    public static String correctRequest = "A Good Request";
    public static String desiredResponse = "An Appropriate Response";

    public static Core core = new Core() {
        @Override
        public byte[] craftResponseTo(byte[] request) {
            if (itsAGood(request)) { return desiredResponse.getBytes(); }
            return "The core's craftResponse method was called, but the request was no good.".getBytes();
        }
    };

    private static boolean itsAGood(byte[] request) {
        return correctRequest.equals(new String(request));
    }
}
