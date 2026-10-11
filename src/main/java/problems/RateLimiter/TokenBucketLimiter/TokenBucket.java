package problems.RateLimiter.TokenBucketLimiter;

public class TokenBucket {
//    - tokens : double
//    - lastRefillTime : long

    double tokens;
    long lastRefillTime;
    public TokenBucket(double initialTokens, long lastRefillTime){
        this.tokens = initialTokens;
        this.lastRefillTime = lastRefillTime;
    }
}
