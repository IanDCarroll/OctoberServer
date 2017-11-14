import java.io.File;

public class DirSetter {

    public static String setDir(String input) {
        File directoryCheck = new File(input);
        if (directoryCheck.isDirectory()) {
            return input;
        } else { throw new IllegalArgumentException(directoryNotInFSMessage(input)); }
    }

    public static String directoryNotInFSMessage(String badInput) {
        return badInput + " is not a directory in the file system. Please specify the full path of an existing directory.";
    }
}
