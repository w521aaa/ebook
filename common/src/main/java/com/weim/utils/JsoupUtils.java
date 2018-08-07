package com.weim.utils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.util.StringUtils;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author weim
 * @date 18-7-26
 */
public class JsoupUtils {

    public static String getHtmlString(String url, String charsetName) throws IOException {

        //设置时间5s
        Connection connect = Jsoup.connect(url).timeout(5000);

        connect.headers(getHeaders());

        Connection.Response response = connect.execute();

        Document document = Jsoup.parse(response.bodyStream(), charsetName, url);

        return document.html().replaceAll("<script>.*</script>", "");
    }

    public static String getHtmlString(String url, String charsetName, String filterKey) throws IOException {

        //设置时间5s
        Connection connect = Jsoup.connect(url).timeout(5000);
        connect.headers(getHeaders());

        Connection.Response response = connect.execute();

        Document document = Jsoup.parse(response.bodyStream(), charsetName, url);

        return document.select(filterKey).html().replaceAll("<script>.*</script>", "");
    }

    private static Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>(3);
        headers.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        headers.put("Content-Type","text/html; charset=gbk");
        headers.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:60.0) Gecko/20100101 Firefox/60.0");
        return headers;
    }


    /**
     * @param url
     * @param pathName
     * @return
     * @throws Exception
     */

    public static String downloadImage(String url, String pathName) throws IOException {

        if(StringUtils.isEmpty("")) {
            //eg: pathName=/home/xxx/ebook/upload/image
            pathName = CommonUtils.getUplodFilePath()+"/image";
        }

        File imageRootFile = new File(pathName);
        if(!imageRootFile.exists()) {
            imageRootFile.mkdirs();
        }

        //eg: url=http://www.biquge.com.tw/files/article/image/18/18949/18949s.jpg;
        //eg: imageFilePath=/home/xxx/ebook/upload/image/18949s.jpg
        String fileName = url.substring(url.lastIndexOf("/"));
        String imageFilePath = imageRootFile + fileName;

        File imageFile = new File(imageFilePath);
        //不存在则 下载保存
        if(!imageFile.exists()) {
            URL urlTemp = new URL(url);
            URLConnection uri = urlTemp.openConnection();
            InputStream inputStream = uri.getInputStream();

            FileImageOutputStream outputStream = new FileImageOutputStream(imageFile);

            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        }

        //本地访问路径
        return CommonUtils.getLocalHostName() + "/upload/image" + fileName;
    }

}
