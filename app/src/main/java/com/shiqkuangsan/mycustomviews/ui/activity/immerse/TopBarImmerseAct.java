package com.shiqkuangsan.mycustomviews.ui.activity.immerse;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.shiqkuangsan.mycustomviews.R;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import static com.shiqkuangsan.mycustomviews.utils.UIUitl.calculateColorWithOpacity;

/**
 * Created by shiqkuangsan on 2017/8/2. <p>
 * ClassName: TopBarImmerseAct <p>
 * Author: shiqkuangsan <p>
 * Description: 纯色沉浸、半透明、全透明
 */
public class TopBarImmerseAct extends AppCompatActivity {

    @ViewInject(R.id.rl_immerse_topbar)
    LinearLayout topbar;
    @ViewInject(R.id.btn_immerse_red2)
    Button red;
    @ViewInject(R.id.btn_immerse_green2)
    Button green;
    @ViewInject(R.id.btn_immerse_blue2)
    Button blue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topbar_immerse);
        x.view().inject(this);

    }

    @Event(value = {R.id.iv_immerse_back, R.id.btn_immerse_red2, R.id.btn_immerse_green2, R.id.btn_immerse_blue2,
            R.id.btn_immerse_translucent2, R.id.btn_immerse_transparent2})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_immerse_back:
                finish();
                break;
            case R.id.btn_immerse_red2:
                setTopbarColor(R.color.red);
                setStatusBarColor(R.color.red);
                break;
            case R.id.btn_immerse_green2:
                setTopbarColor(R.color.green);
                setStatusBarColor(R.color.green);
                break;
            case R.id.btn_immerse_blue2:
                setTopbarColor(R.color.blue);
                setStatusBarColor(R.color.blue, 0);
                break;
            case R.id.btn_immerse_translucent2:
                setTopbarColor(R.color.transparent);
                tranlucentBar();
                dissallowOtherButtons();
                break;
            case R.id.btn_immerse_transparent2:
                setTopbarColor(R.color.transparent);
                transparentBar();
                break;
        }
    }

    private void dissallowOtherButtons() {
        red.setEnabled(false);
        green.setEnabled(false);
        blue.setEnabled(false);
    }

    private void setTopbarColor(int colorId) {
        int color = getResources().getColor(colorId);
        topbar.setBackgroundColor(color);
    }

    @TargetApi(21)
    private void setStatusBarColor(int colorId) {
        setStatusBarColor(colorId, 50);
    }

    @TargetApi(21)
    private void setStatusBarColor(int colorId, int alpha) {
        int color = getResources().getColor(colorId);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        window.setStatusBarColor(calculateColorWithOpacity(color, alpha));
        window.setNavigationBarColor(color);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void tranlucentBar() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        fitSystemWindows();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void transparentBar() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        View decorView = window.getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(option);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);
        fitSystemWindows();
    }

    private void fitSystemWindows() {
        ViewGroup view = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        view.setFitsSystemWindows(true);
    }

}
