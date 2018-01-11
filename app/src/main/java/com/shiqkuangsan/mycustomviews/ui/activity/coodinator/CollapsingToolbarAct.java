package com.shiqkuangsan.mycustomviews.ui.activity.coodinator;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by shiqkuangsan on 2017/8/4. <p>
 * ClassName: CollapsingToolbarAct <p>
 * Author: shiqkuangsan <p>
 * Description: 头部视差效果parallax.<p>
 * 头部视差效果需要用到CollapsingToolbarLayout, 他的属性和介绍在笔记中直接可以搜到.
 * AppBarLayout里面放入CollapsingToolbarLayout, 里面再放入ImageView和Toolbar. 通过调整layout_collapseMode
 * 即可实现. 通过设置儿子(这里是ImageView)的app:layout_collapseParallaxMultiplier属性值(0.0 - 1.0)调整视差效果
 */
@ContentView(R.layout.activity_collapsing_simple)
public class CollapsingToolbarAct extends BaseActivity {

    @ViewInject(R.id.collapsing_coordinator_demo)
    CollapsingToolbarLayout collapsing;
    @ViewInject(R.id.toolbar_coordinator_demo)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        collapsing.setTitle("Collapsing");
        initToolbar();
        immerse();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_coordinator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @TargetApi(21)
    private void immerse() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            return;
        Window window = getWindow();
        View decorView = window.getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(option);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);
        fitSystemWindows();
    }

    private void fitSystemWindows() {
        ViewGroup viewGroup = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        viewGroup.setFitsSystemWindows(true);
    }

}
