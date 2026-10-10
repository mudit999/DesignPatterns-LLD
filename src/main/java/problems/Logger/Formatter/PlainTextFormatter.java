package problems.Logger.Formatter;

import problems.Logger.Formatter.Formatter;
import problems.Logger.LogRecord;

public class PlainTextFormatter implements Formatter {
    @Override
    public String format(LogRecord record) {
        return record.getTimestamp() + " [" + record.getLevel() + "]" + record.getThreadName() + " " + record.getMessage();
    }
}
