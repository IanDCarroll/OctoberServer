package Mocks;

import Loggers.Logger;

public class MockLoggerDealer {
    public static Logger logger = new Logger() {
        public void log(String report) {}
    };
}
