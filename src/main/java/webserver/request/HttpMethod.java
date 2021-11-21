package webserver.request;

import java.util.Arrays;

public enum HttpMethod {
    GET, POST, PUT, DELETE, NOT_FOUND;
    public static HttpMethod getMethod(final String str) {
        return Arrays.stream(values()).filter(method -> method.name().equals(str)).findFirst().orElse(NOT_FOUND);
    }

    public boolean isGet() {
        return this == GET;
    }

    public boolean isPost() {
        return this == POST;
    }
}
