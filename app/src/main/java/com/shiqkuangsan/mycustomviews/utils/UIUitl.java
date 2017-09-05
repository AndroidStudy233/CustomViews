package com.shiqkuangsan.mycustomviews.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
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

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度px值
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        // 获得状态栏高度
        int statusHeightId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(statusHeightId);
    }

    /**
     * 获取导航栏高度
     *
     * @param context context
     * @return 导航栏高度px值
     */
    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        // 获得导航栏高度
        int naviShowId = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        boolean naviShow = resources.getBoolean(naviShowId);
        if (naviShow) {
            int naviHeightId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            return resources.getDimensionPixelSize(naviHeightId);
        }
        return -1;
    }


    /**
     * 计算状态栏颜色, 根据给定的透明度和颜色算出应该显示的颜色
     *
     * @param color color值
     * @param alpha alpha值 0-100 ↑
     * @return 最终的状态栏颜色
     */
    public static int calculateColorWithOpacity(int color, int alpha) {
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }
}
