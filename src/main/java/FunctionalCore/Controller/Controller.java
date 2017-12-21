package FunctionalCore.Controller;

import Filers.FileClerk;
import FunctionalCore.Controller.ResponseGeneration.ResponseGenerator;
import FunctionalCore.Request;

import java.util.LinkedHashMap;

public class Controller {
    private final String teaPotRoute = "/coffee";
    private ResponseGenerator responseGenerator;
    private LinkedHashMap<String, LinkedHashMap<String, String>> routes;
    private FileClerk fileClerk;
    private RangeValidator rangeValidator;
    private AuthValidator authValidator;

    public Controller(ResponseGenerator responseGenerator,
                      LinkedHashMap<String, LinkedHashMap<String, String>> routes,
                      FileClerk fileClerk) {
        this.responseGenerator = responseGenerator;
        this.routes = routes;
        this.fileClerk = fileClerk;
        this.rangeValidator = new RangeValidator(fileClerk);
        this.authValidator = new AuthValidator();
    }

    public byte[] getAppropriateResponse(Request request) {
        return teaEarlGreyHot(request);
    }

    private byte[] teaEarlGreyHot(Request request) {
        return request.getUri().equals(teaPotRoute)
                ? responseGenerator.generate418()
                : valid(request);
    }

    private byte[] valid(Request request) {
        for (String route : routes.keySet()) {
            if (route.equals(request.getUri())) {
                return validMethod(request);
            }
        }
        return responseGenerator.generate404();
    }

    private byte[] validMethod(Request request) {
        String permittedMethods = routes.get(request.getUri()).get("allowed-methods");
        return (permittedMethods.contains(request.getMethod()))
                ? restrictedUri(request)
                : responseGenerator.generate405(permittedMethods);
    }

    private byte[] restrictedUri(Request request) {
        return routes.get(request.getUri()).containsKey("authorization")
                ? authorize(request, routes.get(request.getUri()).get("authorization"))
                : directedUri(request);
    }

    private byte[] authorize(Request request, String authRoute) {
        String authHeader = authValidator.getAuthHeader(request.getHeaders());
        String authValue = authValidator.getAuth(authHeader);
        return authHeader.isEmpty()
                ? responseGenerator.generate401()
                : authorize(request, authRoute, authValue);
    }

    private byte[] authorize(Request request, String authRoute, String authValue) {
        return authValidator.valid(authRoute, authValue)
                ? directedUri(request)
                : responseGenerator.generate403();
    }

    private byte[] directedUri(Request request) {
        return routes.get(request.getUri()).containsKey("redirect-uri")
                ? responseGenerator.generate302(routes.get(request.getUri()).get("redirect-uri"))
                : handleMethod(request);
    }

    private byte[] handleMethod(Request request) {
        if (request.getMethod().equals("HEAD")) return head(request);
        if (request.getMethod().equals("OPTIONS")) return options(request);
        if (request.getMethod().equals("POST")) return post(request);
        if (request.getMethod().equals("PUT")) return put(request);
        if (request.getMethod().equals("DELETE")) return delete(request);
        return checkRange(request);
    }

    private byte[] checkRange(Request request) {
        String rangeHeader = rangeValidator.getRangeHeader(request.getHeaders());
        return rangeHeader.isEmpty()
                ? get(request)
                : range(request.getUri(), rangeHeader);

    }

    private byte[] range(String uri, String rangeHeader) {
        int[] range = rangeValidator.getRange(uri, rangeHeader);
        return rangeValidator.valid(uri, range)
                ? responseGenerator.generate206(uri, range[0], range[1])
                : responseGenerator.generate416(uri);
    }

    private byte[] head(Request request) { return responseGenerator.generate200Head(request.getUri()); }

    private byte[] options(Request request) {
        String permittedMethods = routes.get(request.getUri()).get("allowed-methods");
        return responseGenerator.generate200Options(permittedMethods);
    }

    private byte[] post(Request request) {
        fileClerk.append(request.getUri(), request.getBody());
        return get(request);
    }

    private byte[] put(Request request) {
        fileClerk.rewrite(request.getUri(), request.getBody());
        return get(request);
    }

    private byte[] delete(Request request) {
        fileClerk.delete(request.getUri());
        return get(request);
    }

    private byte[] get(Request request) {
        return responseGenerator.generate200(request.getUri(), request.getUriParams());
    }
}
