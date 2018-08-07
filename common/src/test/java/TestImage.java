import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageInputStream;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author weim
 * @date 18-7-27
 */
public class TestImage {

    public static void main(String[] args) throws IOException {

        String url = "http://www.biquge.com.tw/files/article/image/18/18949/18949s.jpg";


//        CloseableHttpClient client = HttpClientBuilder.create().build();
//
//        HttpGet httpGet = new HttpGet(url);
//
//        CloseableHttpResponse respon = client.execute(httpGet);
//
//        InputStream content = respon.getEntity().getContent();
//
//        FileImageOutputStream outputStream = new FileImageOutputStream(new File("/tmp/abc.jpg"));
//
//        byte[] buf = new byte[1024];
//
//        int len;
//
//        while ((len = content.read(buf)) != -1) {
//            outputStream.write(buf, 0, len);
//        }


        URL urlTemp = new URL(url);
        URLConnection uri = urlTemp.openConnection();
        InputStream inputStream = uri.getInputStream();

        FileImageOutputStream outputStream = new FileImageOutputStream(new File("/tmp/abcd.jpg"));

        byte[] buf = new byte[1024];

        int len;

        while ((len = inputStream.read(buf)) != -1) {
            outputStream.write(buf, 0, len);
        }


    }
}
