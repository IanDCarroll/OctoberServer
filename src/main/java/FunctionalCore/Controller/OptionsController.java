package FunctionalCore.Controller;

import FunctionalCore.Controller.ResponseGeneration.OptionsGenerator;
import FunctionalCore.Request;

import java.util.LinkedHashMap;

public class OptionsController implements SubController {
    private final String allowKey = "allowed-methods";
    private final String options = "OPTIONS";
    private OptionsGenerator optionsGenerator;

    public OptionsController(OptionsGenerator optionsGenerator) {
        this.optionsGenerator = optionsGenerator;
    }

    public boolean relevant(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) {
        return methodNotAllowed(request, routes) || allowedOptionsRequest(request, routes);
    }

    private boolean methodNotAllowed(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) {
        return !methodAllowed(request, routes, request.getMethod());
    }

    private boolean allowedOptionsRequest(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) {
        return methodAllowed(request, routes, options) && request.getMethod().equals(options);
    }

    private boolean methodAllowed(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes, String method) {
        return routes.get(request.getUri()).get(allowKey).contains(method);
    }

    public byte[] generate(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) {
        String permittedMethods = routes.get(request.getUri()).get(allowKey);
        return permittedMethods.contains(request.getMethod())
                ? optionsGenerator.generate(OptionsGenerator.Code.OK, permittedMethods)
                : optionsGenerator.generate(OptionsGenerator.Code.METHOD_NOT_ALLOWED, permittedMethods);
    }
}
