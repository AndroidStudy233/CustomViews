package com.shiqkuangsan.cityselector.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 吐司小工具类
 */
public class ToastUtil {

    private static Toast mToast;

    /**
     * 显示吐司
     *
     * @param context 上下文
     * @param message 内容
     */
    public static void showToast(final Context context, final String message) {
        if (mToast == null) {
            mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     * 显示吐司
     *
     * @param context 上下文
     * @param messageResId 内容id
     */
    public static void showToast(final Context context, final int messageResId) {
        if (mToast == null) {
            mToast = Toast.makeText(context, messageResId, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(messageResId);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }
}
