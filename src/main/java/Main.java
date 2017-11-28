import TerminalArgs.ArgParser;
import TerminalArgs.HashLoopArgParser;
import ServerShell.Server;

public class Main {
    public static void main(String[] args) {
        ArgParser userSettings = new HashLoopArgParser();
        userSettings.setArgs(args);
        Factory factory = new Factory(userSettings.getPort(), userSettings.getDirectory(), userSettings.getConfigFile());
        Server server = factory.buildServer();
        server.start();
    }
}
