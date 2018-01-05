package FunctionalCore.Controller.SubControllers;

import FunctionalCore.Controller.ResponseGeneration.RedirectionGenerator;
import FunctionalCore.Request;

import java.util.LinkedHashMap;

public class RedirectionController implements SubController {
    private final String redirectKey = "redirect-uri";
    private RedirectionGenerator redirectionGenerator;

    public RedirectionController(RedirectionGenerator redirectionGenerator) {
        this.redirectionGenerator = redirectionGenerator;
    }

    public boolean relevant(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) {
        return routes.get(request.getUri()).containsKey(redirectKey);
    }

    public byte[] generate(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) {
        String redirectUri = routes.get(request.getUri()).get(redirectKey);
        return redirectionGenerator.generate(RedirectionGenerator.Code.FOUND, redirectUri);
    }
}
