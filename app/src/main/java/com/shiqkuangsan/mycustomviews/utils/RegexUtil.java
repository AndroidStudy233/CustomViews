package com.shiqkuangsan.mycustomviews.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shiqkuangsan on 2017/1/5.
 * <p>
 * ClassName: RegexUtil
 * Author: shiqkuangsan
 * Description: 正则工具类
 */
public class RegexUtil {

    /**
     * 匹配全网IP的正则表达式
     */
    private static final String IP_REGEX = "^((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))$";

    /**
     * 匹配手机号码的正则表达式
     * <br>支持130——139、150——153、155——159、180、183、185、186、188、189号段
     */
    private static final String PHONE_NUMBER_REGEX = "^1{1}(3{1}\\d{1}|5{1}[012356789]{1}|8{1}[035689]{1})\\d{8}$";

    /**
     * 匹配邮箱的正则表达式
     * <br>"www."可省略不写
     */
    private static final String EMAIL_REGEX = "^(www\\.)?\\w+@\\w+(\\.\\w+)+$";

    /**
     * 匹配汉子的正则表达式，个数限制为一个或多个
     */
    private static final String CHINESE_REGEX = "^[\u4e00-\u9f5a]+$";

    /**
     * 匹配正整数的正则表达式，个数限制为一个或多个
     */
    private static final String POSITIVE_INTEGER_REGEX = "^\\d+$";

    /**
     * 匹配身份证号的正则表达式
     */
    private static final String ID_CARD = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";

    /**
     * 匹配邮编的正则表达式
     */
    private static final String ZIP_CODE = "^\\d{6}$";

    /**
     * 匹配URL的正则表达式
     */
    private static final String URL = "^(([hH][tT]{2}[pP][sS]?)|([fF][tT][pP]))\\:\\/\\/[wW]{3}\\.[\\w-]+\\.\\w{2,4}(\\/.*)?$";

    /**
     * 匹配给定的字符串是否是一个邮箱账号，"www."可省略不写
     *
     * @param string 给定的字符串
     * @return true：是
     */
    public static boolean isEmail(String string) {
        return string.matches(EMAIL_REGEX);
    }

    /**
     * 匹配给定的字符串是否是一个手机号码，支持130——139、150——153、155——159、180、183、185、186、188、189号段
     *
     * @param string 给定的字符串
     * @return true：是
     */
    public static boolean isMobilePhoneNumber(String string) {
        return string.matches(PHONE_NUMBER_REGEX);
    }

    /**
     * 匹配给定的字符串是否是一个全网IP
     *
     * @param string 给定的字符串
     * @return true：是
     */
    public static boolean isIp(String string) {
        return string.matches(IP_REGEX);
    }

    /**
     * 匹配给定的字符串是否全部由汉子组成
     *
     * @param string 给定的字符串
     * @return true：是
     */
    public static boolean isChinese(String string) {
        return string.matches(CHINESE_REGEX);
    }

    /**
     * 验证给定的字符串是否全部由正整数组成
     *
     * @param string 给定的字符串
     * @return true：是
     */
    public static boolean isPositiveInteger(String string) {
        return string.matches(POSITIVE_INTEGER_REGEX);
    }

    /**
     * 验证给定的字符串是否是身份证号
     *
     * @param string 给定的字符串
     * @return 是
     */
    public static boolean isIdCard(String string) {
        return string.matches(ID_CARD);
    }

    /**
     * 验证给定的字符串是否是邮编
     *
     * @param string 给定的字符串
     * @return 是
     */
    public static boolean isZipCode(String string) {
        return string.matches(ZIP_CODE);
    }

    /**
     * 验证给定的字符串是否是URL，仅支持http、https、ftp
     *
     * @param string 给定的字符串
     * @return 是
     */
    public static boolean isURL(String string) {
        return string.matches(URL);
    }


    /**
     * 验证密码只能输入字母和数字的特殊字符,这个返回的是过滤之后的字符串
     *
     * @param pre 用户输入的字符串
     * @return 过滤之后的字符串
     */
    public static String checkPassWordPre(String pre) {
        try {
            // 只允许字母、数字和汉字
            String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(pre);
            return m.replaceAll("").trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 只能输入字母和汉字 这个返回的是过滤之后的字符串
     *
     * @param pre 用户输入的字符串
     * @return 过滤之后的字符串
     */
    public static String checkInputPre(String pre) {
        try {
            String regEx = "[^a-zA-Z\u4E00-\u9FA5]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(pre);
            return m.replaceAll("").trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
