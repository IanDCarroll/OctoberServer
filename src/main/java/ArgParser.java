import java.io.File;
import java.io.InputStream;

public class ArgParser {
    private int port = 5000;
    private String directory = System.getProperty("user.dir") + "/public";
    private static final String PORT_FLAG = "-p";
    private static final String DIRECTORY_FLAG = "-d";

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
            this.port = PortSetter.setPort(args[1]);
        } else if (itsFlagged(args, 0, DIRECTORY_FLAG)) {
            this.directory = DirSetter.setDir(args[1]);
        } else {
            throw new IllegalArgumentException(usageMessage());
        }
    }

    private void setPortAndDirectory(String[] args) {
        if (itsOrdered(args, PORT_FLAG, DIRECTORY_FLAG)) {
            this.port = PortSetter.setPort(args[1]);
            this.directory = DirSetter.setDir(args[3]);
        } else if (itsOrdered(args, DIRECTORY_FLAG, PORT_FLAG)) {
            this.directory = DirSetter.setDir(args[1]);
            this.port = PortSetter.setPort(args[3]);
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

    public static String usageMessage() {
        return "\n\nUSAGE: java -jar target/OctoberServer-1.0-SNAPSHOT.jar\n" +
                "\t-p = port (must be a number between 0 and 65535; defaults to 5000 if not specified)\n" +
                "\t-d = directory (must be an existing absolute path; defaults to /public if not specified)\n";
    }
}
