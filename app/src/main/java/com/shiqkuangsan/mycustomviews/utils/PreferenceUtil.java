package com.shiqkuangsan.mycustomviews.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by shiqkuangsan on 2017/1/4.
 * <p>
 * ClassName: PreferenceUtil
 * Author: shiqkuangsan
 * Description: preference工具类,用的是默认的保存路径 包名+"_preferences".
 */
public class PreferenceUtil {


    public static void writeInt(Context context, String k, int v) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(k, v);
        editor.apply();
    }

    public static void writeBoolean(Context context, String k, boolean v) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(k, v);
        editor.apply();
    }

    public static void writeString(Context context, String k, String v) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(k, v);
        editor.apply();
    }

    public static int readInt(Context context, String k) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(k, 0);
    }

    public static int readInt(Context context, String k, int defv) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(k, defv);
    }

    public static boolean readBoolean(Context context, String k) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(k, false);
    }

    public static boolean readBoolean(Context context, String k, boolean defBool) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(k, defBool);
    }

    public static String readString(Context context, String k) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(k, (String) null);
    }

    public static String readString(Context context, String k, String defV) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(k, defV);
    }

    public static void remove(Context context, String k) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(k);
        editor.apply();
    }

    public static void clean(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
