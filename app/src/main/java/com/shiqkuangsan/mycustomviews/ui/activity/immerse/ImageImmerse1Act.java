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

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by shiqkuangsan on 2017/8/2. <p>
 * ClassName: ImageImmerse1Act <p>
 * Author: shiqkuangsan <p>
 * Description: 图片沉浸式 -> Toolbar. 该界面默认使用style配置形式实现. 代码设置优先级高于style.
 * 需要看代码设置效果的清单文件中AppNoActionBar.Immerse主题改成AppNoActionBar, 放开初始化沉浸式代码即可
 */
public class ImageImmerse1Act extends AppCompatActivity {

    @ViewInject(R.id.toolbar_image_immerse1)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background_immerse1);
        x.view().inject(this);

        initToolbar();// 添加返回键支持

//        immerse();// 实现沉浸式
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
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.setStatusBarColor(Color.TRANSPARENT);
//        window.setNavigationBarColor(calculateColorWithOpacity(color, alpha));
    }
}
