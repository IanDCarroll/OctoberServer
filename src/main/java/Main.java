import Factory.ServerFactory;
import TerminalArgs.ArgParser;
import TerminalArgs.HashLoopArgParser;
import ServerShell.Server;

public class Main {
    public static void main(String[] args) {
        ArgParser userSettings = new HashLoopArgParser();
        userSettings.setArgs(args);
        ServerFactory serverFactory = new ServerFactory(userSettings.getPort(), userSettings.getDirectory(), userSettings.getConfigFile());
        Server server = serverFactory.buildServer();
        server.start();
    }
}
