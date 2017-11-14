public class PortSetter {
    private static final int PORT_MIN = 0;
    private static final int PORT_MAX = 65535;

    public static int setPort(String input) {
        try {
            return validatePort(Integer.parseInt(input));
        } catch (NumberFormatException e) {
            throw new NumberFormatException(portNumberFormatErrorMessage(input));
        }
    }

    private static int validatePort(int port) {
        if (port >= PORT_MIN && port <= PORT_MAX) {
            return port;
        } else {
            throw new IllegalArgumentException(portOutOfRangeMessage(
                    String.valueOf(port), String.valueOf(PORT_MIN), String.valueOf(PORT_MAX))); }
    }

    public static String portNumberFormatErrorMessage(String badInput) {
        return badInput + " is not a number. Please only use Hindu-Arabic numerals to represent the port.";
    }

    public static String portOutOfRangeMessage(String badInput, String min, String max) {
        return badInput + " is not a valid port. Please use a number between " + min + " and " + max + ".";
    }
}
