package FunctionalCore.Controller;

import FunctionalCore.Controller.ResponseGeneration.*;
import FunctionalCore.Controller.SubControllers.*;
import FunctionalCore.Request;
import Loggers.Logger;

import java.util.LinkedHashMap;

public class Controller {
    private LinkedHashMap<String, LinkedHashMap<String, String>> routes;
    private Logger logger;
    private SubController[] orderOfConcerns;
    private ClientErrorGenerator clientErrorGenerator;

    public Controller(LinkedHashMap<String, LinkedHashMap<String, String>> routes,
                      Logger logger,
                      TeaPotController tea,
                      UriController uri,
                      AuthController auth,
                      OptionsController allowed,
                      RedirectionController redirect,
                      CookieController cookie,
                      RangeController range,
                      MethodController method,
                      ClientErrorGenerator clientErrorGenerator) {
        this.routes = routes;
        this.logger = logger;
        this.orderOfConcerns = new SubController[]{tea, uri, allowed, auth, redirect, cookie, range, method};
        this.clientErrorGenerator = clientErrorGenerator;
    }

    public byte[] getAppropriateResponse(Request request) {
        if(flaggedForLogging(request)) { logger.log(request.record());}
        return useSubsArrayToGetAppropriateResponse(request);
    }

    private boolean flaggedForLogging(Request request) {
        return routes.containsKey(request.getUri()) && routes.get(request.getUri()).containsKey("log-activity");
    }

    public byte[] useSubsArrayToGetAppropriateResponse(Request request) {
        for (SubController subController : orderOfConcerns) {
            if (subController.relevant(request, routes)) {
                return subController.generate(request, routes);
            }
        }
        return clientErrorGenerator.generate(ClientErrorGenerator.Code.BAD_REQUEST);
    }
}