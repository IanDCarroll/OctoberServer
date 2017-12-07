package FunctionalCore.Parser;

import Loggers.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class ParamDecoder {
    public static String[] decode(String[] params) {
        for (int i = 0; i < params.length; i++) {
            params[i] = addSpacesBeforeAndAfterAssignmentOperator(params[i]);
            params[i] = decodeParam(params[i]);
        }
        return params;
    }

    public static String addSpacesBeforeAndAfterAssignmentOperator(String param) {
        String assignmentOperator = "=";
        String addSpaces = " " + assignmentOperator + " ";
        return param.replaceFirst(assignmentOperator, addSpaces);
    }

    public static String decodeParam(String encodedParam) {
        String decodedParam;
        try {
            decodedParam = URLDecoder.decode(encodedParam, "UTF-8");
        } catch (UnsupportedEncodingException e ) { decodedParam = ""; }
        return decodedParam;
    }
}
