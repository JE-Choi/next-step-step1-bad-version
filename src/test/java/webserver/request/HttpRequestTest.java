package webserver.request;

import org.junit.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class HttpRequestTest {
    private String testDir = "src/test/resources/";

    @Test
    public void 파일이_존재하는가() throws Exception {
        final File file = new File(testDir + "Http_GET.txt");
        final InputStream in = new FileInputStream(file);
        assertThat(file.canRead() && file.isFile(), is(true));
    }

    @Test
    public void request_GET() throws Exception {
        final File file = new File(testDir + "Http_GET.txt");
        final InputStream in = new FileInputStream(file);
        HttpRequest.Default request = new HttpRequest.Default(in);
        assertEquals(true, request.getMathod().isGet());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("java", request.getParameter("userId"));
    }

    @Test
    public void request_POST() throws Exception{
        final File file = new File(testDir + "Http_POST.txt");
        final InputStream in = new FileInputStream(file);
        final HttpRequest.Default request = new HttpRequest.Default(in);
        assertEquals(true, request.getMathod().isPost());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("java", request.getParameter("userId"));
    }

}