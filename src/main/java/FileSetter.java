import java.io.File;

public class FileSetter {
    public static String setFile(String input) {
        File directoryCheck = new File(input);
        if (directoryCheck.isFile()) {
            return input;
        } else { throw new IllegalArgumentException(fileNotInFSMessage(input)); }
    }

    public static String fileNotInFSMessage(String badInput) {
        return badInput + " is not a file in the file system. Please specify the full path of an existing file.";
    }
}
