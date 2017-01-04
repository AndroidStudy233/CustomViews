package com.shiqkuangsan.mycustomviews.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiqkuangsan on 2017/1/4.
 * <p>
 * Author: shiqkuangsan
 * Description: SD卡操作相关类.
 */
public class SDCradUtil {

    /**
     * 获取SD卡的状态
     */
    public static String getState() {
        return Environment.getExternalStorageState();
    }

    /**
     * SD卡是否可用
     *
     * @return 只有当SD卡已经安装并且准备好了才返回true
     */
    public static boolean isAvailable() {
        return getState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡的根目录
     *
     * @return null:SD卡不可用
     */
    public static File getRootDirectory() {
        return isAvailable() ? Environment.getExternalStorageDirectory() : null;
    }

    /**
     * 获取SD卡的根路径
     *
     * @return null:SD卡不可用
     */
    public static String getRootPath() {
        File rootDirectory = getRootDirectory();
        return rootDirectory == null ? null : rootDirectory.getPath();
    }

    /**
     * 获取sd卡的路径
     *
     * @return null:不存在SD卡
     */
    public static String getSDPath() {
        if (isAvailable()) {
            File sdDir = Environment.getExternalStorageDirectory();
            return sdDir.toString();
        } else
            return null;
    }

    /**
     * 获取SD卡可用空间
     *
     * @return long值
     */
    public static long getSDFreeSize() {
        File file = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(file.getPath());
        long blockSize = (long) sf.getBlockSize();
        long freeBlocks = (long) sf.getAvailableBlocks();
        return freeBlocks * blockSize / 1024L / 1024L;
    }

    /**
     * 获取SD卡总共空间
     *
     * @return long值
     */
    public static long getSDAllSize() {
        File file = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(file.getPath());
        long blockSize = (long) sf.getBlockSize();
        long allBlocks = (long) sf.getBlockCount();
        return allBlocks * blockSize / 1024L / 1024L;
    }

}
