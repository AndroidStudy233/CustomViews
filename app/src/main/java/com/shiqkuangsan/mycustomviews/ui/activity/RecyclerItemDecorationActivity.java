package com.shiqkuangsan.mycustomviews.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.adapter.NormalRecyclerAdapter;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by shiqkuangsan on 2017/5/10.
 * <p>
 * ClassName: RecyclerItemDecorationActivity
 * Author: shiqkuangsan
 * Description: RecyclerView的条目装饰ItemDecoration
 */
public class RecyclerItemDecorationActivity extends AppCompatActivity {

    @ViewInject(R.id.recycler_itemdecoration)
    RecyclerView recycler_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_item_decoration);
        x.view().inject(this);

        initUI();
    }

    private void initUI() {
        recycler_main.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        NormalRecyclerAdapter adapter = new NormalRecyclerAdapter(this);
        recycler_main.setAdapter(adapter);
        //
    }
}
