package com.shiqkuangsan.mycustomviews.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by shiqkuangsan on 2017/1/3.
 * <p>
 * author: shiqkuangsan
 * description: 加密工具类
 */
public class EncodeUtil {

    private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 得到参数加密后的MD5值
     *
     * @param inStr 需要加密的字符串
     * @return 返回32byte MD5值
     */
    public static String getMD5(String inStr) {
        byte[] inStrBytes = inStr.getBytes();
        try {
            MessageDigest MD = MessageDigest.getInstance("MD5");
            MD.update(inStrBytes);
            byte[] mdByte = MD.digest();
            char[] str = new char[mdByte.length * 2];
            int k = 0;
            for (byte temp : mdByte) {
                str[k++] = hexDigits[temp >>> 4 & 0xf];
                str[k++] = hexDigits[temp & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对字符串加密,加密算法可以使用MD5,SHA-1,SHA-256,默认使用SHA-256
     *
     * @param strSrc  要加密的字符串
     * @param encName 加密类型
     * @return 返回加密后的字符串
     */
    public static String SHA256Encode(String strSrc, String encName) {
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
