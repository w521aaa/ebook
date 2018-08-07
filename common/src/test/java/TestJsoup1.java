import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.HttpParams;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author weim
 * @date 18-7-26
 */
public class TestJsoup1 {

    public static void main(String[] args) throws IOException {


//        String url = "http://www.biquge.com.tw/modules/article/soshu.php?searchkey="+ URLEncoder.encode("大道朝天","gbk");
        String url = "http://www.biquge.com.tw/modules/article/soshu.php?searchkey="+ URLEncoder.encode("将夜","gbk");
        System.out.println("url>>"+url);

        Connection con = Jsoup.connect(url);

        Map<String, String> headers = new HashMap<>();

        headers.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        headers.put("Content-Type","text/html; charset=gbk");
        headers.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/63.0.3239.132 Chrome/63.0.3239.132 Safari/537.36");
        headers.put("Host", "www.biquge.com.tw");
        con.headers(headers);

        Connection.Response response = con.execute();

        Document document = Jsoup.parse(response.bodyStream(), "GBK", url);

        File file = new File("/tmp/abc6.txt");

        FileWriter writer = new FileWriter(file);

        writer.write(document.html());
        writer.flush();
        writer.close();

    }
}
