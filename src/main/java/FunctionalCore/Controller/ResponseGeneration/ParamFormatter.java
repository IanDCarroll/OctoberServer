package FunctionalCore.Controller.ResponseGeneration;

public class ParamFormatter {
    public static String[] addStyling(String[] params) {
        for(int i = 0; i < params.length; i ++) {
            params[i] = addStyling(params[i]);
        }
        return params;
    }

    public static String addStyling(String param) {
        String assignmentOperator = "=";
        String addSpaces = " " + assignmentOperator + " ";
        String nl = "\n";
        return param.replaceFirst(assignmentOperator, addSpaces) + nl;
    }
}
