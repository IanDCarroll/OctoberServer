package FunctionalCore.Controller.SubControllers.MethodSubControllers;

import Filers.FileClerk;
import FunctionalCore.Controller.ResponseGeneration.SuccessGenerator;
import FunctionalCore.Request;

public class DeleteController extends BodyResponder implements MethodSubController {
    private FileClerk fileClerk;

    public DeleteController(SuccessGenerator successGenerator, FileClerk fileClerk) {
        super(successGenerator);
        this.fileClerk = fileClerk;
    }

    public byte[] fulfill(Request request) {
        fileClerk.delete(request.getUri());
        return super.respondWithBody(request);
    }
}
