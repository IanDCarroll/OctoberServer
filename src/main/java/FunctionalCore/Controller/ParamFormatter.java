package FunctionalCore.Controller;

public class ParamFormatter {
    public static String addSpaces(String param) {
        String assignmentOperator = "=";
        String addSpaces = " " + assignmentOperator + " ";
        return param.replaceFirst(assignmentOperator, addSpaces);
    }
}
