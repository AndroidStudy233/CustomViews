package com.shiqkuangsan.mycustomviews.ui.activity.immerse;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.utils.MyLogUtil;
import com.shiqkuangsan.mycustomviews.utils.UIUitl;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by shiqkuangsan on 2017/8/3. <p>
 * ClassName: CompleteImmerseAct <p>
 * Author: shiqkuangsan <p>
 * Description: 完全沉浸式. 模拟器上可能会拉不回来状态栏和导航栏. 但是真机OK的
 * 实现方式很简单, 重写onWindowFocusChanged 添加相应flag即可. 注意添加fitSystemWindows属性
 */
@ContentView(R.layout.activity_complete_immerse)
public class CompleteImmerseAct extends AppCompatActivity {

    @ViewInject(R.id.toolbar_complete_immerse)
    Toolbar toolbar;
    @ViewInject(R.id.ll_immerse_playlayout)
    LinearLayout playLayout;

    boolean flag = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        initToolbar();
        // 有导航栏的手机隐藏的时候右边有margin
    }

    private void compatLeftMargin() {
        int naviShowId = getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        boolean naviShow = getResources().getBoolean(naviShowId);
        MyLogUtil.d(naviShow ? "true" : "false");
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

    @Event(value = {R.id.rl_immerse_background})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_immerse_background:
                toolbar.setVisibility(flag ? View.VISIBLE : View.INVISIBLE);
                playLayout.setVisibility(flag ? View.VISIBLE : View.INVISIBLE);
                flag = !flag;
                break;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        toolbar.setVisibility(View.INVISIBLE);
        playLayout.setVisibility(View.INVISIBLE);
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(option);
        }
    }
}
