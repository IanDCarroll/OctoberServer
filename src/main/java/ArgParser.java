import java.io.File;
import java.io.InputStream;

public class ArgParser {
    private int port = 5000;
    private String directory = System.getProperty("user.dir") + "/public";
    private static final String PORT_FLAG = "-p";
    private static final String DIRECTORY_FLAG = "-d";
    private static final int PORT_MIN = 0;
    private static final int PORT_MAX = 65535;

    public ArgParser(String[] args) {
        setArgs(args);
    }

    public int getPort() {
        return this.port;
    }

    public String getDirectory() {
        return this.directory;
    }

    private void setArgs(String[] args) {
        if (args.length == 0) {
            //use defaults
        } else if (args.length == 2) {
            setPortOrDirectory(args);
        } else if (args.length == 4) {
            setPortAndDirectory(args);
        } else {
            throw new IllegalArgumentException(usageMessage());
        }
    }

    private void setPortOrDirectory(String[] args) {
        if (itsFlagged(args, 0, PORT_FLAG)) {
            this.port = parsePort(args[1]);
        } else if (itsFlagged(args, 0, DIRECTORY_FLAG)) {
            this.directory = parseDirectory(args[1]);
        } else {
            throw new IllegalArgumentException(usageMessage());
        }
    }

    private void setPortAndDirectory(String[] args) {
        if (itsOrdered(args, PORT_FLAG, DIRECTORY_FLAG)) {
            this.port = parsePort(args[1]);
            this.directory = parseDirectory(args[3]);
        } else if (itsOrdered(args, DIRECTORY_FLAG, PORT_FLAG)) {
            this.directory = parseDirectory(args[1]);
            this.port = parsePort(args[3]);
        } else {
            throw new IllegalArgumentException(usageMessage());
        }
    }

    private boolean itsOrdered(String[] args, String flag1, String flag2) {
        return itsFlagged(args, 0, flag1) && itsFlagged(args, 2, flag2);

    }

    private boolean itsFlagged(String[] args, int index, String flag) {
        return args[index].equals(flag);
    }

    private int parsePort(String port) {
        try {
            return validatePort(Integer.parseInt(port));
        } catch (NumberFormatException e) {
            throw new NumberFormatException(portNumberFormatErrorMessage(port));
        }
    }

    private int validatePort(int port) {
        if (port >= PORT_MIN && port <= PORT_MAX) {
            return port;
        } else { throw new IllegalArgumentException(portOurOfRangeMessage(String.valueOf(port))); }
    }

    private String parseDirectory(String directory) {
        File directoryCheck = new File(directory);
        if (directoryCheck.isDirectory()) {
            return directory;
        } else { throw new IllegalArgumentException(directoryNotInFSMessage(directory)); }
    }

    public static String portNumberFormatErrorMessage(String badInput) {
        return badInput + " is not a number. Please only use Hindu-Arabic numerals to represent the port.";
    }

    public static String portOurOfRangeMessage(String badInput) {
        return badInput + " is not a valid port. Please use a number between 0 and 65535.";
    }

    public static String directoryNotInFSMessage(String badInput) {
        return badInput + " is not a directory in the file system. Please specify an existing directory,.";
    }

    public static String usageMessage() {
        return "\n\nUSAGE: java -jar target/OctoberServer-1.0-SNAPSHOT.jar\n" +
                "\t-p = port (must be a number between 0 and 65535; defaults to 5000 if not specified)\n" +
                "\t-d = directory (must be an existing absolute path; defaults to /public if not specified)\n";
    }
}
