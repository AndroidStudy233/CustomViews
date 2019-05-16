/*
 * Copyright (c) 2014,KJFrameForAndroid Open Source Project,张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package qwy.anenda.com.baselib.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import qwy.anenda.com.baselib.common.Constant;


public class PreferenceUtil {

    private static Application mApp;
    public static String File_Pref_Config = Constant.CONFIG;

    public static void init(Application app) {
        mApp = app;
    }

    public static void write(String k, int v) {
        SharedPreferences preference = mApp.getApplicationContext().getSharedPreferences(File_Pref_Config, Context.MODE_PRIVATE);
        Editor editor = preference.edit();
        editor.putInt(k, v);
        editor.apply();
    }

    public static void write(String k, long v) {
        SharedPreferences preference = mApp.getApplicationContext().getSharedPreferences(File_Pref_Config, Context.MODE_PRIVATE);
        Editor editor = preference.edit();
        editor.putLong(k, v);
        editor.apply();
    }

    public static void write(String k, boolean v) {
        SharedPreferences preference = mApp.getApplicationContext().getSharedPreferences(File_Pref_Config, Context.MODE_PRIVATE);
        Editor editor = preference.edit();
        editor.putBoolean(k, v);
        editor.apply();
    }

    public static void write(String k, String v) {
        SharedPreferences preference = mApp.getApplicationContext().getSharedPreferences(File_Pref_Config, Context.MODE_PRIVATE);
        Editor editor = preference.edit();
        editor.putString(k, v);
        editor.apply();
    }


    public static int readInt(String k) {
        SharedPreferences preference = mApp.getApplicationContext().getSharedPreferences(File_Pref_Config, Context.MODE_PRIVATE);
        return preference.getInt(k, 0);
    }

    public static int readInt(String k, int defv) {
        SharedPreferences preference = mApp.getApplicationContext().getSharedPreferences(File_Pref_Config, Context.MODE_PRIVATE);
        return preference.getInt(k, defv);
    }

    public static long readLong(String k) {
        SharedPreferences preference = mApp.getApplicationContext().getSharedPreferences(File_Pref_Config, Context.MODE_PRIVATE);
        return preference.getLong(k, 0);
    }

    public static long readLong(String k, long defv) {
        SharedPreferences preference = mApp.getApplicationContext().getSharedPreferences(File_Pref_Config, Context.MODE_PRIVATE);
        return preference.getLong(k, defv);
    }

    public static boolean readBoolean(String k) {
        SharedPreferences preference = mApp.getApplicationContext().getSharedPreferences(File_Pref_Config, Context.MODE_PRIVATE);
        return preference.getBoolean(k, false);
    }

    public static boolean readBoolean(String k, boolean defBool) {
        SharedPreferences preference = mApp.getApplicationContext().getSharedPreferences(File_Pref_Config, Context.MODE_PRIVATE);
        return preference.getBoolean(k, defBool);
    }

    public static String readString(String k) {
        SharedPreferences preference = mApp.getApplicationContext().getSharedPreferences(File_Pref_Config, Context.MODE_PRIVATE);
        return preference.getString(k, null);
    }

    public static String readString(String k, String defV) {
        SharedPreferences preference = mApp.getApplicationContext().getSharedPreferences(File_Pref_Config, Context.MODE_PRIVATE);
        return preference.getString(k, defV);
    }

    public static void remove(String k) {
        SharedPreferences preference = mApp.getApplicationContext().getSharedPreferences(File_Pref_Config, Context.MODE_PRIVATE);
        Editor editor = preference.edit();
        editor.remove(k);
        editor.apply();
    }

    public static void clean(Context cxt, String fileName) {
        SharedPreferences preference = cxt.getSharedPreferences(File_Pref_Config, Context.MODE_PRIVATE);
        Editor editor = preference.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
     * 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
     *
     * @param object 待加密的转换为String的对象
     * @return String   加密后的String
     */
    private static String Object2String(Object object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            String string = new String(Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
            objectOutputStream.close();
            return string;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用Base64解密String，返回Object对象
     *
     * @param objectString 待解密的String
     * @return object      解密后的object
     */
    private static Object String2Object(String objectString) {
        byte[] mobileBytes = Base64.decode(objectString.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mobileBytes);
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object object = objectInputStream.readObject();
            objectInputStream.close();
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 使用SharedPreference保存对象
     *
     * @param key        储存对象的key
     * @param saveObject 储存的对象
     */
    public static void write(String key, Object saveObject) {
        SharedPreferences sharedPreferences = mApp.getApplicationContext().getSharedPreferences(File_Pref_Config, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        String string = Object2String(saveObject);
        editor.putString(key, string);
        editor.apply();
    }

    /**
     * 获取SharedPreference保存的对象
     *
     * @param key 储存对象的key
     * @return object 返回根据key得到的对象
     */
    public static Object readObject(String key) {
        SharedPreferences sharedPreferences = mApp.getApplicationContext().getSharedPreferences(File_Pref_Config, Context.MODE_PRIVATE);
        String string = sharedPreferences.getString(key, null);
        if (string != null) {
            Object object = String2Object(string);
            return object;
        } else {
            return null;
        }
    }
}
