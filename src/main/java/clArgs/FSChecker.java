package clArgs;

import java.io.File;

public class FSChecker {
    public static boolean checkFile(String input) {
        return new File(input).isFile();
    }

    public static boolean checkDir(String input) {
        return new File(input).isDirectory();
    }
}
