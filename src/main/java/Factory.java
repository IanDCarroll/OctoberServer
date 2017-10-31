public class Factory {
    // builds the server injecting the dependencies and initializing them in a final object
    // uses config.yml to inject routes to the controller
    private static final String emptyArg = "";
    private int port = 5000;
    private String directory = "/public";

    public static Factory constructYourself(String[] args) {
        if (args.length == 0) {
            return constructYourself();
        } else if (args.length == 2) {
            return constructYourself(args[0], args[1]);
        } else if (args.length == 4) {
            return constructYourself(args[0], args[1], args[2], args[3]);
        } else { throw new IllegalArgumentException(tellUsage()); }
    }

    private static Factory constructYourself() {
        return constructYourself(emptyArg, emptyArg, emptyArg, emptyArg);
    }

    private static Factory constructYourself(String flag, String value) {
        return constructYourself(flag, value, emptyArg, emptyArg);
    }

    private static Factory constructYourself(String flag1, String value1, String flag2, String value2) {
        if (improperFlags(flag1, flag2)) { throw new IllegalArgumentException(tellUsage()); }
        return new Factory(flag1, value1, flag2, value2);
    }

    private static boolean improperFlags(String flag1, String flag2) {
        return !(properFlag(flag1) && properFlag(flag2));
    }

    private static boolean properFlag(String flag) {
        return flag.equals("-p") || flag.equals("-d") || flag.equals("");
    }

    private static int integerify(String port) {
        try {
            return Integer.parseInt(port);
        }
        catch( NumberFormatException e ) {
            throw new NumberFormatException(tellUsage());
        }
    }

    private static String tellUsage() {
        return "\n\nUSAGE: java -jar target/OctoberServer-1.0-SNAPSHOT.jar\n" +
                "\t-p = port (must be a number; defaults to 5000 if not specified)\n" +
                "\t-d = directory (defaults to /public if not specified)\n";
    }

    public Factory(String flag1, String value1, String flag2, String value2) {

        if (flag1.equals("-p")) {
            this.port = integerify(value1);
        } else if (flag2.equals("-p")) {
            this.port = integerify(value2);
        }
        if (flag1.equals("-d")) {
            this.directory = value1;
        } else if (flag2.equals("-d")) {
            this.directory = value2;
        }
    }

    public ServerSocket buildServer() {
        System.out.format("building server with port %d and directory %s\n", port, directory);
        ByteConverter byteConverter = new ByteConverter();
        Parser parser = new Parser();
        ResponseGenerator responseGenerator = new ResponseGenerator();
        Controller controller = new Controller(responseGenerator);
        DeParser deParser = new DeParser();
        ClientSocket client = new ClientSocket(byteConverter, parser, controller, deParser);
        return new ServerSocket(port, directory, client);
    }
}
