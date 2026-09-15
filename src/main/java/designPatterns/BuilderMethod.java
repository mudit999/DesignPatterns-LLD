package designPatterns;

import java.util.HashMap;
import java.util.Map;

class HttpRequest{
    private String url;
    private String method;
    private Map<String, String> headers;
    private String body;

    private HttpRequest(){}

    public static class Builder{
        private HttpRequest request = new HttpRequest();

        public Builder url(String url){
            request.url = url;
            return this;
        }

        public Builder method(String method){
            request.method = method;
            return this;
        }

        public Builder header(String key, String value){
            if(request.headers == null){
                request.headers = new HashMap<>();
            }
            request.headers.put(key, value);
            return this;
        }

        public Builder body(String body){
            request.body = body;
            return this;
        }

        public HttpRequest build(){
            // validate fields
            if(request.url == null){
                throw new IllegalStateException("URL is required");
            }
            return request;
        }
    }
}

public class BuilderMethod {
    public static void main(String[] args) {
        HttpRequest request = new HttpRequest.Builder()
                .url("https://www.helloInterview.com")
                .method("Post")
                .header("Content-Type", "application/json")
                .body("{\"key\": \"value\"}")
                .build();
    }
}

/**

// HttpRequest request = new HttpRequest.Builder()
// public static class Builder{

 * HttpRequest
 *     │
 *     │ static
 *     ↓
 * Builder can be accessed without
 * HttpRequest object
 *     │
 *     │ new
 *     ↓
 * Create Builder object


// Static nested class = doesn’t need an outer-class object.
// It can still be instantiated into its own objects using new.

 */