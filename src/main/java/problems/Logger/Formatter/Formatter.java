package problems.Logger.Formatter;

import problems.Logger.LogRecord;

public interface Formatter {
//    - format (logRecord) -> String
    String format(LogRecord record);
}

//class PlainTextFormatter implements Formatter
//class JsonFormatter implements Formatter

