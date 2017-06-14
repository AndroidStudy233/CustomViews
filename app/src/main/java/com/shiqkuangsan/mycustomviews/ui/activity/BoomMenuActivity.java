package com.shiqkuangsan.mycustomviews.ui.activity;

import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.utils.ToastUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by shiqkuangsan on 2017/6/14.
 * <p>
 * ClassName: BoomMenuActivity
 * Author: shiqkuangsan
 * Description: 弹出多个按钮的菜单界面
 */
public class BoomMenuActivity extends AppCompatActivity {

    @ViewInject(R.id.navigation_menu)
    BottomNavigationView navigationView;
    @ViewInject(R.id.floating_boommenu)
    FloatingActionButton floating_boommenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boom_menu);
        x.view().inject(this);

        init();
    }

    private void init() {
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_movie:
                        ToastUtil.toastShort(BoomMenuActivity.this, "Moview");
                        navigationView.setItemBackgroundResource(R.color.sienna);
                        break;
                    case R.id.menu_music:
                        ToastUtil.toastShort(BoomMenuActivity.this, "Music");
                        navigationView.setItemBackgroundResource(R.color.red);
                        break;
                    case R.id.menu_picture:
                        ToastUtil.toastShort(BoomMenuActivity.this, "Picutre");
                        navigationView.setItemBackgroundResource(R.color.colorPrimary);
                        break;
                    case R.id.menu_book:
                        ToastUtil.toastShort(BoomMenuActivity.this, "Book");
                        navigationView.setItemBackgroundResource(R.color.green);
                        break;
                    case R.id.menu_newspaper:
                        ToastUtil.toastShort(BoomMenuActivity.this, "News");
                        navigationView.setItemBackgroundResource(R.color.darkorchid);
                        break;
                }
                return true;
            }
        });
        navigationView.setSelectedItemId(R.id.menu_picture);

        floating_boommenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.toastShort(BoomMenuActivity.this, "Floating");
            }
        });
    }
}
