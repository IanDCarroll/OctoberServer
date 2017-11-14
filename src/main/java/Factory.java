import java.util.LinkedHashMap;

public class Factory {
    private int port;
    private String directory;
    private String configFile;

    public Factory(int port, String directory, String configFile) {
        this.port = port;
        this.directory = directory;
        this.configFile = configFile;
    }

    public ReactiveServer buildServer() {

        System.out.format("building server with port %d and directory %s\n", port, directory);
            Parser parser = new Parser();
                ResponseGenerator responseGenerator = new ResponseGenerator();
                ConfigImporter configImporter = new ConfigImporter(configFile);
                LinkedHashMap routes = configImporter.importConfig();
            Controller controller = new Controller(responseGenerator, routes);
        ResponderSocket responder = new ResponderSocket(parser, controller);
        ListenerSocket listener = new ListenerSocket(port);
        return new ReactiveServer(listener, responder);
    }
}
