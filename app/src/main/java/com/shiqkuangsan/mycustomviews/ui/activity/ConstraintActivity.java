package com.shiqkuangsan.mycustomviews.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.shiqkuangsan.mycustomviews.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

/**
 * Created by shiqkuangsan on 2017/1/15.
 * <p>
 * ClassName: ConstraintActivity
 * Author: shiqkuangsan
 * Description: 学习使用ConstraintLayout的界面
 */
@ContentView(R.layout.activity_constraint)
public class ConstraintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

    }
}
