package com.shiqkuangsan.mycustomviews.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.base.BaseActivity;
import com.shiqkuangsan.mycustomviews.ui.activity.immerse.myimmerse.BgImmerseActivity;
import com.shiqkuangsan.mycustomviews.ui.activity.immerse.myimmerse.ColorImmerseActivity;
import com.shiqkuangsan.mycustomviews.utils.MyStatusBarUtil;

/**
 * Created by shiqkuangsan on 2016/9/28.
 *
 * @author shiqkaungsan
 * Description: 测试沉浸式的页面, 沉浸式分为两种, 每种又需要处理4.4.4 / 5.0,其中
 * 4.4.4呈现的是半透明状态栏, 而5.0以上呈现的是纯色状态栏(4.4.4以下不支持,定制机特殊)
 * <p>
 * 1. 着色沉浸式-沉浸颜色
 * 2. 背景沉浸式-沉浸背景
 * 具体实现戳ColorImmerseActivity / BgImmerseActivity
 */
public class MyImmerseActivity extends BaseActivity {

    private Toolbar toolbar;
    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myimmerse);

        toolbar = (Toolbar) findViewById(R.id.toolBar_immerse);
        toolbar.setTitle("沉浸式测试");
        color = getResources().getColor(R.color.color_orange);
    }

    /**
     * 测试着色功能
     *
     * @param view
     */
    public void statuscolor(View view) {
        MyStatusBarUtil.setStatusColor(this, color);
    }

    /**
     * 测试透明功能
     *
     * @param view
     */
    public void statustransparent(View view) {
        MyStatusBarUtil.setStatusTransparent(this, true);
    }

    /**
     * 进入着色沉浸效果页
     *
     * @param view
     */
    public void colorimmerse(View view) {
        startActivity(new Intent(this, ColorImmerseActivity.class));
    }

    /**
     * 进入背景沉浸效果页
     *
     * @param view
     */
    public void bgimmerse(View view) {
        startActivity(new Intent(this, BgImmerseActivity.class));
    }
}