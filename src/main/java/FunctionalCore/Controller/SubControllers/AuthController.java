package FunctionalCore.Controller.SubControllers;

import FunctionalCore.Controller.ResponseGeneration.AuthGenerator;
import FunctionalCore.Request;

import java.util.Base64;
import java.util.LinkedHashMap;

public class AuthController implements SubController {
    private final String authPrefix = "Authorization: Basic";
    private final String authKey = "authorization";
    private final String withNothing = "";
    private AuthGenerator authGenerator;

    public AuthController(AuthGenerator authGenerator) {
        this.authGenerator = authGenerator;
    }

    public boolean relevant(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) {
        return required(request, routes) && invalid(request, routes);
    }

    private boolean required(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) {
        return routes.get(request.getUri()).containsKey(authKey);
    }

    private boolean invalid(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) {
        String requestAuth = getAuthValueFrom(request.getHeader(authPrefix));
        String routeAuth = routes.get(request.getUri()).get(authKey);
        return !requestAuth.equals(routeAuth);
    }

    private String getAuthValueFrom(String authHeader) {
        String authValue = authHeader.replace(authPrefix, withNothing).trim();
        return new String(Base64.getDecoder().decode(authValue));
    }

    public byte[] generate(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) {
        return thereIsAnAuthHeader(request)
                ? authGenerator.generate403()
                : authGenerator.generate401();
    }

    private boolean thereIsAnAuthHeader(Request request) {
        return !request.getHeader(authPrefix).isEmpty();
    }
}
