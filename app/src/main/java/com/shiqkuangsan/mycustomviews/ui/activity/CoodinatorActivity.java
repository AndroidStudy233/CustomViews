package com.shiqkuangsan.mycustomviews.ui.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.shiqkuangsan.mycustomviews.ui.activity.coodinator.CollapsingToolbarAct;
import com.shiqkuangsan.mycustomviews.ui.activity.coodinator.EmmaInfoDemoActivity;
import com.shiqkuangsan.mycustomviews.ui.activity.coodinator.FollowScollToolbarAct;
import com.shiqkuangsan.mycustomviews.ui.activity.coodinator.StickyTabScrollToolbarAct;

/**
 * Created by shiqkuangsan on 2017/7/27. <p>
 * ClassName: CoodinatorActivity <p>
 * Author: shiqkuangsan <p>
 * Description: CoordinatorLayoutDemo集合页面
 */
public class CoodinatorActivity extends ListActivity {

    String[] itemTexts = new String[]{
            "1. Toolbar跟随滚动",
            "2. Toolbar跟随滚动, Tab固定",
            "3. Collapsing演示parallax效果",
            "5. Emma个人信息demo",
    };

    Class[] activities = new Class[]{
            FollowScollToolbarAct.class,
            StickyTabScrollToolbarAct.class,
            CollapsingToolbarAct.class,
            EmmaInfoDemoActivity.class,
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemTexts));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        startActivity(new Intent(this, activities[position]));
    }
}
