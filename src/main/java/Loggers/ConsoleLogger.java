package Loggers;

public class ConsoleLogger implements Logger {
    @Override
    public void systemLog(String report) {
        System.out.println(Formatter.format(report));
    }

    @Override
    public byte[] messageLog(byte[] message) {
        System.out.println(Formatter.format(new String(message)));
        return message;
    }
}
