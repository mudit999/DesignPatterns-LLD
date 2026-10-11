package problems.RateLimiter;

import problems.RateLimiter.SlidingWindowLogLimiter.SlidingWindowLogLimiter;
import problems.RateLimiter.TokenBucketLimiter.TokenBucketLimiter;

import java.util.Collections;
import java.util.Map;

public class LimiterFactory {
//    + create(externalConfig) -> Limiter

    public Limiter create(Map<String, Object> config) {
        String algorithm = (String) config.get("algorithm");
        Map<String, Object> algoConfig = (Map<String, Object>) config.get("algoConfig");

        if (algoConfig == null) {
            algoConfig = Collections.emptyMap(); // if config is not present, then put algoConfig as empty map
        }

        if ("TokenBucket".equals(algorithm)) {
            return new TokenBucketLimiter(
                    (int) algoConfig.getOrDefault("capacity", 0),
                    (int) algoConfig.getOrDefault("refillRatePerSecond", 0)
            );
        }

        if ("SlidingWindowLog".equals(algorithm)) {
                return new SlidingWindowLogLimiter(
                        (int) algoConfig.get("maxRequest"),
                        (Long) algoConfig.get("windowMs")
                );
        }

        throw new IllegalArgumentException("Unknown algorithm: " + algorithm);
    }
}
