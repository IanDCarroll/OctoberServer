package FunctionalCore.Controller;

import Filers.FileClerk;

public class RangeValidator {
    FileClerk fileClerk;

    public RangeValidator(FileClerk fileClerk) {
        this.fileClerk = fileClerk;
    }

    public boolean valid(String uri, String rangeHeader) {
        int[] range = getRange(uri, rangeHeader);
        if(invalid(uri, range)) { return false; }
        return true;
    }

    private boolean invalid(String uri, int[] range) {
        return range[0] > range[1] || range[1] > sizeOf(uri);
    }

    private int sizeOf(String uri) {
        return fileClerk.checkout(uri).length;
    }

    public int[] getRange(String uri, String rangeHeader) {
        String rangeValue = rangeHeader.replace("Range: bytes=", "");
        String[] range = rangeValue.split("-");
        return parseValues(uri, range);
    }

    private int[] parseValues(String uri, String[] values) {
        int[] range = new int[2];
        range[0] = parseValue(values[0]);
        try {
            range[1] = parseValue(values[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            range[1] = sizeOf(uri);
        }
        return range;
    }

    private int parseValue(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
