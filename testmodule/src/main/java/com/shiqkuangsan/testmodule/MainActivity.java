package com.shiqkuangsan.testmodule;

import android.animation.Animator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;

import java.io.BufferedReader;

import static android.R.attr.id;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // fab
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // 测试贝塞尔曲线
        BaseirWaveView waveView = (BaseirWaveView) findViewById(R.id.wave_demo);
        waveView.setRunning();

        btn_main = (Button) findViewById(R.id.btn_ripple_main);
        btn_main.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ripple_main:
                Animator animator = ViewAnimationUtils.createCircularReveal(
                        btn_main, 0, 0, 0, (float) Math.hypot(btn_main.getWidth(), btn_main.getHeight()));
                animator.setInterpolator(new AccelerateInterpolator());
                animator.setDuration(600);
                animator.start();
                break;
        }
    }
}
