package com.example.swipebackactivity.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * 用SharePreference来保存相关配置,灵活点
 */
public class SharePreferenceUtil {

    public static final String NAME_SHAREDPREFERENCE = "library_shiqkuangsan";

    public static boolean isImmersiveMode(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean("config_statusbar", true);
    }

    public static void setImmersiveMode(Context context, boolean flag) {
        SharedPreferences.Editor preferences = PreferenceManager.getDefaultSharedPreferences(context).edit();
        preferences.putBoolean("config_statusbar", flag);
        preferences.apply();
    }


    public static boolean needChangeNavColor(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean("config_navigation", true);
    }

    public static void setNeedChangeNavColor(Context context, boolean flag) {
        SharedPreferences.Editor preferences = PreferenceManager.getDefaultSharedPreferences(context).edit();
        preferences.putBoolean("config_navigation", flag);
        preferences.apply();
    }
}
