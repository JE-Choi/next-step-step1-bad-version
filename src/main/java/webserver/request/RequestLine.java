package webserver.request;

import lombok.Getter;
import util.HttpRequestUtils;

import java.util.HashMap;
import java.util.Map;

@Getter
public class RequestLine {
    private final String method;
    private final String path;
    private final Map<String, String> params;

    public RequestLine(final String line) {
        final String[] split = line.split("\\s");
        final String method = split[0];
        this.method = method;
        // Todo: URI객체로 분리 가능
        final String[] uri = split[1].split("\\?");
        final String path = uri[0];
        this.path = path;
        params = uri.length > 1 ? HttpRequestUtils.parseQueryString(uri[1]) : new HashMap<String, String>();
    }
}
