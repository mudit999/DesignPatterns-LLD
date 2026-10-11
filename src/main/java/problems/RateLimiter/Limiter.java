package problems.RateLimiter;

public interface Limiter {
//    + allow(key) -> RateLimitResult
    public RateLimitResult allow(String clientId);
}
