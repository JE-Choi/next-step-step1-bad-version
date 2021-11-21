package webserver.request;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class RequestLine {
    private final String method;
    private final String path;
    private final Map<String, String> params;

    public RequestLine(final String requestLine) {
        this.method = "GET";
        this.path = "/user/create";
        this.params = new HashMap<String, String>() {{
            put("userId", "java");
            put("password", "1234");
        }};
    }
}
