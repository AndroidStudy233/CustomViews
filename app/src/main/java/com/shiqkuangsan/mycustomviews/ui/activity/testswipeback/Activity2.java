package com.shiqkuangsan.mycustomviews.ui.activity.testswipeback;

import android.content.Intent;
import android.os.Bundle;

import com.example.swipebackactivity.app.SwipeBackActivity;
import com.shiqkuangsan.mycustomviews.R;

/**
 * Created by shiqkuangsan on 2016/11/16.
 *
 * @author shiqkuangsan
 * @summary
 */

public class Activity2 extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipeback_2);

        startActivity(new Intent(this, Activity3.class));
    }
}
