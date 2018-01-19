package FunctionalCore.Controller.SubControllers.MethodSubControllers;

import Filers.FileClerk;
import FunctionalCore.Controller.ResponseGeneration.ETagGenerator;
import FunctionalCore.Controller.ResponseGeneration.SuccessGenerator;
import FunctionalCore.Request;

public class PatchController implements MethodSubController {
    private ETagGenerator eTagGenerator;
    private FileClerk fileClerk;

    public PatchController(ETagGenerator eTagGenerator, FileClerk fileClerk) {
        this.eTagGenerator = eTagGenerator;
        this.fileClerk = fileClerk;
    }

    public byte[] fulfill(Request request) {
        fileClerk.append(request.getUri(), request.getBody());
        return eTagGenerator.generate(SuccessGenerator.Code.NO_CONTENT, request);
    }
}
