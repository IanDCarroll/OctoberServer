package clArgs;

import java.io.File;
import java.io.InputStream;

public class ConditionalArgParser implements ArgParser {
    private int port = 5000;
    private String directory = System.getProperty("user.dir") + "/public";
    private String configFile = "src/main/java/routes_config.yml";
    private static final String PORT_FLAG = "-p";
    private static final String DIRECTORY_FLAG = "-d";
    private static final String CONFIG_FLAG = "-c";
    private static final String useDefault = "";

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public String getDirectory() {
        return this.directory;
    }

    @Override
    public String getConfigFile() { return configFile; }

    @Override
    public void setArgs(String[] args) {
        if (args.length == 0) {
            setSettings(useDefault, useDefault, useDefault);
        } else if (args.length == 2) {
            setOneArg(args);
        } else if (args.length == 4) {
            setTwoArgs(args);
        } else if (args.length == 6) {
            setThreeArgs(args);
        } else {
            throw new IllegalArgumentException(usageMessage());
        }
    }

    private void setOneArg(String[] args) {
        if (itsFlagged(args, 0, PORT_FLAG)) {
            setSettings(args[1], useDefault, useDefault);
        } else if (itsFlagged(args, 0, DIRECTORY_FLAG)) {
            setSettings(useDefault, args[1], useDefault);
        } else if (itsFlagged(args,0, CONFIG_FLAG)) {
            setSettings(useDefault, useDefault, args[1]);
        } else {
            throw new IllegalArgumentException(usageMessage());
        }
    }

    private void setTwoArgs(String[] args) {
        if (itsOrdered(args, PORT_FLAG, DIRECTORY_FLAG)) {
            setSettings(args[1], args[3], useDefault);
        } else if (itsOrdered(args, DIRECTORY_FLAG, PORT_FLAG)) {
            setSettings(args[3], args[1], useDefault);
        } else if (itsOrdered(args, PORT_FLAG, CONFIG_FLAG)) {
            setSettings(args[1], useDefault, args[3]);
        } else if (itsOrdered(args, CONFIG_FLAG, PORT_FLAG)) {
            setSettings(args[3], useDefault, args[1]);
        } else if (itsOrdered(args, DIRECTORY_FLAG, CONFIG_FLAG)) {
            setSettings(useDefault, args[1], args[3]);
        } else if (itsOrdered(args, CONFIG_FLAG, DIRECTORY_FLAG)) {
            setSettings(useDefault, args[3], args[1]);
        } else {
            throw new IllegalArgumentException(usageMessage());
        }
    }

    private void setThreeArgs(String[] args) {
        if (itsOrdered(args, PORT_FLAG, DIRECTORY_FLAG, CONFIG_FLAG)) {
            setSettings(args[1], args[3], args[5]);
        } else if (itsOrdered(args, PORT_FLAG, CONFIG_FLAG, DIRECTORY_FLAG)) {
            setSettings(args[1], args[5], args[3]);
        } else if (itsOrdered(args, DIRECTORY_FLAG, PORT_FLAG, CONFIG_FLAG)) {
            setSettings(args[3], args[1], args[5]);
        } else if (itsOrdered(args, DIRECTORY_FLAG, CONFIG_FLAG, PORT_FLAG)) {
            setSettings(args[5], args[1], args[3]);
        } else if (itsOrdered(args, CONFIG_FLAG, PORT_FLAG, DIRECTORY_FLAG)) {
            setSettings(args[3], args[5], args[1]);
        } else if (itsOrdered(args, CONFIG_FLAG, DIRECTORY_FLAG, PORT_FLAG)) {
            setSettings(args[5], args[3], args[1]);
        } else {
            throw new IllegalArgumentException(usageMessage());
        }
    }

    private void setSettings(String port, String directory, String configFile) {
        this.port = port.equals(useDefault) ? this.port : PortSetter.setPort(port);
        this.directory = directory.equals(useDefault) ? this.directory : DirSetter.setDir(directory);
        this.configFile = configFile.equals(useDefault) ? this.configFile : FileSetter.setFile(configFile);
    }

    private boolean itsOrdered(String[] args, String flag1, String flag2, String flag3) {
        return itsFlagged(args,0, flag1) && itsFlagged(args, 2, flag2) && itsFlagged(args, 4, flag3);
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
                "\t-d = directory (must be an existing absolute path; defaults to /public if not specified)\n" +
                "\t-c = configFile (must be explicitly set for cob_spec; default works when serving from repo's root)";
    }
}
