package com.weim.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author weim
 * @date 18-7-27
 */
public class DownloadUtils {

    public static void downloadImage(String url, String pathName) throws Exception {

        CloseableHttpClient client = HttpClientBuilder.create().build();

        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse respon = client.execute(httpGet);

        InputStream content = respon.getEntity().getContent();

        FileImageOutputStream outputStream = new FileImageOutputStream(new File("/tmp/abc.jpg"));

        byte[] buf = new byte[1024];

        int len;

        while ((len = content.read(buf)) != -1) {
            outputStream.write(buf, 0, len);
        }


//        URL urlTemp = new URL(url);
//        URLConnection uri = urlTemp.openConnection();
//        InputStream inputStream = uri.getInputStream();
//
//        FileImageOutputStream outputStream = new FileImageOutputStream(new File("/tmp/abcd.jpg"));
//
//        byte[] buf = new byte[1024];
//
//        int len;
//
//        while ((len = inputStream.read(buf)) != -1) {
//            outputStream.write(buf, 0, len);
//        }
    }

}
