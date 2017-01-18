package com.shiqkuangsan.mycustomviews.ui.custom.swipeback.app;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.shiqkuangsan.mycustomviews.ui.custom.swipeback.utils.FinishHelper;
import com.shiqkuangsan.mycustomviews.ui.custom.swipeback.utils.SharePreferenceUtil;
import com.shiqkuangsan.mycustomviews.ui.custom.swipeback.utils.StatusBarUtil;
import com.shiqkuangsan.mycustomviews.ui.custom.swipeback.utils.SwipeBackLayout;


/**
 * 自带右滑返回上一个界面的Activity
 * 使用:直接继承自SwipeBackActivity即可,用在项目中可以让你的BaseActivity集成这个
 * 禁用getSwipeBackLayout().setEnableGesture(false);
 * <p>
 * 5.0以下的版本滑动之前的界面会黑屏. 效果有限.暂且完美支持5.0+
 * <p>
 * 使用这个类需要注意的地方:
 * 1.你需要复制自定义控件SwipeBackLayout需要用的自定义属性申明(attrs)2个
 * 2.styles下面的SwipeBackLayout的几个样式
 * 3.SwipeBackActivityHelper中需要R.layout.swipeback_layout布局文件(根布局为SwipeBackLayout的空布局)
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
