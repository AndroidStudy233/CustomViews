package qwy.anenda.com.baselib.utils;

import android.app.Application;
import android.widget.Toast;

/**
 * Created by zhuguidong on 2019/4/28.
 * ClassName: ToastUtils
 * Description: toast 提示 util
 */
public class ToastUtils {

    private static Toast toast;
    private static Application mApp;

    public static void init(Application app) {
        mApp = app;
    }

    /**
     * 短提示 by xlq
     *
     * @param content
     */
    public static void shortShowStr(String content) {
        if (content == null) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(mApp.getApplicationContext(), content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    /**
     * 常提示 by xlq
     *
     * @param content
     */
    public static void longShowStr(String content) {
        if (content == null) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(mApp.getApplicationContext(), content, Toast.LENGTH_LONG);
        } else {
            toast.setText(content);
        }
        toast.show();
    }


    public static void showToast(int resId, int errorCode) {
        if (toast == null) {
            toast = Toast.makeText(mApp.getApplicationContext(), errorCode, Toast.LENGTH_LONG);
        } else {
            toast.setText(errorCode);
        }
        toast.show();
    }

}
