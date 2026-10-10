package problems.Logger.Sink;

import java.io.IOException;

public interface Sink {
//    + write(formatted: String)
    void write(String formatted) throws IOException;
}

//class ConsoleSink implements Sink
//class FileSink implements Sink
//  - filePath: String