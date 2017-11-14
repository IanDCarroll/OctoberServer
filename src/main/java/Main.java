public class Main {
    public static void main(String[] args) {
        ArgParser userSettings = new ArgParser(args);
        Factory factory = new Factory(userSettings.getPort(), userSettings.getDirectory(), userSettings.getConfigFile());
        ReactiveServer server = factory.buildServer();
        server.start();
    }
}
