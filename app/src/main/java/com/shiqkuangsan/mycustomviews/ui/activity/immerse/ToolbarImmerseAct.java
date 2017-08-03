package com.shiqkuangsan.mycustomviews.ui.activity.immerse;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.utils.ToastUtil;
import com.shiqkuangsan.mycustomviews.utils.UIUitl;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import static com.shiqkuangsan.mycustomviews.utils.UIUitl.calculateColorWithOpacity;

/**
 * Created by shiqkuangsan on 2017/8/2. <p>
 * ClassName: ToolbarImmerseAct <p>
 * Author: shiqkuangsan <p>
 * Description: 纯色沉浸、半透明、全透明
 */
public class ToolbarImmerseAct extends AppCompatActivity {

    @ViewInject(R.id.llroot_toolbar_immerse)
    LinearLayout rootLayout;
    @ViewInject(R.id.toolbar_colorimmerse1)
    Toolbar toolbar;
    @ViewInject(R.id.btn_immerse_red1)
    Button red;
    @ViewInject(R.id.btn_immerse_green1)
    Button green;
    @ViewInject(R.id.btn_immerse_blue1)
    Button blue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_immerse);
        x.view().inject(this);

        initToolbar();
    }

    private void initToolbar() {
        toolbar.setTitleTextAppearance(this, R.style.toolbarTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Event(value = {R.id.btn_immerse_red1, R.id.btn_immerse_green1, R.id.btn_immerse_blue1,
            R.id.btn_immerse_translucent1, R.id.btn_immerse_transparent1})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_immerse_red1:
                setToolbarColor(R.color.red);
                setStatusBarColor(R.color.red);
                setRootBackground(-1);
                break;
            case R.id.btn_immerse_green1:
                setToolbarColor(R.color.green);
                setStatusBarColor(R.color.green);
                setRootBackground(-1);
                break;
            case R.id.btn_immerse_blue1:
                setToolbarColor(R.color.blue);
                setStatusBarColor(R.color.blue, 0);
                setRootBackground(-1);
                break;
            case R.id.btn_immerse_translucent1:
                setToolbarColor(R.color.transparent);
                translucentBar();
                setRootBackground(R.mipmap.img_background_anime3);
                // 半透明效果是修改了根布局的fitSystemWindows属性. 这时候重新着色会有bug. 所以禁掉了
                dissallowOtherButtons();
                break;
            case R.id.btn_immerse_transparent1:
                setToolbarColor(R.color.transparent);
                transparentBar();
                setRootBackground(R.mipmap.img_background_anime1);
                // 半透明效果是修改了根布局的fitSystemWindows属性. 这时候重新着色会有bug. 所以禁掉了
                break;
        }
    }

    private void setRootBackground(@DrawableRes int imgResourse) {
        if (imgResourse == -1) {
            rootLayout.setBackgroundColor(Color.WHITE);
        } else {
            rootLayout.setBackgroundResource(imgResourse);
        }
    }

    private void dissallowOtherButtons() {
        red.setEnabled(false);
        green.setEnabled(false);
        blue.setEnabled(false);
    }

    private void setToolbarColor(int colorId) {
        int color = getResources().getColor(colorId);
        toolbar.setBackgroundColor(color);
    }

    @TargetApi(21)
    private void setStatusBarColor(int colorId) {
        setStatusBarColor(colorId, 50);
    }

    @TargetApi(21)
    private void setStatusBarColor(int colorId, int alpha) {
        int color = getResources().getColor(colorId);
        Window window = getWindow();
        // 1. 添加绘制系统bar 相当于style中配置windowDrawsSystemBarBackgrounds -> true
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // 2. 清除2个半透明 相当于style中配置windowTranslucentStatus -> false
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 相当于style中配置windowTranslucentNavigation -> false
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        // 3. 设置颜色 状态栏颜色
        window.setStatusBarColor(calculateColorWithOpacity(color, alpha));
        // 导航栏颜色
        window.setNavigationBarColor(color);
//        window.setNavigationBarColor(calculateColorWithOpacity(color, alpha));
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void translucentBar() {
        Window window = getWindow();
        // 添加2个半透明的flag
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 上面会导致Toolbar陷进状态栏. 需要给根布局添加fitSystemWindows属性. 可以用代码也布局文件中加
        fitSystemWindows();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void transparentBar() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        View decorView = window.getDecorView();
        /*
            flag1: 应用的主体内容占用系统状态栏的空间, 配合flag2一起用
            flag2: 必须配合flag1一起, 应用的主体内容占用系统状态栏的空间
            flag3: 让应用的主体内容占用系统导航栏的空间
            注意: flag1和flag3有两个相似的 SYSTEM_UI_FLAG_FULLSCREEN / SYSTEM_UI_FLAG_HIDE_NAVIGATION,
            分别是隐藏状态栏和隐藏导航栏.
         */
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(option);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);
        // 同样的会导致Toolbar陷入状态栏.
        fitSystemWindows();
    }

    private void fitSystemWindows() {
        ViewGroup view = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        view.setFitsSystemWindows(true);
    }

}
