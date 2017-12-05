package FunctionalCore.Parser;

import java.util.Arrays;

public class ByteSplitter {

    public static byte[][] splitByPattern(byte[] source, byte[] pattern) {
        for(int i = 0; i < source.length; i++) {
            int endOfSample = i + pattern.length;
            byte[] sample = Arrays.copyOfRange(source, i, endOfSample);
            if (Arrays.equals(sample, pattern)) { return split(source, i, pattern.length); }
        }
        return new byte[][] { source, new byte[0] };
    }

    private static byte[][] split(byte[] source, int start, int thickness) {
        byte[] head = Arrays.copyOfRange(source, 0, start);
        byte[] body = Arrays.copyOfRange(source, start+thickness, source.length);
        return new byte[][] { head, body };
    }
}
