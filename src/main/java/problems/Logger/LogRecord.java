package problems.Logger;

import java.time.Instant;
public class LogRecord {

//    - timestamp : Instant
//    - level : LogLevel
//    - message : String
//    - threadName : String
//
//    + LogRecord(timestamp, level, message, threadName)
//    + getter for all fields

    String threadName;
    String message;
    LogLevel level;
    Instant timestamp;
    public LogRecord(Instant now, LogLevel level, String message, String threadName) {
        this.timestamp = now;
        this.level = level;
        this.message = message;
        this.threadName = threadName;
    }

    public String getThreadName() {
        return threadName;
    }

    public String getMessage() {
        return message;
    }

    public LogLevel getLevel() {
        return level;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
