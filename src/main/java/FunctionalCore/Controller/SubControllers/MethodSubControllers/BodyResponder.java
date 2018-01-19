package FunctionalCore.Controller.SubControllers.MethodSubControllers;

import FunctionalCore.Controller.ResponseGeneration.SuccessGenerator;
import FunctionalCore.Request;

public class BodyResponder {

    private SuccessGenerator successGenerator;

    public BodyResponder(SuccessGenerator successGenerator) {
        this.successGenerator = successGenerator;
    }

    public byte[] respondWithBody(Request request) {
        return successGenerator.generate(SuccessGenerator.Code.OK, request.getUri(), request.getUriParams());
    }
}
