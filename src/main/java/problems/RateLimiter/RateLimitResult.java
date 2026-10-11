package problems.RateLimiter;

public class RateLimitResult {
//    - allowed : boolean
//    - remaining : int
//    - retryAfterMs : Long || null
//
//    + RateLimitResult(allowed, remaining, retryAfterMs)
//    + isAllowed() -> boolean
//    + getRemaining() -> int
//    + getRetryAfterMs() -> long | null

    private final boolean allowed;
    private final int remaining;
    private final Long retryAfterMs; // null can be accepted with Long type

    public RateLimitResult(boolean allowed, int remaining, Long retryAfterMs){
        this.allowed = allowed;
        this.remaining = remaining;
        this.retryAfterMs = retryAfterMs;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public int getRemaining() {
        return remaining;
    }

    public long getRetryAfterMs() {
        return retryAfterMs;
    }
}
