package com.shiqkuangsan.mycustomviews.ui.activity.immerse;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.shiqkuangsan.mycustomviews.R;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import static com.shiqkuangsan.mycustomviews.utils.UIUitl.calculateColorWithOpacity;

/**
 * Created by shiqkuangsan on 2017/8/2. <p>
 * ClassName: ColorImmerse1Act <p>
 * Author: shiqkuangsan <p>
 * Description: 颜色沉浸式界面1 -> Toolbar下沉浸+NavigationBar
 */
public class ColorImmerse1Act extends AppCompatActivity {

    @ViewInject(R.id.toolbar_colorimmerse1)
    Toolbar toolbar;
    @ViewInject(R.id.btn_immerse_red1)
    Button btn_red;
    @ViewInject(R.id.btn_immerse_green1)
    Button btn_green;
    @ViewInject(R.id.btn_immerse_blue1)
    Button btn_blue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_immerse1);
        x.view().inject(this);

        // 加个返回键, 需要onOptionsItemSelected添加返回支持
        initToolbar();
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

    @Event(value = {R.id.btn_immerse_red1, R.id.btn_immerse_green1, R.id.btn_immerse_blue1})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_immerse_red1:
                setToolbarColor(R.color.red);
                setStatusBarColor(R.color.red);
                break;
            case R.id.btn_immerse_green1:
                setToolbarColor(R.color.green);
                setStatusBarColor(R.color.green);
                break;
            case R.id.btn_immerse_blue1:
                setToolbarColor(R.color.blue);
                setStatusBarColor(R.color.blue, 0);
                break;
        }
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
        // 相当于style中配置windowTranslucentStatus -> true
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // 相当于style中配置windowTranslucentNavigation -> false
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 手动设置状态颜色
        window.setStatusBarColor(calculateColorWithOpacity(color, alpha));
        // 手动设置导航栏颜色
        window.setNavigationBarColor(calculateColorWithOpacity(color, alpha));
    }

}
