package com.shiqkuangsan.mycustomviews.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by shiqkuangsan on 2017/1/4.
 * <p>
 * ClassName: UIUitl
 * Author: shiqkuangsan
 * Description: 关于界面常用方法的工具类
 */
public class UIUitl {

    private static int[] deviceWidthHeight = new int[2];

    /**
     * 获取设备的屏幕大小-px值
     *
     * @param context 上下文
     * @return 数组, pos[0]-宽,pos[1]-高
     */
    public static int[] getDeviceSizepx(Context context) {
        if ((deviceWidthHeight[0] == 0) && (deviceWidthHeight[1] == 0)) {
            DisplayMetrics metrics = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay()
                    .getMetrics(metrics);

            deviceWidthHeight[0] = metrics.widthPixels;
            deviceWidthHeight[1] = metrics.heightPixels;
        }
        return deviceWidthHeight;
    }

    /**
     * 获取设备的屏幕大小
     *
     * @param context 上下文
     * @return Point, x-宽, y-高
     */
    public static Point getScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
            return new Point(display.getWidth(), display.getHeight());
        } else {
            Point point = new Point();
            display.getSize(point);
            return point;
        }
    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     *
     * @return 像素值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  //获取屏幕的密度
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @return dp值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
