package com.weim.utils;

import org.apache.logging.log4j.util.PropertiesUtil;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * @author weim
 * @date 18-6-28
 */

public class CommonUtils {

    //已经下载了
    public final static Integer IS_DOWNLOAD = 1;
    //等待下载
    public final static Integer IS_NOT_DOWNLOAD = 0;

    //ebook 已经完结了
    public final static Integer IS_END = 1;
    //ebook 没有完结
    public final static Integer IS_NOT_END = 0;

    //message 已经处理了
    public final static Integer IS_DEAL = 1;
    //message 没有处理
    public final static Integer IS_NOT_DEAL = 0;




    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-","");
    }


    public static String getNowDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }



    public static String localHostName = null;

    /**
     * 从配置文件中读取 域名
     * @return
     */
    public static String getLocalHostName() {

        if(StringUtils.isEmpty(localHostName)) {

            Properties properties = new Properties();

            try {
                properties.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties"),"UTF-8"));
            } catch (IOException e) {
                System.out.println((getNowDate() + " 配置文件读取异常" + e.getMessage()));
            }

//            InputStream inputStream = Object.class.getResourceAsStream("/application.properties");
//            try {
//                properties.load(inputStream);
//            } catch (IOException e) {
//                System.out.println(getNowDate() + "   配置文件读取异常:" + e.getMessage());
//            }

            localHostName = properties.getProperty("access.host.domain.name");
            System.out.println("access.host.domain.name>>>>" + localHostName);
        }

        return localHostName;
    }

    /**
     * 获取项目的绝对路径
     * @return
     */
    public static String getUplodFilePath() {

//        String path = WebMvcConfig.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//        path = path.substring(1, path.length());
//
//        try {
//            path = java.net.URLDecoder.decode(path, "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        int lastIndex = path.lastIndexOf("/") + 1;
//        path.substring(0, lastIndex);

        File file = new File("");
        return file.getAbsolutePath() + "/upload/";
    }


    private static ExecutorService executorService = new ScheduledThreadPoolExecutor(50);
    public static ExecutorService getExecutorService() {
        return CommonUtils.executorService;
    }



    private static ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(50);
    public static ScheduledExecutorService getScheduledExecutorService() {
        return CommonUtils.scheduledExecutorService;
    }



    /**
     * 获取路径下的所有文件名
     * @param pathName
     */

    public static String[] getPathFileName(String pathName) {

        if(StringUtils.isEmpty(pathName)) {
            pathName = CommonUtils.getUplodFilePath()+"/text";
        }

        File file = new File(pathName);

        return file.list();
    }

    /**
     * 获取路径下的所有文件
     * @param pathName
     */

    public static File[] getPathFile(String pathName) {

        if(StringUtils.isEmpty(pathName)) {
            pathName = CommonUtils.getUplodFilePath()+"/text";
        }

        File file = new File(pathName);

        return file.listFiles();
    }




    public static void main(String[] args) {
        System.out.println(getUplodFilePath());
    }
}
