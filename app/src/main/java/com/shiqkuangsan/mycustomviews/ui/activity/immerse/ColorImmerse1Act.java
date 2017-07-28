package com.shiqkuangsan.mycustomviews.ui.activity.immerse;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.shiqkuangsan.mycustomviews.R;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by shiqkuangsan on 2017/7/27.
 * <p>
 * ClassName: ColorImmerse1Act
 * Author: shiqkuangsan
 * Description: 颜色沉浸式界面1 -> 使用Toolbar
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
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(calculateStatusColor(color, alpha));
//        getWindow().setStatusBarColor(color);
    }

    /**
     * 计算状态栏颜色, 根据不同透明度和颜色算出应该显示的颜色
     *
     * @param color color值
     * @param alpha alpha值 0-100 ↑
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
