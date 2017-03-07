package com.shiqkuangsan.mycustomviews.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by shiqkuangsan on 2016/9/28.
 *
 * @author shiqkaungsan
 * @summary 状态栏工具类, 主要用于沉浸式设置, 包含3个方法
 * 1. setStatusColor() 用于着色沉浸式
 * 2. setStatusTransparent() 用于背景沉浸式
 * 3. getStatusBarHeight() 获取状态栏高度
 * <p>
 * 5.0处理: 5.0以上由于新特性可以直接通过申请flag来设置状态栏的颜色
 * 4.4.4处理: 4.4.4以上通过申请flag使状态栏半透明,然后设置activity的根部局颜色配合fitsSystemWindows
 * 属性来达到颜色沉浸的效果.
 *
 * 使用:
 * 1.颜色沉浸,你写完activity布局,指定好根节点的background(一般就是白色)属性,最外层再加一层布局如LinearLayout,
 * 然后再Activity的onCreate()中直接调用setStatusColor(this, color);即可
 *
 * 2.背景沉浸,activity根布局设置background背景图属性,设置fitsSystemWindows-true,然后再加一层LinearLayout之类的,
 * onCreate()中直接调用setStatusTransparent(this, false);
 * 参考demo中的ColorImmerseActivity / BgImmerseActivity
 */
public class MyStatusBarUtil {

    private static final int DEFAULT_STATUS_BAR_ALPHA = 112;

    /**
     * 设置状态栏纯色 不加半透明效果
     *
     * @param activity 需要设置的 activity
     * @param color    状态栏颜色值
     */
    public static void setStatusColor(Activity activity, int color) {
        setColor(activity, color, 0);
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity       需要设置的activity
     * @param color          状态栏颜色值
     * @param statusBarAlpha 状态栏透明度,预留值,可以附加半透明效果
     */
    private static void setColor(Activity activity, int color, int statusBarAlpha) {
        // 5.0以上的处理
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            activity.getWindow().setStatusBarColor(calculateStatusColor(color, statusBarAlpha));
            activity.getWindow().setStatusBarColor(color);

            // 4.4.4的处理. 有的手机拿到的rootView没有孩子  要通过getwindws.getdecorView获取.
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup rootView = (ViewGroup) activity.getWindow().findViewById(android.R.id.content);
            View childAt = rootView.getChildAt(0);// activity根布局
            childAt.setBackgroundColor(color);
            setRootView(activity);
        }
    }

    /**
     * 设置状态栏全透明
     * <p>
     * 适用于图片作为背景的界面,此时图片填充到状态栏
     *
     * @param activity    需要设置的activity
     * @param setRootView 是否需要设置根布局适配.测试界面使用.实际一般给false
     */
    public static void setStatusTransparent(Activity activity, boolean setRootView) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        transparentStatusBar(activity);
        if (setRootView)
            setRootView(activity);
    }

    /**
     * 设置根布局参数,这样就不需要在activity的布局文件中设置了
     */
    private static void setRootView(Activity activity) {
        ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        rootView.setFitsSystemWindows(true);
        rootView.setClipToPadding(true);
    }

    /**
     * 使状态栏透明
     */
    private static void transparentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 半透明导航栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup rootView = (ViewGroup) activity.getWindow().findViewById(android.R.id.content);
            View childAt = rootView.getChildAt(0);
            childAt.setBackgroundColor(Color.TRANSPARENT);
//            setRootView(activity);    // 改为用户手动设置
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * 计算状态栏颜色,根据不同透明度和颜色算出应该显示的颜色,百度的一套算法
     *
     * @param color color值
     * @param alpha alpha值
     * @return 最终的状态栏颜色
     */
    private static int calculateStatusColor(int color, int alpha) {
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
