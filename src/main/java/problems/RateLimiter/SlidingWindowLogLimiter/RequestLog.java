package problems.RateLimiter.SlidingWindowLogLimiter;

import java.util.LinkedList;
import java.util.Queue;

// remove from front
// add at Last
public class RequestLog {
//- timestamps: Queue<long>

    Queue<Long> timestamps;

    public RequestLog(){
        this.timestamps = new LinkedList<>();
    }
}
