package com.weim.utils;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author weim
 * @date 18-7-27
 */
public class HttpClientUtil {

    private static CloseableHttpClient httpClient = null;

    private final static Object syncLock = new Object();

    static final int timeOut = 10 * 1000;

    private static void config(HttpRequestBase httpRequestBase) {
        // 配置请求的超时设置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(timeOut)
                .setConnectTimeout(timeOut).setSocketTimeout(timeOut).build();
        httpRequestBase.setConfig(requestConfig);
    }


    public static CloseableHttpClient getHttpClient() {
        if (httpClient == null) {
            synchronized (syncLock) {
                if (httpClient == null) {
                    httpClient = HttpClientBuilder.create().build();
                }
            }
        }

        return httpClient;
    }

    public static String execute(HttpRequestBase httpRequestBase) throws IOException {

        config(httpRequestBase);

        CloseableHttpResponse response = getHttpClient().execute(httpRequestBase);

        if(response.getStatusLine().getStatusCode() == 200) {
            InputStream content = response.getEntity().getContent();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content));

            StringBuilder retString = new StringBuilder();

            String temp;

            while ((temp = bufferedReader.readLine()) != null) {
                retString.append(temp);
            }
            return retString.toString();
        }

        return null;
    }

    public static InputStream execute2(HttpRequestBase httpRequestBase) throws IOException {

        config(httpRequestBase);

        CloseableHttpResponse response = getHttpClient().execute(httpRequestBase);

        if(response.getStatusLine().getStatusCode() == 200) {
            return response.getEntity().getContent();
        }

        return null;

    }



}
