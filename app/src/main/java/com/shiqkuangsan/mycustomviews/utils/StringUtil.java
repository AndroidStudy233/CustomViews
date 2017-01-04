package com.shiqkuangsan.mycustomviews.utils;

import java.util.Locale;

import static com.loc.f.i;

/**
 * Created by shiqkuangsan on 2017/1/3.
 * <p>
 * author: shiqkuangsan
 * description: 字符串常用方法功工具类
 */
public class StringUtil {

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串
     * 若输入字符串为null或空字符串，返回true
     *
     * @param input 需要验证的字符串
     * @return true-空,fasle-不为空
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否全是数字
     *
     * @param str 字符串
     * @return true-全是
     */
    public static boolean isAllDigital(String str) {
        char[] array = str.toCharArray();
        boolean result = true;
        for (char chr : array) {
            if (!Character.isDigit(chr)) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * 将给定字符串中给定的区域的字符转换成小写
     *
     * @param str        给定字符串中
     * @param beginIndex 开始索引（包括）
     * @param endIndex   结束索引（不包括）
     * @return 新的字符串
     */
    public static String toLowerCase(String str, int beginIndex, int endIndex) {
        return str.replaceFirst(str.substring(beginIndex, endIndex),
                str.substring(beginIndex, endIndex)
                        .toLowerCase(Locale.getDefault()));
    }


    /**
     * 将给定字符串中给定的区域的字符转换成大写
     *
     * @param str        给定字符串中
     * @param beginIndex 开始索引（包括）
     * @param endIndex   结束索引（不包括）
     * @return 新的字符串
     */
    public static String toUpperCase(String str, int beginIndex, int endIndex) {
        return str.replaceFirst(str.substring(beginIndex, endIndex),
                str.substring(beginIndex, endIndex)
                        .toUpperCase(Locale.getDefault()));
    }


}
