package FunctionalCore.Controller;

import Filers.FileClerk;

public class RangeValidator {
    private final String rangePrefix = "Range: bytes=";
    private final String withNothing = "";
    FileClerk fileClerk;

    public RangeValidator(FileClerk fileClerk) {
        this.fileClerk = fileClerk;
    }

    public boolean valid(String uri, int[] range) {
        return range[0] <= range[1] && range[1] <= sizeOf(uri);
    }

    private int sizeOf(String uri) {
        return fileClerk.checkout(uri).length;
    }

    public String getRangeHeader(String[] headers) {
        for(String header : headers) {
            if(header.startsWith(rangePrefix)) return header;
        }
        return withNothing;
    }

    public int[] getRange(String uri, String rangeHeader) {
        int notSet = -1;
        int start = notSet;
        int end = notSet;
        String rangeValue = rangeHeader.trim().replace(rangePrefix, withNothing);
        if(rangeValue.startsWith("-")) { start = 0; }
        if(rangeValue.endsWith("-")) { end = sizeOf(uri); }
        String[] bounds = rangeValue.split("-");
        if(start!=0) { start = Integer.parseInt(bounds[0]); }
        if(end!=sizeOf(uri)) { end = Integer.parseInt(bounds[1]); }
        return new int[]{ start, end };
    }

}
