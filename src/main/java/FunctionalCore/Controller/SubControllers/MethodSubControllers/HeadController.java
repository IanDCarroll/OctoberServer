package FunctionalCore.Controller.SubControllers.MethodSubControllers;

import FunctionalCore.Controller.ResponseGeneration.SuccessGenerator;
import FunctionalCore.Request;

public class HeadController implements MethodSubController {
    private SuccessGenerator successGenerator;

    public HeadController(SuccessGenerator successGenerator) {
        this.successGenerator = successGenerator;
    }

    public byte[] fulfill(Request request) {
        return successGenerator.generateHead(SuccessGenerator.Code.OK, request.getUri());
    }
}
