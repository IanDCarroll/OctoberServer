package TerminalArgs;

import java.io.File;

public class FSChecker {
    public static String validateFile(String input) {
        return itsAFile(input) ? input : throwError(input);
    }

    public static String validateDir(String input) { return itsADir(input) ? input : tryCreating(input);
    }

    private static String tryCreating(String input) {
        try {
            new File(input).mkdir();
        } catch (SecurityException e) { throwError(input); }
        return input;
    }

    public static boolean itsAFile(String input) { return new File(input).isFile(); }

    public static boolean itsADir(String input) { return new File(input).isDirectory(); }

    private static String throwError(String input) {
        throw new IllegalArgumentException(notInFSMessage(input));
    }

    public static String notInFSMessage(String badInput) {
        return badInput + " does not exist, can't be created, or is not a valid type.";
    }
}
