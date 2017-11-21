package clArgs;

public class FSSetter {
    public static String set(boolean itChecksOut, String input) {
        if (itChecksOut) {
            return input;
        } else { throw new IllegalArgumentException(notInFSMessage(input)); }
    }

    public static String notInFSMessage(String badInput) {
        return badInput + " does not exist or is not a valid type.";
    }
}
