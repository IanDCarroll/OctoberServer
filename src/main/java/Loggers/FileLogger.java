package Loggers;

import Filers.FileUpdater;

import java.io.PrintStream;

public class FileLogger implements Logger {
    private FileUpdater fileUpdater;

    public FileLogger(FileUpdater fileUpdater)  {
        this.fileUpdater = fileUpdater;
    }

    @Override
    public void log(String event) {
        String logEntry = Formatter.stampTime(event);
        record(logEntry);
    }

    private void record(String logEntry) {
        fileUpdater.append("/logs", logEntry.getBytes());
    }
}
