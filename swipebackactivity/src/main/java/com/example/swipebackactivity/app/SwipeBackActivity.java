package com.example.swipebackactivity.app;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.swipebackactivity.FinishHelper;
import com.example.swipebackactivity.SwipeBackLayout;
import com.example.swipebackactivity.Utils.SharePreferenceUtil;
import com.example.swipebackactivity.Utils.StatusBarUtil;

/**
 * 自带右滑返回上一个界面的Activity
 * 使用:直接继承自SwipeBackActivity即可,用在项目中可以让你的BaseActivity集成这个
 * 控制是否支持手势getSwipeBackLayout().setEnableGesture(false);
 *
 * 5.0以下的版本滑动之前的界面会黑屏. 暂且支持5.0+
 */
public class SwipeBackActivity extends AppCompatActivity implements SwipeBackActivityBase {

    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        FinishHelper.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    /**
     * 初始化主题风格,主要是颜色的设置
     *
     * @param floatingActionButton 浮动按钮
     * @param toolbar              ToolBar
     * @param needChangeToolbar    是否需要改变ToolBar
     * @param needChangeStatusBar  是否需要改变状态栏
     * @param drawerLayout         DrawerLayout
     * @return 返回传入的颜色
     */
    public int initMainStyle(FloatingActionButton floatingActionButton, Toolbar toolbar,
                             boolean needChangeToolbar, boolean needChangeStatusBar, DrawerLayout drawerLayout, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (SharePreferenceUtil.needChangeNavColor(this))
                getWindow().setNavigationBarColor(color);
            else
                getWindow().setNavigationBarColor(Color.BLACK);
        }
        if (floatingActionButton != null)
            floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(color));
        if (needChangeToolbar)
            toolbar.setBackgroundColor(color);
        if (needChangeStatusBar) {
            if (SharePreferenceUtil.isImmersiveMode(this))
                StatusBarUtil.setColorNoTranslucent(this, color);
            else
                StatusBarUtil.setColor(this, color);
        }
        if (drawerLayout != null) {
            if (SharePreferenceUtil.isImmersiveMode(this))
                StatusBarUtil.setColorNoTranslucentForDrawerLayout(this, drawerLayout, color);
            else
                StatusBarUtil.setColorForDrawerLayout(this, drawerLayout, color);
        }
        return color;
    }
}
