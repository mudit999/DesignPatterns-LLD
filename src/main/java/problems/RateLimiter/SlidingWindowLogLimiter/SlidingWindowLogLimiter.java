package problems.RateLimiter.SlidingWindowLogLimiter;

import problems.RateLimiter.Limiter;
import problems.RateLimiter.RateLimitResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class SlidingWindowLogLimiter implements Limiter {
//    - maxRequest: int
//    - windowMs : Long
//    - logs : Map<String, RequestLog>

    int maxRequest;
    long windowMs;
    Map<String, RequestLog> logs = new HashMap<>();

    public SlidingWindowLogLimiter(int maxRequest, Long windowMs){
        this.maxRequest = maxRequest;
        this.windowMs = windowMs;
    }

//    allow - core logic
//    1. Get or create the log for this client
//    2. Remove stale timestamps that fall outside the window
//    3. Check if we're under the limit
//    4. Add the current timestamp if allowed, or calculate retry time if denied
    @Override
    public RateLimitResult allow(String clientId) {
//        Step 1: Get or create the log
        RequestLog log = getOrCreateLog(clientId);

//        Step 2: Remove stale timestamps
        long now = System.currentTimeMillis();
        Long cutoff = now - windowMs;
        Queue<Long> timestampQueue = log.timestamps;
        while(!timestampQueue.isEmpty() && timestampQueue.peek() < cutoff){
            timestampQueue.poll();
        }

//        Step 3: Check if we're under the limit
        if(timestampQueue.size() < maxRequest){
            // allow request
            timestampQueue.add(now);
            return new RateLimitResult(true, maxRequest - timestampQueue.size(), null);
        }else{
            // deny
            Long oldestTimestamp = timestampQueue.peek();
            Long retryAfterMs = (oldestTimestamp + windowMs) - now;
            return new RateLimitResult(false, 0, retryAfterMs);
        }
    }

    private RequestLog getOrCreateLog(String clientId){
        if(logs.containsKey(clientId)){
            return logs.get(clientId);
        }else{
            return logs.put(clientId, new RequestLog());
        }
    }
}
