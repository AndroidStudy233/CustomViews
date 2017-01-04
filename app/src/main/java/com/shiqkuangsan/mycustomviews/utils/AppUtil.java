package com.shiqkuangsan.mycustomviews.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by shiqkuangsan on 2017/1/4.
 * <p>
 * ClassName: AppUtil
 * Author: shiqkuangsan
 * Description: 关于app的相关操作Util
 */
public class AppUtil {
    /**
     * PackageInfo
     */
    private static PackageInfo packageInfo;

    /**
     * 获取指定App版本VersionName
     *
     * @param context 上下文
     * @return VersionName的字符串值
     */
    public static String getAppVersionName(Context context, String packageName) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "Unknown";
        }
    }

    /**
     * 获取指定App的VersionCode
     *
     * @param context     上下文
     * @param packageName 包名
     * @return VersionCode的int值
     */
    public static int getAppVersionCode(Context context, String packageName) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
    }

    /**
     * 获取当前App的版本
     *
     * @param context 上下文
     * @return VersionName的字符串值
     */
    public static String getAppVersionName(Context context) {
        if (packageInfo != null) {
            return packageInfo.versionName;
        }
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "Unknown";
        }
    }

    /**
     * 获取当前App的VersionCode
     *
     * @param context 上下文
     * @return VersionCode的int值
     */
    public static int getAppVersionCode(Context context) {
        if (packageInfo != null) {
            return packageInfo.versionCode;
        }
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
    }

    /**
     * 获取应用运行的最大内存
     *
     * @return 最大内存
     */
    public static long getMaxMemory() {

        return Runtime.getRuntime().maxMemory() / 1024;
    }

    /**
     * 安装apk
     *
     * @param context 上下文
     * @param file    APK文件
     */
    public static void installApk(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }


    /**
     * 安装apk
     *
     * @param context 上下文
     * @param file    APK文件uri
     */
    public static void installApk(Context context, Uri file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(file, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 检测服务是否运行
     *
     * @param context   上下文
     * @param className 类名
     * @return 是否运行的状态
     */
    public static boolean isServiceRunning(Context context, String className) {
        boolean isRunning = false;
        ActivityManager activityManager
                = (ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> servicesList
                = activityManager.getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo si : servicesList) {
            if (className.equals(si.service.getClassName())) {
                isRunning = true;
            }
        }
        return isRunning;
    }


    /**
     * 停止运行服务
     *
     * @param context   上下文
     * @param className 类名
     * @return 是否执行成功
     */
    public static boolean stopRunningService(Context context, String className) {
        Intent intent_service = null;
        boolean ret = false;
        try {
            intent_service = new Intent(context, Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (intent_service != null) {
            ret = context.stopService(intent_service);
        }
        return ret;
    }


    /**
     * 得到CPU核心数
     *
     * @return CPU核心数
     */
    public static int getNumCores() {
        try {
            File dir = new File("/sys/devices/system/cpu/");
            File[] files = dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                        return true;
                    }
                    return false;
                }
            });
            return files.length;
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * 卸载apk
     *
     * @param context     上下文
     * @param packageName 包名
     */
    public static void uninstallApk(Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        Uri packageURI = Uri.parse("package:" + packageName);
        intent.setData(packageURI);
        context.startActivity(intent);
    }


    /**
     * 工程配置文件元数据读取
     *
     * @param context 上下文
     * @return 封装了MetaData的Bundle
     */
    private static Bundle getMetaData(Context context) {
        Bundle value = null;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            value = applicationInfo.metaData;
        } catch (PackageManager.NameNotFoundException e) {
            // none
        }
        return value;
    }

    /**
     * 根据指定的键读取元数据
     *
     * @param context      上下文
     * @param keyName      键
     * @param defaultValue 默认值
     * @return 键所对应的元数据字符串值, 或者null
     */
    public static String getStringMeta(Context context, String keyName, String defaultValue) {
        Bundle metaData = getMetaData(context);
        if (metaData != null) {
            return metaData.getString(keyName, defaultValue);
        } else {
            return null;
        }
    }

    /**
     * 根据指定的键读取元数据,不指定默认值
     *
     * @param context 上下文
     * @param keyName 键
     * @return 键所对应的元数据字符串值
     */
    public static String getStringMeta(Context context, String keyName) {
        return getStringMeta(context, keyName, null);
    }

    /**
     * 读取元数据
     *
     * @param context      上下文
     * @param keyName      键
     * @param defaultValue 默认值
     * @return 键所对应的元数据字符序列值, 或者null
     */
    public static CharSequence getCharSequenceMeta(Context context, String keyName, CharSequence defaultValue) {
        Bundle metaData = getMetaData(context);
        if (metaData != null) {
            return metaData.getCharSequence(keyName, defaultValue);
        } else {
            return null;
        }
    }

    /**
     * 读取元数据,不指定默认值
     *
     * @param context 上下文
     * @param keyName 键
     * @return 键所对应的元数据字符序列值
     */
    public static CharSequence getCharSequenceMeta(Context context, String keyName) {
        return getCharSequenceMeta(context, keyName, null);
    }

    /**
     * 读取元数据
     *
     * @param context      上下文
     * @param keyName      键
     * @param defaultValue 默认值
     * @return 键所对应的元数据字符值
     */
    public static char getCharMeta(Context context, String keyName, char defaultValue) {
        Bundle metaData = getMetaData(context);
        if (metaData != null) {
            return metaData.getChar(keyName, defaultValue);
        } else {
            return 0;
        }
    }

    /**
     * 读取元数据
     *
     * @param context 上下文
     * @param keyName 键
     * @return 键所对应的元数据字符值
     */
    public static char getCharMeta(Context context, String keyName) {
        return getCharMeta(context, keyName, (char) 0);
    }

    /**
     * 读取元数据
     *
     * @param context      上下文
     * @param keyName      键
     * @param defaultValue 默认值
     * @return 键所对应的元数据字节值
     */
    public static byte getByteMeta(Context context, String keyName, byte defaultValue) {
        Bundle metaData = getMetaData(context);
        if (metaData != null) {
            return metaData.getByte(keyName, defaultValue);
        } else {
            return 0;
        }
    }

    /**
     * 读取元数据
     *
     * @param context 上下文
     * @param keyName 键
     * @return 键所对应的元数据字节值
     */
    public static byte getByteMeta(Context context, String keyName) {
        return getByteMeta(context, keyName, (byte) 0);
    }

    /**
     * 读取元数据
     *
     * @param context      上下文
     * @param keyName      键
     * @param defaultValue 默认值
     * @return 键所对应的元数据布尔值
     */
    public static boolean getBooleanMeta(Context context, String keyName, boolean defaultValue) {
        Bundle metaData = getMetaData(context);
        if (metaData != null) {
            return metaData.getBoolean(keyName, defaultValue);
        } else {
            return defaultValue;
        }
    }

    /**
     * 读取元数据
     *
     * @param context 上下文
     * @param keyName 键
     * @return 键所对应的元数据布尔值
     */
    public static boolean getBooleanMeta(Context context, String keyName) {
        return getBooleanMeta(context, keyName, false);
    }

    /**
     * 读取元数据
     *
     * @param context      上下文
     * @param keyName      键
     * @param defaultValue 默认值
     * @return 键所对应的元数据int值
     */
    public static int getIntMeta(Context context, String keyName, int defaultValue) {
        Bundle metaData = getMetaData(context);
        if (metaData != null) {
            return metaData.getInt(keyName, defaultValue);
        } else {
            return defaultValue;
        }
    }

    /**
     * 读取元数据
     *
     * @param context 上下文
     * @param keyName 键
     * @return 键所对应的元数据int值
     */
    public static int getIntMeta(Context context, String keyName) {
        return getIntMeta(context, keyName, 0);
    }

    /**
     * 读取元数据
     *
     * @param context      上下文
     * @param keyName      键
     * @param defaultValue 默认值
     * @return 键所对应的元数据long值
     */
    public static long getLongMeta(Context context, String keyName, long defaultValue) {
        Bundle metaData = getMetaData(context);
        if (metaData != null) {
            return metaData.getLong(keyName, defaultValue);
        } else {
            return defaultValue;
        }
    }

    /**
     * 读取元数据
     *
     * @param context 上下文
     * @param keyName 键
     * @return 键所对应的元数据long值
     */
    public static long getLongMeta(Context context, String keyName) {
        return getLongMeta(context, keyName, 0L);
    }

    /**
     * 读取元数据
     *
     * @param context      上下文
     * @param keyName      键
     * @param defaultValue 默认值
     * @return 键所对应的元数据float值
     */
    public static float getFloatMeta(Context context, String keyName, float defaultValue) {
        Bundle metaData = getMetaData(context);
        if (metaData != null) {
            return metaData.getFloat(keyName, defaultValue);
        } else {
            return defaultValue;
        }
    }

    /**
     * 读取元数据
     *
     * @param context 上下文
     * @param keyName 键
     * @return 键所对应的元数据float值
     */
    public static float getFloatMeta(Context context, String keyName) {
        return getFloatMeta(context, keyName, .0f);
    }


    /**
     * 读取元数据
     *
     * @param context      上下文
     * @param keyName      键
     * @param defaultValue 默认值
     * @return 键所对应的元数据double值
     */
    public static double getDoubleMeta(Context context, String keyName, double defaultValue) {
        Bundle metaData = getMetaData(context);
        if (metaData != null) {
            return metaData.getDouble(keyName, defaultValue);
        } else {
            return defaultValue;
        }
    }

    /**
     * 读取元数据
     *
     * @param context 上下文
     * @param keyName 键
     * @return 键所对应的元数据double值
     */
    public static double getDoubleMeta(Context context, String keyName) {
        return getDoubleMeta(context, keyName, .0);
    }

}
