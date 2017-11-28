package clArgs;

import java.util.HashMap;

public class HashLoopArgParser implements ArgParser {
    private HashMap<String, String> parsedArgs = new HashMap<>();

    private static final String PORT_FLAG = "-p";
    private static final String DIRECTORY_FLAG = "-d";
    private static final String CONFIG_FLAG = "-c";
    private static final String defaultPort = "5000";
    private static final String defaultDirectory = System.getProperty("user.dir") + "/public";
    private static final String defaultConfigFile = "src/main/java/routes_config.yml";

    private int argsAccountedFor = 0;

    public HashLoopArgParser() {
        parsedArgs.put(PORT_FLAG, defaultPort);
        parsedArgs.put(DIRECTORY_FLAG, defaultDirectory);
        parsedArgs.put(CONFIG_FLAG, defaultConfigFile);
    }

    @Override
    public void setArgs(String[] args) {
        LengthChecker.validateLength(args.length);
        setEach(args);
        LengthChecker.checkForLeftovers(args.length, argsAccountedFor);
    }

    private void setEach(String[] args) {
        for (String flag : parsedArgs.keySet()) {
            setArgsWithFlag(args, flag);
        }
    }

    private void setArgsWithFlag(String[] args, String flag) {
        for (int i = 0; i < args.length; i++) {
            if (flag.equals(args[i])) { setAndCountArg(flag, args[i+1]); }
        }
    }

    private void setAndCountArg(String key, String value) {
        parsedArgs.put(key, value);
        argsAccountedFor += 2;
    }

    @Override
    public String getDirectory() {
        return FSChecker.validateDir(parsedArgs.get(DIRECTORY_FLAG));
    }

    @Override
    public int getPort() {
        return PortChecker.validatePort(parsedArgs.get(PORT_FLAG));
    }

    @Override
    public String getConfigFile() {
        return FSChecker.validateFile(parsedArgs.get(CONFIG_FLAG));
    }
}
