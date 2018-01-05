package FunctionalCore.Controller;

import Filers.FileClerk;
import FunctionalCore.Controller.ResponseGeneration.RangeGenerator;
import FunctionalCore.Request;

import java.util.LinkedHashMap;

public class RangeController extends HeadHunter implements SubController {
    private final String rangePrefix = "Range: bytes=";
    private final String withNothing = "";
    FileClerk fileClerk;
    RangeGenerator rangeGenerator;

    public RangeController(FileClerk fileClerk, RangeGenerator rangeGenerator) {
        this.fileClerk = fileClerk;
        this.rangeGenerator = rangeGenerator;
    }

    public boolean relevant(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) {
        String isThisARangeHeader = getRangeHeader(request.getHeaders());
        return !isThisARangeHeader.isEmpty();
    }

    public byte[] generate(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) {
        String uri = request.getUri();
        String rangeHeader = getRangeHeader(request.getHeaders());
        return findRange(uri, rangeHeader);
    }

    public byte[] findRange(String uri, String rangeHeader) {
        int[] rangeTuple = getRange(uri, rangeHeader);
        return valid(uri, rangeTuple)
                ? rangeGenerator.generate(RangeGenerator.Code.PARTIAL_CONTENT, uri, rangeTuple)
                : rangeGenerator.generate(RangeGenerator.Code.RANGE_NOT_SATISFIABLE, uri);
    }

    public boolean valid(String uri, int[] range) {
        return range[0] <= range[1] && range[1] <= sizeOf(uri);
    }

    private int sizeOf(String uri) { return fileClerk.checkout(uri).length; }

    public String getRangeHeader(String[] headers) {
        return getHeader(headers, rangePrefix);
    }

    public int[] getRange(String uri, String rangeHeader) {
        int notSet = -1;
        int start = notSet;
        int end = notSet;
        String rangeValue = rangeHeader.trim().replace(rangePrefix, withNothing);
        if(rangeValue.startsWith("-")) { start = 0; }
        if(rangeValue.endsWith("-")) { end = sizeOf(uri); }
        String[] bounds = rangeValue.split("-");
        if(start==notSet) { start = Integer.parseInt(bounds[0]); }
        if(end==notSet) { end = Integer.parseInt(bounds[1]); }
        return new int[]{ start, end };
    }

}
