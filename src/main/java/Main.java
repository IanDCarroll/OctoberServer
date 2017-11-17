import clArgs.ArgParser;
import clArgs.ConditionalArgParser;
import serverShell.Server;

public class Main {
    public static void main(String[] args) {
        ArgParser userSettings = new ConditionalArgParser();
        userSettings.setArgs(args);
        Factory factory = new Factory(userSettings.getPort(), userSettings.getDirectory(), userSettings.getConfigFile());
        Server server = factory.buildServer();
        server.start();
    }
}
