package Loggers;

import Filers.FileUpdater;

import java.io.PrintStream;

public class ConsoleLogger implements Logger {
    private FileUpdater fileUpdater;
    private PrintStream printStream;

    public ConsoleLogger(FileUpdater fileUpdater) { this(fileUpdater, System.out); }

    public ConsoleLogger(FileUpdater fileUpdater, PrintStream printStream)  {
        this.fileUpdater = fileUpdater;
        this.printStream = printStream;
    }

    @Override
    public void log(String event) {
        String logEntry = Formatter.stampTime(event);
        record(logEntry);
        report(logEntry);
    }

    private void record(String logEntry) {
        fileUpdater.append("/logs", logEntry.getBytes());
    }

    private void report(String logEntry) {
        printStream.println(logEntry);
    }
}
