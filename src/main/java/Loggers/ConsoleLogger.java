package Loggers;

public class ConsoleLogger implements Logger {
    @Override
    public void log(String report) {
        System.out.println(Formatter.stampTime(report));
    }
}
