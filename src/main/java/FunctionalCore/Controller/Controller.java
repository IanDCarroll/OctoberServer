package FunctionalCore.Controller;

import Filers.FileClerk;
import FunctionalCore.Controller.ResponseGeneration.RedirectionGenerator;
import FunctionalCore.Controller.ResponseGeneration.ClientErrorGenerator;
import FunctionalCore.Controller.ResponseGeneration.SuccessGenerator;
import FunctionalCore.Request;

import java.util.LinkedHashMap;

public class Controller {
    private final String teaPotRoute = "/coffee";
    private SuccessGenerator successGenerator;
    private RedirectionGenerator redirectionGenerator;
    private ClientErrorGenerator clientErrorGenerator;
    private LinkedHashMap<String, LinkedHashMap<String, String>> routes;
    private FileClerk fileClerk;
    private RangeValidator rangeValidator;
    private AuthValidator authValidator;

    public Controller(LinkedHashMap<String, LinkedHashMap<String, String>> routes,
                      FileClerk fileClerk) {
        this.successGenerator = new SuccessGenerator(fileClerk);
        this.redirectionGenerator = new RedirectionGenerator();
        this.clientErrorGenerator = new ClientErrorGenerator(fileClerk);
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
                ? clientErrorGenerator.generate418()
                : valid(request);
    }

    private byte[] valid(Request request) {
        for (String route : routes.keySet()) {
            if (route.equals(request.getUri())) {
                return validMethod(request);
            }
        }
        return clientErrorGenerator.generate(ClientErrorGenerator.Code.NOT_FOUND);
    }

    private byte[] validMethod(Request request) {
        String permittedMethods = routes.get(request.getUri()).get("allowed-methods");
        return (permittedMethods.contains(request.getMethod()))
                ? restrictedUri(request)
                : clientErrorGenerator.generate405(permittedMethods);
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
                ? clientErrorGenerator.generate401()
                : authorize(request, authRoute, authValue);
    }

    private byte[] authorize(Request request, String authRoute, String authValue) {
        return authValidator.valid(authRoute, authValue)
                ? directedUri(request)
                : clientErrorGenerator.generate(ClientErrorGenerator.Code.FORBIDDEN);
    }

    private byte[] directedUri(Request request) {
        return routes.get(request.getUri()).containsKey("redirect-uri")
                ? redirectRequest(request)
                : handleMethod(request);
    }

    private byte[] redirectRequest(Request request) {
        RedirectionGenerator.Code code = RedirectionGenerator.Code.FOUND;
        String redirectUri = routes.get(request.getUri()).get("redirect-uri");
        return redirectionGenerator.generate(code, redirectUri);
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
        int[] rangeTuple = rangeValidator.getRange(uri, rangeHeader);
        return rangeValidator.valid(uri, rangeTuple)
                ? successGenerator.generate(SuccessGenerator.Code.PARTIAL_CONTENT, uri, rangeTuple)
                : clientErrorGenerator.generate416(uri);
    }

    private byte[] head(Request request) { return successGenerator.generateHead(SuccessGenerator.Code.OK, request.getUri()); }

    private byte[] options(Request request) {
        String permittedMethods = routes.get(request.getUri()).get("allowed-methods");
        return successGenerator.generateOptions(SuccessGenerator.Code.OK, permittedMethods);
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
        return successGenerator.generate(SuccessGenerator.Code.OK, request.getUri(), request.getUriParams());
    }
}
