import Filers.DirectoryFileClerk;
import Filers.FileClerk;
import Filers.FileUpdater;
import FunctionalCore.*;
import FunctionalCore.Controller.*;
import FunctionalCore.Controller.ResponseGeneration.*;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.BodySetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.*;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.StartLineSetter;
import FunctionalCore.Core;
import FunctionalCore.Parser.Parser;
import Importers.FileImporter;
import Importers.YamlImporter;
import Loggers.FileLogger;
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
    private FileClerk fileClerk;


    public Factory(int port, String directory, String configFile) {
        this.port = port;
        this.directory = directory;
        this.configFile = configFile;
        this.logger = buildLogger();
        this.fileClerk = new DirectoryFileClerk(directory);
    }

    private Logger buildLogger() {
        FileUpdater fileUpdater = new FileUpdater(directory);
        return new FileLogger(fileUpdater);
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
        LinkedHashMap<String, LinkedHashMap<String, String>> routes = importConfigFile();
        TeaPotController tea = buildTeaPotController();
        UriController uri = buildUriController();
        AuthController auth = buildAuthController();
        OptionsController allowed = buildOptionsController();
        RedirectionController redirect = buildRedirectionController();
        CookieController cookie = buildCookieController();
        RangeController range = buildRangeController();
        MethodController method = buildMethodController();
        ClientErrorGenerator clientErrorGenerator = buildClientErrorGenerator();
        return new Controller(routes, logger, tea, uri, auth, allowed, redirect, cookie, range, method, clientErrorGenerator);
    }

    private LinkedHashMap<String, LinkedHashMap<String, String>> importConfigFile() {
        FileImporter configImporter = new YamlImporter();
        return configImporter.importAsHash(configFile);
    }

    private TeaPotController buildTeaPotController() {
        TeaPotGenerator teaPotGenerator = buildTeaPotGenerator();
        return new TeaPotController(teaPotGenerator);
    }

    private TeaPotGenerator buildTeaPotGenerator() {
        StartLineSetter startLineSetter = buildStartLineSetter();
        BodySetter bodySetter = buildBodySetter();
        return new TeaPotGenerator(startLineSetter, bodySetter);
    }

    private StartLineSetter buildStartLineSetter() {
        return new StartLineSetter();
    }

    private BodySetter buildBodySetter() {
        return new BodySetter(fileClerk);
    }

    private UriController buildUriController() {
        ClientErrorGenerator clientErrorGenerator = buildClientErrorGenerator();
        return new UriController(clientErrorGenerator);
    }

    private ClientErrorGenerator buildClientErrorGenerator() {
        StartLineSetter startLineSetter = buildStartLineSetter();
        return new ClientErrorGenerator(startLineSetter);
    }

    private AuthController buildAuthController() {
        AuthGenerator authGenerator = buildAuthGenerator();
        return new AuthController(authGenerator);
    }

    private AuthGenerator buildAuthGenerator() {
        StartLineSetter startLineSetter = buildStartLineSetter();
        AuthenticateHeaderSetter authenticateHeaderSetter = new AuthenticateHeaderSetter();
        return new AuthGenerator(startLineSetter, authenticateHeaderSetter);
    }

    private OptionsController buildOptionsController() {
        OptionsGenerator optionsGenerator = buildOptionsGenerator();
        return new OptionsController(optionsGenerator);
    }

    private OptionsGenerator buildOptionsGenerator() {
        StartLineSetter startLineSetter = buildStartLineSetter();
        AllowHeaderSetter allowHeaderSetter = new AllowHeaderSetter();
        return new OptionsGenerator(startLineSetter, allowHeaderSetter);
    }

    private RedirectionController buildRedirectionController() {
        RedirectionGenerator redirectionGenerator = buildRedirectionGenerator();
        return new RedirectionController(redirectionGenerator);
    }

    private RedirectionGenerator buildRedirectionGenerator() {
        StartLineSetter startLineSetter = buildStartLineSetter();
        LocationHeaderSetter locationHeaderSetter = new LocationHeaderSetter();
        return new RedirectionGenerator(startLineSetter, locationHeaderSetter);
    }

    private CookieController buildCookieController() {
        SuccessGenerator successGenerator = buildSuccessGenerator();
        return new CookieController(successGenerator);
    }

    private SuccessGenerator buildSuccessGenerator() {
        StartLineSetter startLineSetter = buildStartLineSetter();
        BodySetter bodySetter = buildBodySetter();
        SetCookieHeaderSetter setCookieHeaderSetter = new SetCookieHeaderSetter();
        ETagHeaderSetter eTagHeaderSetter = new ETagHeaderSetter();
        return new SuccessGenerator(startLineSetter, bodySetter, setCookieHeaderSetter, eTagHeaderSetter);
    }

    private RangeController buildRangeController() {
        RangeGenerator rangeGenerator = buildRangeGenerator();
        return new RangeController(fileClerk, rangeGenerator);
    }

    private RangeGenerator buildRangeGenerator() {
        StartLineSetter startLineSetter = buildStartLineSetter();
        BodySetter bodySetter = buildBodySetter();
        RangeHeaderSetter rangeHeaderSetter = new RangeHeaderSetter(fileClerk);
        return new RangeGenerator(startLineSetter, bodySetter, rangeHeaderSetter);
    }

    private MethodController buildMethodController() {
        SuccessGenerator successGenerator = buildSuccessGenerator();
        return new MethodController(fileClerk, buildSuccessGenerator());
    }
}
