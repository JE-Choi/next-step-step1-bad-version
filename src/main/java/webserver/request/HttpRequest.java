package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        private static final Logger logger = LoggerFactory.getLogger(Default.class);
        private static final String INVALID_VALUE_MSG = "유효하지 않은 값";

        private RequestLine requestLine;
        private Map<String, String> header = new HashMap<>();
        private Map<String, String> parameter = new HashMap<>();

        public Default(InputStream in) {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"))) {
                String line = bufferedReader.readLine();
                while (!"".equals(line) && line != null) {
                    final HttpRequestUtils.Pair pair = HttpRequestUtils.parseHeader(line);
                    final boolean isRequestLine = pair == null;
                    final boolean isRequestHeader = pair != null;
                    if (isRequestLine) {
                        this.requestLine = new RequestLine(line);
                    } else if (isRequestHeader) {
                        logger.debug("header : {}", line);
                        this.header.put(pair.getKey(), pair.getValue());
                    }
                    line = bufferedReader.readLine();
                }

                // 모든 버퍼의 데이터를 돌았으면 => 요청값에 맞게 parameter를 세팅해준다.
                try {
                    final int contentLength = Integer.parseInt(this.header.getOrDefault("Content-Length", "0"));
                    if (!"GET".equals(this.getMathod()) && contentLength > 0) {
                        final String body = IOUtils.readData(bufferedReader, contentLength);
                        this.parameter = HttpRequestUtils.parseQueryString(body);
                    } else if ("GET".equals(this.getMathod())) {
                        this.parameter = this.requestLine.getParams();
                    }
                } catch (NumberFormatException | NullPointerException e) { }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String getMathod() {
            if (Objects.isNull(this.requestLine)) {
                logger.debug(INVALID_VALUE_MSG);
            }
            return this.requestLine.getMethod();
        }

        @Override
        public String getPath() {
            if (Objects.isNull(this.requestLine)) {
                logger.debug(INVALID_VALUE_MSG);
            }
            return this.requestLine.getPath();
        }

        @Override
        public String getHeader(String key) {
            final String result = this.header.getOrDefault(key, "");
            if ("".equals(result)) {
                logger.debug(INVALID_VALUE_MSG);
            }
            return result;
        }

        @Override
        public String getParameter(String key) {
            final String result = this.parameter.getOrDefault(key, "");
            if ("".equals(result)) {
                logger.debug(INVALID_VALUE_MSG);
            }
            return result;
        }
    }
}
