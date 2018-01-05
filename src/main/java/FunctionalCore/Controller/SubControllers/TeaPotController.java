package FunctionalCore.Controller.SubControllers;

import FunctionalCore.Controller.ResponseGeneration.TeaPotGenerator;
import FunctionalCore.Request;

import java.util.LinkedHashMap;

public class TeaPotController implements SubController {
    private final String teaPotRoute = "/coffee";
    private TeaPotGenerator generator;

    public TeaPotController(TeaPotGenerator generator) {
        this.generator = generator;
    }

    public boolean relevant(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) {
        return request.getUri().equals(teaPotRoute);
    }

    public byte[] generate(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) {
        return generator.generate(TeaPotGenerator.Code.IM_A_TEAPOT);
    }
}
