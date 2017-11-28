package TerminalArgs;

public interface ArgParser {
    void setArgs(String[] args);

    int getPort();

    String getDirectory();

    String getConfigFile();
}
