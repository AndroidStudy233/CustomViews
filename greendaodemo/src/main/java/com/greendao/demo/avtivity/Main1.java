package com.greendao.demo.avtivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.greendaodemo.R;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/2/26
 * 内容：
 * 最后修改：
 */

public class Main1 extends AppCompatActivity {
    private android.widget.Button pigBut;
    private android.widget.Button miBut;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        this.miBut = (Button) findViewById(R.id.miBut);
        this.pigBut = (Button) findViewById(R.id.pigBut);
        miBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main1.this,GreenDaoActivity.class));
            }
        });
        pigBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main1.this,MainActivity.class));
            }
        });
    }
}
