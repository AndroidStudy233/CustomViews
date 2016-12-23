package com.shiqkuangsan.mycustomviews.ui.activity.api21;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import com.shiqkuangsan.mycustomviews.R;

/**
 * 配合展示Activity切换动画和共享元素的界面
 */
@TargetApi(21)
public class Api21Activity extends AppCompatActivity {

    private TextView tv_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api21);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_explose);
        toolbar.setTitle("5.0简单测试页面");
        toolbar.setTitleTextColor(0xde000000);
        toolbar.setNavigationIcon(R.drawable.img_back);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_explose);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        tv_text = (TextView) findViewById(R.id.tv_api21_text);

        ViewTreeObserver observer = tv_text.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Animator animator = ViewAnimationUtils.createCircularReveal(
                        tv_text, 0, 0, 0, (float) Math.hypot(tv_text.getWidth(), tv_text.getHeight()));
                animator.setInterpolator(new AccelerateInterpolator());
                animator.setDuration(800);
                animator.start();
                tv_text.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                final int w = tv_text.getMeasuredWidth();
                final int h = tv_text.getMeasuredHeight();
            }
        });
    }

}
