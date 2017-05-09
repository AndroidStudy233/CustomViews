package com.shiqkuangsan.mycustomviews.ui.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.shiqkuangsan.mycustomviews.ui.activity.vlayout.ui.VLayoutActivity;

/**
 * Created by shiqkuangsan on 2017/5/9.
 * <p>
 * ClassName: VLayoutMainActivity
 * Author: shiqkuangsan
 * Description: 学习VLayout的界面
 */
public class VLayoutMainActivity extends ListActivity {

    String[] itemTexts = new String[]{
            VLayoutActivity.class.getSimpleName(),
    };

    Class[] activities = new Class[]{
            VLayoutActivity.class,
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
