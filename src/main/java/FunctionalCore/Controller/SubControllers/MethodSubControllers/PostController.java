package FunctionalCore.Controller.SubControllers.MethodSubControllers;

import Filers.FileClerk;
import FunctionalCore.Controller.ResponseGeneration.SuccessGenerator;
import FunctionalCore.Request;

public class PostController extends BodyResponder implements MethodSubController {

    private FileClerk fileClerk;

    public PostController(SuccessGenerator successGenerator, FileClerk fileClerk) {
        super(successGenerator);
        this.fileClerk = fileClerk;
    }

    public byte[] fulfill(Request request) {
        fileClerk.append(request.getUri(), request.getBody());
        return super.respondWithBody(request);
    }
}
