package com.shiqkuangsan.mycustomviews.ui.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.shiqkuangsan.mycustomviews.ui.activity.coodinator.CollapsingToolbarAct;
import com.shiqkuangsan.mycustomviews.ui.activity.coodinator.CustomBehaviorAct;
import com.shiqkuangsan.mycustomviews.ui.activity.coodinator.CustomBehaviorAct22;
import com.shiqkuangsan.mycustomviews.ui.activity.coodinator.DrawerEmmaInfoActivity;
import com.shiqkuangsan.mycustomviews.ui.activity.coodinator.FollowScollToolbarAct;
import com.shiqkuangsan.mycustomviews.ui.activity.coodinator.FragmentSurppotAct;
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
            "4. DrawerLayout下parallax效果",
            "5. BottomBar模式fragment沉浸 + parallax",
            "6. 自定义Behavior实现Slidingup + parallax",
    };

    Class[] activities = new Class[]{
            FollowScollToolbarAct.class,
            StickyTabScrollToolbarAct.class,
            CollapsingToolbarAct.class,
            DrawerEmmaInfoActivity.class,
            FragmentSurppotAct.class,
            CustomBehaviorAct.class
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
