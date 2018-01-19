package FunctionalCore.Controller.SubControllers.MethodSubControllers;

import FunctionalCore.Controller.ResponseGeneration.SuccessGenerator;
import FunctionalCore.Request;

public class GetController extends BodyResponder implements MethodSubController {

    public GetController(SuccessGenerator successGenerator) {
        super(successGenerator);
    }

    public byte[] fulfill(Request request) {
        return super.respondWithBody(request);
    }
}
