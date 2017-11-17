import functionalCore.*;
import importers.FileImporter;
import importers.YamlImporter;
import serverShell.ListenerSocket;
import serverShell.ReactiveServer;
import serverShell.ResponderSocket;
import serverShell.Server;

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

    public Server buildServer() {

        System.out.format("building server with port %d and directory %s\n", port, directory);
        CoreCoordinator functionalCore = buildCore();
        ResponderSocket responder = new ResponderSocket(functionalCore);
        ListenerSocket listener = new ListenerSocket(port);
        return new ReactiveServer(listener, responder);
    }

    private HTTPCoordinator buildCore() {
        Parser parser = new Parser();
        Controller controller = buildController();
        return new HTTPCoordinator(parser, controller);
    }

    private Controller buildController() {
        ResponseGenerator responseGenerator = new ResponseGenerator();
        LinkedHashMap routes = importConfigFile();
        return new Controller(responseGenerator, routes);
    }

    private LinkedHashMap importConfigFile() {
        FileImporter configImporter = new YamlImporter();
        return configImporter.importAsHash(configFile);
    }
}
