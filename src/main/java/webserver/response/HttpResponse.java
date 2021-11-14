package webserver.response;

public interface HttpResponse {
    void addHeader(String key, String value);

    void forward(String url);

    void forwardBody(String body);

    void response200Header(int status);

    void responseBody(byte[] body);

    void sendRedirect(String url);

    void processHeaders();
}
