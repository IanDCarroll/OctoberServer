package FunctionalCore.Controller.SubControllers;

import FunctionalCore.Controller.ResponseGeneration.CookieGenerator;
import FunctionalCore.Controller.ResponseGeneration.SuccessGenerator;
import FunctionalCore.Request;

import java.util.LinkedHashMap;

public class CookieController implements SubController {
    private final String setCookieKey = "set-a-cookie";
    private final String watchCookieKey = "watch-for-cookie";
    private final String cookieHeaderPrefix = "Cookie: ";
    private CookieGenerator cookieGenerator;

    public CookieController(CookieGenerator cookieGenerator) {
        this.cookieGenerator = cookieGenerator;
    }

    public boolean relevant(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) {
        return thereAreCookiesToBeSet(request, routes) || thereAreCookiesToWatchFor(request, routes);
    }

    private boolean thereAreCookiesToBeSet(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) {
        return thereIsA(setCookieKey, request, routes);
    }

    private boolean thereIsA(String key, Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) {
        return routes.get(request.getUri()).containsKey(key);
    }

    private boolean thereAreCookiesToWatchFor(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) {
        return keepAnEyeOutForCookies(request, routes) && thereIsACookieHeader(request);
    }

    private boolean keepAnEyeOutForCookies(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) {
        return thereIsA(watchCookieKey, request, routes);
    }

    private boolean thereIsACookieHeader(Request request) {
        return !request.getHeader(cookieHeaderPrefix).isEmpty();
    }

    public byte[] generate(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) {
        String uri = request.getUri();
        if (thereAreCookiesToBeSet(request, routes)) { return cookieGenerator.generateSet(SuccessGenerator.Code.OK); }
        return cookieGenerator.generateGet(SuccessGenerator.Code.OK, uri);
    }
}

