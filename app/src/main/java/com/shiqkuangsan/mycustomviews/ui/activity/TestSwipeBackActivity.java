package com.shiqkuangsan.mycustomviews.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.swipebackactivity.app.SwipeBackActivity;
import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.ui.activity.testswipeback.Activity2;

/**
 * Created by shiqkuangsan on 2016/11/16.
 *
 * @author shiqkuangsan
 * @summary 测试SwipeBackActivity和StatusBarUtil
 */

public class TestSwipeBackActivity extends SwipeBackActivity {

    private Toolbar toolbar;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipeback_1);
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("大猪SB");
        actionBar.setDisplayHomeAsUpEnabled(true);

        fab = (FloatingActionButton) findViewById(R.id.fab_explose);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "What a shit big pig!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initMainStyle(fab, toolbar, true, true, null, 0xff92B4DA);

        startActivity(new Intent(this,Activity2.class));
    }


    public void blue(View view) {
        initMainStyle(fab, toolbar, true, true, null, 0xff4545FF);
    }

    public void red(View view) {
        initMainStyle(fab, toolbar, true, true, null, 0xffFF2C2C);
    }

    public void green(View view) {
        initMainStyle(fab, toolbar, true, true, null, 0xff47FF4D);
    }
}
