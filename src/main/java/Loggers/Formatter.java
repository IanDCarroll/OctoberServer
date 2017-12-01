package Loggers;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Formatter {
    public static String stampTime(String report) {
        String timeStamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return stamp(report, timeStamp);
    }

    public static String stamp(String report, String stamp) {
        String sp = "\n";
        return stamp + sp + report + sp;
    }
}
