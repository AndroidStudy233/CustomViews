package com.shiqkuangsan.mycustomviews.utils;

import android.support.annotation.NonNull;
import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by shiqkuangsan on 2017/1/3.
 * <p>
 * author: shiqkuangsan
 * description: 加密工具类
 */
public class EncryptUtil {

    /**
     * 对字符串进行MD5加密
     *
     * @param str 字符串
     * @return 加密后的字符串
     */
    public static String md5Encryption(@NonNull String str) {
        return encrypt(str, "MD5");
    }

    /**
     * 对字符串进行SHA-1加密
     *
     * @param str 字符串
     * @return 加密后的字符串
     */
    public static String sha_1Encryption(@NonNull String str) {
        return encrypt(str, "SHA-1");
    }

    /**
     * 对字符串进行SHA-256加密
     *
     * @param str 字符串
     * @return 加密后的字符串
     */
    public static String sha_256Encryption(@NonNull String str) {
        return encrypt(str, "SHA-256");
    }

    /**
     * 对字符串进行Base64加密
     *
     * @param str 字符串
     * @return 加密后的字符串
     */
    public static String base64Encryption(@NonNull String str) {
        return Arrays.toString(Base64.encode(str.getBytes(), Base64.DEFAULT));
    }

    /**
     * 对字符串进行Base64加密
     *
     * @param str 字符串
     * @return 解密后的字符串
     */
    public static String base64Decrypt(@NonNull String str) {
        return Arrays.toString(Base64.decode(str.getBytes(), Base64.DEFAULT));
    }

    /**
     * 对字符串加密,加密算法可以使用MD5,SHA-1,SHA-256,默认使用SHA-256
     *
     * @param strSrc  要加密的字符串
     * @param encName 加密类型
     * @return 返回加密后的字符串
     */
    private static String encrypt(String strSrc, String encName) {
        MessageDigest md;
        String strDes = null;

        byte[] bt = strSrc.getBytes();
        try {
            if (encName == null || encName.equals("")) {
                encName = "SHA-256";
            }
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDes;
    }

    private static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp;
        for (byte byt : bts) {
            tmp = (Integer.toHexString(byt & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
}
