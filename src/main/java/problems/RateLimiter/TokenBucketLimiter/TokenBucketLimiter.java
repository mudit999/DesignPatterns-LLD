package problems.RateLimiter.TokenBucketLimiter;

import problems.RateLimiter.Limiter;
import problems.RateLimiter.RateLimitResult;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.floor;

public class TokenBucketLimiter implements Limiter {

//    - capacity : int
//    - refillRatePerSecond : int
//    - buckets : Map<String, TokenBucket> // String -> clientId
        // The buckets map is where we track per-client state.
        // Each key (client ID) gets its own bucket.

    private final int capacity;
    private final int refillRatePerSecond;
    private final Map<String, TokenBucket> buckets = new HashMap<>();

    public TokenBucketLimiter(int capacity, int refillRatePerSecond){
        this.capacity = capacity;
        this.refillRatePerSecond = refillRatePerSecond;
    }
    @Override
    public RateLimitResult allow(String clientId) {
//        Step 1: Get or create the bucket
          TokenBucket bucket = getOrCreateBucket(clientId);

//        Step 2: Refill tokens based on elapsed time
          long now = System.currentTimeMillis();
          long elapsed = now - bucket.lastRefillTime;
          double tokensToAdd = (elapsed * refillRatePerSecond)/1000.0; // convert from ms -> seconds
          bucket.tokens = Math.min(capacity, bucket.tokens + tokensToAdd); // cap -> never cross capacity
          bucket.lastRefillTime = now;

//        Step 3: Check if we can allow the request
          if(bucket.tokens >= 1){
              // allow the request
              // Step 4a: Allow the request (if tokens available)
              bucket.tokens -= 1;
              int remaining = (int) floor(bucket.tokens);
              return new RateLimitResult(true, remaining, null);
          }else{
              // deny the request
              // Step 4b: Deny the request (if insufficient tokens)
              double tokensNeeded = 1 - bucket.tokens;
              long retryAfterMs = (long) Math.ceil((tokensNeeded*1000)/refillRatePerSecond); // convert from seconds -> ms
              return new RateLimitResult(false, 0, retryAfterMs);
          }

    }

    private TokenBucket getOrCreateBucket(String clientId){
        if(!buckets.containsKey(clientId)){
            buckets.put(clientId, new TokenBucket(capacity, System.currentTimeMillis()));
        }
        return buckets.get(clientId);
    }
}
