import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author weim
 * @date 18-7-26
 */
public class TestJsoup {

    public static void main(String[] args) throws IOException {

        String url = "http://www.biquge.com.tw/18_18949/";

        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000)
                .setSocketTimeout(10000).build();

        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();


        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        httpGet.addHeader("Content-Type","text/html; charset=gbk");
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/63.0.3239.132 Chrome/63.0.3239.132 Safari/537.36");

//        Connection con = Jsoup.connect(url);
//
//        Map<String,String> headers = new HashMap<>();
//
//        headers.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//        headers.put("Content-Type","text/html; charset=gbk");
//        headers.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/63.0.3239.132 Chrome/63.0.3239.132 Safari/537.36");
//        con.headers(headers);
//
//        Connection.Response response = con.execute();

        //Document document = Jsoup.parse(response.body());

//        Document document = Jsoup.parse(new URL(url).openStream(), "GBK", url);

        Document document = Jsoup.parse(httpClient.execute(httpGet).getEntity().getContent(),"gbk",url);

//        File file = new File("/tmp/abc.txt");
//
//        FileWriter writer = new FileWriter(file);
//
//        writer.write(document.toString());
//        writer.flush();
//        writer.close();
//        byte[] bytes = document.toString().getBytes("utf-8");
//        System.out.println(new String(bytes, "utf-8"));

//        byte[] text = document.select("#info").select("h1").text().getBytes("utf-8");
//        System.out.println("++++++++++++++++++++++++"+new String(text, "gbk"));
//        System.out.println(new String(text,"utf-8"));


        String text = document.select("#info").select("h1").text();
        System.out.println(document.select("#info").html());
        System.out.println("-------------------------------------------");
        System.out.println(document.select("#info").outerHtml());  // 包含select标签
        System.out.println("-------------------------------------------");
        System.out.println(text);
    }
}
