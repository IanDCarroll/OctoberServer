package Loggers;

public interface Logger {
    void systemLog(String report);

    byte[] messageLog(byte[] message);
}
