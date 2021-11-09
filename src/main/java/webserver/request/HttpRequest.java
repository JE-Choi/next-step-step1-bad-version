package webserver.request;

import java.io.InputStream;

/**
 * 중첩클래스: https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=ljpark6&logNo=221207153575
 */
interface HttpRequest {
    /**
     * HTTP 메소드
     */
    String getMathod();

    /**
     * URL
     */
    String getPath();

    /**
     * Header
     */
    String getHeader(String key);

    /**
     * 본문
     */
    String getParameter(String key);

    static class Default implements HttpRequest {
        private final InputStream in;

        public Default(InputStream in) {
            this.in = in;
        }

        @Override
        public String getMathod() {
            return "GET";
        }

        @Override
        public String getPath() {
            return "/user/create";
        }

        @Override
        public String getHeader(String key) {
            return "keep-alive";
        }

        @Override
        public String getParameter(String key) {
            return "java";
        }
    }
}
