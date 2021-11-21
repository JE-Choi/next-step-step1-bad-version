package webserver.request;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class RequestLineTest {
    @Test
    public void create_method(){
        final RequestLine line = new RequestLine("GET /user/create?userId=java&password=1234&name=JiEun HTTP/1.1");
        Assertions.assertThat(line.getMethod()).isEqualTo("GET");
        Assertions.assertThat(line.getPath()).isEqualTo("/user/create");
        Assertions.assertThat(line.getParams().get("userId")).isEqualTo("java");
    }
}
