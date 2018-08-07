import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author weim
 * @date 18-7-26
 */
public class TestJsoup2 {

    public static void main(String[] args) throws IOException {
        String url = "http://www.biquge.com.tw/modules/article/soshu.php?searchkey="+ URLEncoder.encode("大道朝天","gbk");

        HttpGet httpGet = new HttpGet(url);
        //禁止302 301跳转
        httpGet.setConfig(RequestConfig.custom().setRedirectsEnabled(false).build());
        httpGet.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        httpGet.addHeader("Content-Type","text/html; charset=gbk");
        httpGet.addHeader("Host", "www.biquge.com.tw");

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        CloseableHttpResponse execute = httpClient.execute(httpGet);


        Header[] allHeaders = execute.getAllHeaders();

        for (int index = 0; index < allHeaders.length; index++) {
            System.out.println("name==>" + allHeaders[index].getName());
            System.out.println("value==>" + allHeaders[index].getValue());
        }
        System.out.println("name==>" + execute.getHeaders("Location")[0].getName());
        System.out.println("value==>" + execute.getHeaders("Location")[0].getValue());
    }
}
