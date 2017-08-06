package com.shiqkuangsan.mycustomviews.ui.activity.immerse.myimmerse;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.utils.MyStatusBarUtil;

import org.xutils.view.annotation.ContentView;

/**
 * Created by shiqkuangsan on 2016/10/20.
 *
 * @author shiqkuangsan
 * @summary 着色沉浸式页面
 */
@ContentView(R.layout.activity_color_immerse)
public class ColorImmerseActivity extends AppCompatActivity implements View.OnClickListener {

    private int color;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

        color = ContextCompat.getColor(this, R.color.colorPrimary);
        MyStatusBarUtil.setStatusColor(this, color);
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_colorimmerse);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findViewById(R.id.btn_immerse_red).setOnClickListener(this);
        findViewById(R.id.btn_immerse_green).setOnClickListener(this);
        findViewById(R.id.btn_immerse_blue).setOnClickListener(this);
    }

    private void setToolBarColor(int color) {
        toolbar.setBackgroundColor(color);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_immerse_red:
                color = Color.RED;
                MyStatusBarUtil.setStatusColor(this, color);
                setToolBarColor(color);
                break;

            case R.id.btn_immerse_green:
                color = Color.GREEN;
                MyStatusBarUtil.setStatusColor(this, color);
                setToolBarColor(color);
                break;

            case R.id.btn_immerse_blue:
                color = Color.BLUE;
                MyStatusBarUtil.setStatusColor(this, color);
                setToolBarColor(color);
                break;
        }
    }

}
