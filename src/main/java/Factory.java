import functionalCore.*;
import functionalCore.Core;
import importers.FileImporter;
import importers.YamlImporter;
import serverShell.*;
import serverShell.Server;

import java.io.IOException;
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
        return buildSingleThreadedReactiveServer();
        //return buildAsynchronousReactiveServer();
    }

    private Server buildAsynchronousReactiveServer() {
        ReactiveFlowable flowable = buildFlowable();
        ReactiveSubscriber subscriber = buildSubscriber();
        return new AsynchronousReactiveServer(flowable, subscriber);
    }

    private ReactiveFlowable buildFlowable() {
        AsynchronousListener listener = buildListener();
        AsynchronousHandler handler = new AsynchronousHandler(listener);
        return new ReactiveFlowable(listener, handler);
    }

    private AsynchronousListener buildListener() {
        try {
            return new AsynchronousListener(port);
        } catch (IOException e) {
            throw new IllegalStateException("IOException caught while trying to initialize listener");
        }
    }

    private ReactiveSubscriber buildSubscriber() {
        AsynchronousResponder responder = buildResponder();
        return new ReactiveSubscriber(responder);
    }

    private AsynchronousResponder buildResponder() {
        SocketReader reader = new SocketReader();
        Core core = buildCore();
        SocketWriter writer = new SocketWriter();
        return new AsynchronousResponder(reader, core, writer);
    }

    private Server buildSingleThreadedReactiveServer() {
        System.out.format("building server with port %d and directory %s\n", port, directory);
        Core functionalCore = buildCore();
        ResponderSocket responder = new ResponderSocket(functionalCore);
        ListenerSocket listener = new ListenerSocket(port);
        return new ReactiveServer(listener, responder);
    }

    private HTTPCore buildCore() {
        Parser parser = new Parser();
        Controller controller = buildController();
        return new HTTPCore(parser, controller);
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
