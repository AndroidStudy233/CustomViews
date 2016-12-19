package com.shiqkuangsan.mycustomviews.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * (一般用于网络请求)从输入流中获取数据的数据的Utils
 *
 * @author shiqkuangsan
 */
public class StreamUtil {

    /**
     * 把流读取成字符串
     * @param is 流
     * @return 字符串
     */
    public static String getDataFromInputStream(InputStream is) {
        String data = null;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len;
        byte[] bys = new byte[1024];

        try {
            while ((len = is.read(bys)) > 0) {
                bos.write(bys, 0, len);
            }
            data = bos.toString();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;

    }
}
