package com.shiqkuangsan.mycustomviews.utils;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by shiqkuangsan on 2017/1/4.
 * <p>
 * ClassName: InputMethodUtil
 * Author: shiqkuangsan
 * Description: 输入法工具类
 */
public class InputMethodUtil {

    /**
     * 为给定的编辑器开启软键盘
     *
     * @param context  上下文
     * @param editText 给定的编辑器
     */
    public static void openSoftKeyboard(Context context, EditText editText) {
        editText.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        Editable editable = editText.getEditableText();
        Selection.setSelection(editable, editable.toString().length());
    }


    /**
     * 关闭软键盘
     *
     * @param activity Activity
     */
    public static void closeSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果软键盘已经开启
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /**
     * 切换软键盘的状态
     *
     * @param context 上下文
     */
    public static void toggleSoftKeyboardState(Context context) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(
                InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
    }


    /**
     * 判断隐藏软键盘是否弹出,弹出就隐藏
     *
     * @param mActivity Activity
     * @return 弹出的软键盘被隐藏时返回true
     */
    public boolean keyBoxIsShow(Activity mActivity) {
        if (mActivity.getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED) {
            mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            return true;
        } else {
            return false;
        }
    }
}
