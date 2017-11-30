package Mocks;

import Loggers.Logger;

public class MockLoggerDealer {
    public static Logger logger = new Logger() {
        public void systemLog(String report) {}
        public byte[] messageLog(byte[] message) { return message; }
    };
}
