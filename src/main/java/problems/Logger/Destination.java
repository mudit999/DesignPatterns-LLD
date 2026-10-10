package problems.Logger;

import problems.Logger.Formatter.Formatter;
import problems.Logger.Sink.Sink;

import java.util.concurrent.locks.ReentrantLock;

public class Destination {
//    - formatter: Formatter
//    - minLevel: logLevel
//    - sink: Sink

//    + Destination(formatter, minLevel, sink)
//    + write(record: LogRecord)

    private final Formatter formatter;
    private final LogLevel minLevel;
    private final Sink sink;
    private final ReentrantLock lock;
    public Destination(Formatter formatter, LogLevel minLevel, Sink sink){
        this.formatter = formatter;
        this.minLevel = minLevel;
        this.sink = sink;
        this.lock = new ReentrantLock();
    }

    public void write(LogRecord record){
//        1. Drop the record if its level is below the destination's threshold.
//        2. Format the record using the formatter.
//        3. Acquire the per-destination lock.
//        4. Hand the formatted string to the sink.
//        5. Release the lock (always, even if the sink throws).

        if(record.getLevel().isAtLeast(minLevel)){
            return; // silent drop
        }

        String formatted = formatter.format(record);

        lock.lock();
        try{
            sink.write(formatted);
        }catch (Exception e){
            System.err.println("logger: sink write failed: " + e.getMessage());
            // write a one-line diagnostic to a known-good fallback stream.
            // The original record is still lost, but the failure itself is visible.
            // The fallback is typically stderr (or a separate "internal logger" if your library has one).
        }finally {
            lock.unlock();
        }
    }
}
