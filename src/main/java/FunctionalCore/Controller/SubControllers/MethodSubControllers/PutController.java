package FunctionalCore.Controller.SubControllers.MethodSubControllers;

import Filers.FileClerk;
import FunctionalCore.Controller.ResponseGeneration.SuccessGenerator;
import FunctionalCore.Request;

public class PutController extends BodyResponder implements MethodSubController {
    private FileClerk fileClerk;

    public PutController(SuccessGenerator successGenerator, FileClerk fileClerk) {
        super(successGenerator);
        this.fileClerk = fileClerk;
    }

    public byte[] fulfill(Request request) {
        fileClerk.rewrite(request.getUri(), request.getBody());
        return super.respondWithBody(request);
    }
}
