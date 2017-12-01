package Loggers;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Formatter {
    public static String stampTime(String report) {
        String sp = "\n";
        String humanDate = ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return humanDate + sp + report + sp;
    }
}
