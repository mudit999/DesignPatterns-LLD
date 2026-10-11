package problems.RateLimiter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RateLimiter {
//    - limiters : Map<String, Limiter>
//    - defaultLimiter : Limiter

//    + RateLimiter(config, defaultConfig)
//    + allow(clientId, endpoint) -> RateLimitResult

    private final HashMap<String, Limiter> limiters;
    private final Limiter defaultLimiter;

    // Each config has endpoint, algo, algoConfig
    public RateLimiter(List<Map<String, Object>> configs, Map<String, Object> defaultConfig){
        // all limiters are initialization at startup once -> eager initialization
        this.limiters = new HashMap<>();
        LimiterFactory factory = new LimiterFactory();

        for(Map<String, Object> config : configs){
            String endpoint = (String) config.get("endpoint");
            if(endpoint == null){
                continue;
            }
            Limiter limiter = factory.create(config); // it will instance correct algo according to config
            limiters.put(endpoint,limiter);
        }

        // create default limiter
        this.defaultLimiter = factory.create(defaultConfig);
    }

    public RateLimitResult allow(String clientId, String endpoint){
        Limiter limiter = limiters.get(endpoint);

        if(limiter == null){
            limiter = defaultLimiter;
        }
        return limiter.allow(clientId);
    }
}
