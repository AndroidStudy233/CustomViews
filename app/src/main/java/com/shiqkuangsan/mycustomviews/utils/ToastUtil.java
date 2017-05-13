package com.shiqkuangsan.mycustomviews.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by shiqkuangsan on 2017/1/4.
 * <p>
 * ClassName: ToastUtil
 * Author: shiqkuangsan
 * Description: 吐司工具类
 */
public class ToastUtil {

    private static Toast toast;

    /**
     * 短吐司
     *
     * @param context 上下文
     * @param message 内容
     */
    public static void toastShort(Context context, String message) {
        if (toast == null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    /**
     * 短吐司
     *
     * @param context    上下文
     * @param resourceId 字符串id
     */
    public static void toastShort(Context context, int resourceId) {
        if (toast == null) {
            toast = Toast.makeText(context, resourceId, Toast.LENGTH_SHORT);
        } else {
            toast.setText(context.getString(resourceId));
        }
        toast.show();
    }

    /**
     * 长吐司
     *
     * @param context 上下文
     * @param message 内容
     */
    public static void toastLong(Context context, String message) {
        if (toast == null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    /**
     * 长吐司
     *
     * @param context    上下文
     * @param resourceId 字符串id
     */
    public static void toastLong(Context context, int resourceId) {
        if (toast == null) {
            toast = Toast.makeText(context, resourceId, Toast.LENGTH_LONG);
        } else {
            toast.setText(resourceId);
        }
        toast.show();
    }
}
