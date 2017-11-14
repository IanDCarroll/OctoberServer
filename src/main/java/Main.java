public class Main {
    public static void main(String[] args) {
        ArgParser userSettings = new ArgParser(args);
        Factory factory = new Factory(userSettings.getPort(), userSettings.getDirectory());
        ReactiveServer server = factory.buildServer();
        server.start();
    }
}
