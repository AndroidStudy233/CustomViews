package com.shiqkuangsan.mycustomviews.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by shiqkuangsan on 2017/1/3.
 * <p>
 * author: shiqkuangsan
 * description: 查看网络状态工具类
 */
public class NetworkUtil {

    /**
     * 判断当前网络是否可用
     *
     * @return true-可用，false-不可用
     */
    public static boolean isNetWorkConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        return networkinfo != null && networkinfo.isAvailable();
    }

    /**
     * 判断网络类型
     *
     * @param context 上下文
     * @return true-wifi状态
     */
    public static boolean isWifi(Context context) {
        boolean isWifi = false;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isAvailable()) {
            if (networkinfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifi = true;
            }
        }
        return isWifi;
    }
}
