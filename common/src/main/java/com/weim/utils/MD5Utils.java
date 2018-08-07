package com.weim.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @author weim
 * @date 18-8-1
 */
public class MD5Utils {

    public static String encode(String str) {

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.update(str.getBytes());

            return new BigInteger(1, messageDigest.digest()).toString(16);

        } catch (Exception e) {
            System.out.println(CommonUtils.getNowDate() + "  error: MD5 error" + str );
            return str;
//            throw new Exception("MD5加密出现错误，"+e.toString());
        }
    }

    public static String encode2(String str) {

        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(str.getBytes());

            byte[] hash = md.digest();

            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
        } catch (Exception e) {
            System.out.println(CommonUtils.getNowDate() + "  error: MD5 error" + str );
            return str;
//            throw new Exception("MD5加密出现错误，"+e.toString());
        }

        return hexString.toString();
    }

}
