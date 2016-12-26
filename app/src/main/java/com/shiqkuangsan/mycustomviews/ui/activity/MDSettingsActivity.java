package com.shiqkuangsan.mycustomviews.ui.activity;

import android.os.Bundle;

import com.example.swipebackactivity.app.SwipeBackActivity;
import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.utils.MyStatusBarUtil;

/**
 * Created by shiqkuangsan on 2016/11/18.
 *
 * @author shiqkuangsan
 * @summary 使用PreferenceFragment编写设置页面(继承自SwipeBackActivity之后不好用PreferenceActivity)
 */

public class MDSettingsActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        int color = getResources().getColor(R.color.colorPrimaryDark);
        MyStatusBarUtil.setStatusColor(this, color);
    }
}
