package clArgs;

public class LengthChecker {
    public static void validateLength(int length) {
        if (badLength(length)) { throw new IllegalArgumentException(usageMessage()); }
    }

    public static void checkForLeftovers(int length, int count) {
        if (leftovers(length, count)) { throw new IllegalArgumentException(usageMessage()); }
    }

    private static boolean badLength(int length) {
        return length < 0 || length % 2 == 1 || length > 6;
    }

    private static boolean leftovers(int argsLength, int argsAccountedFor) {
        return argsLength != argsAccountedFor;
    }

    public static String usageMessage() {
        return "\n\nUSAGE: java -jar target/OctoberServer-1.0-SNAPSHOT.jar\n" +
                "\t-p = port (must be a number between 0 and 65535; defaults to 5000 if not specified)\n" +
                "\t-d = directory (must be an existing absolute path; defaults to /public if not specified)\n" +
                "\t-c = configFile (must be explicitly set for cob_spec; default works when serving from repo's root)";
    }
}
