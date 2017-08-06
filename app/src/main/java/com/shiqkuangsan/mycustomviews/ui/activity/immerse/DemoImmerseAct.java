package com.shiqkuangsan.mycustomviews.ui.activity.immerse;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.shiqkuangsan.mycustomviews.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by shiqkuangsan on 2017/8/2. <p>
 * ClassName: DemoImmerseAct <p>
 * Author: shiqkuangsan <p>
 * Description: demo. 布局中已设置fitSystemWindows
 */
@ContentView(R.layout.activity_demo_immerse)
public class DemoImmerseAct extends AppCompatActivity {

    @ViewInject(R.id.toolbar_image_immerse1)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        initToolbar();

//        immerse(); // 已经通过配置style实现, 并且布局中设置fitSystemWindows
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void immerse() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            return;
        Window window = getWindow();
        // 清除状态栏半透明
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 导航栏半透明
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        // 状态栏全透明
        window.setStatusBarColor(Color.TRANSPARENT);
//        window.setNavigationBarColor(Color.RED); // 添加半透明后设置颜色无效
    }
}
