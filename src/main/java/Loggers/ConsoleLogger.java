package Loggers;

import java.io.PrintStream;

public class ConsoleLogger implements Logger {
    private PrintStream printStream = System.out;

    public ConsoleLogger() {
        new ConsoleLogger(System.out);
    }

    public ConsoleLogger(PrintStream printStream)  {
        this.printStream = printStream;
    }

    @Override
    public void log(String report) {
        printStream.println(Formatter.stampTime(report));
    }
}
