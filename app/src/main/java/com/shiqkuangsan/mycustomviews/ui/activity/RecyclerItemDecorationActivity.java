package com.shiqkuangsan.mycustomviews.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.adapter.NormalRecyclerAdapter;
import com.shiqkuangsan.mycustomviews.adapter.itemdecoration.NormalLineDecoration;
import com.shiqkuangsan.mycustomviews.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by shiqkuangsan on 2017/5/10.
 * <p>
 * ClassName: RecyclerItemDecorationActivity
 * Author: shiqkuangsan
 * Description: RecyclerView的条目装饰ItemDecoration使用初步
 */
@ContentView(R.layout.activity_recycler_item_decoration)
public class RecyclerItemDecorationActivity extends BaseActivity {

    @ViewInject(R.id.recycler_itemdecoration)
    RecyclerView recycler_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        initUI();
    }

    private void initUI() {
        recycler_main.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        NormalRecyclerAdapter adapter = new NormalRecyclerAdapter(this);
        recycler_main.setAdapter(adapter);
        // 给条目加上分割线
        NormalLineDecoration.enableDrawOver = false; // 是否观看DrawOver方法的效果
        NormalLineDecoration lineDecoration = new NormalLineDecoration(this, LinearLayoutManager.VERTICAL, 3, 0xffE4E1E9);
        recycler_main.addItemDecoration(lineDecoration);
    }
}
