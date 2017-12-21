import Filers.FileClerk;
import Filers.FileUpdater;
import FunctionalCore.*;
import FunctionalCore.Controller.Controller;
import FunctionalCore.Controller.ResponseGeneration.ResponseGenerator;
import FunctionalCore.Core;
import FunctionalCore.Parser.Parser;
import Importers.FileImporter;
import Importers.YamlImporter;
import Loggers.ConsoleLogger;
import Loggers.Logger;
import ServerShell.*;
import ServerShell.Server;

import java.io.IOException;
import java.util.LinkedHashMap;

public class Factory {
    private int port;
    private String directory;
    private String configFile;
    private Logger logger;

    public Factory(int port, String directory, String configFile) {
        this.port = port;
        this.directory = directory;
        this.configFile = configFile;
        this.logger = buildLogger();
    }

    private Logger buildLogger() {
        FileUpdater fileUpdater = new FileUpdater(directory);
        return new ConsoleLogger(fileUpdater);
    }

    public Server buildServer() {
        return buildAsynchronousReactiveServer();
    }

    private Server buildAsynchronousReactiveServer() {
        ReactiveFlowable flowable = buildFlowable();
        ReactiveSubscriber subscriber = buildSubscriber();
        return new ReactiveServer(flowable, subscriber);
    }

    private ReactiveFlowable buildFlowable() {
        AsynchronousListener listener = buildListener();
        AsynchronousHandler handler = new AsynchronousHandler(listener, logger);
        return new ReactiveFlowable(listener, handler);
    }

    private AsynchronousListener buildListener() {
        try {
            return new AsynchronousListener(port, logger);
        } catch (IOException e) {
            throw new IllegalStateException("IOException caught while trying to initialize listener");
        }
    }

    private ReactiveSubscriber buildSubscriber() {
        AsynchronousResponder responder = buildResponder();
        return new ReactiveSubscriber(responder, logger);
    }

    private AsynchronousResponder buildResponder() {
        SocketReader reader = new SocketReader();
        Core core = buildCore();
        SocketWriter writer = new SocketWriter();
        return new AsynchronousResponder(reader, core, writer, logger);
    }

    private HTTPCore buildCore() {
        Parser parser = new Parser();
        Controller controller = buildController();
        return new HTTPCore(parser, controller);
    }

    private Controller buildController() {
        FileClerk fileClerk = new FileClerk(directory);
        ResponseGenerator responseGenerator = new ResponseGenerator(fileClerk);
        LinkedHashMap routes = importConfigFile();
        return new Controller(responseGenerator, routes, fileClerk);
    }

    private LinkedHashMap importConfigFile() {
        FileImporter configImporter = new YamlImporter();
        return configImporter.importAsHash(configFile);
    }
}
