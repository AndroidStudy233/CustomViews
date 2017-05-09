package com.shiqkuangsan.mycustomviews.ui.activity.vlayout.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.ui.activity.vlayout.subadapter.VLayout1thAdapter;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by shiqkuangsan on 2017/5/9.
 * <p>
 * ClassName: VLayoutActivity
 * Author: shiqkuangsan
 * Description: 学习使用V-Layout
 */
public class VLayoutActivity extends AppCompatActivity {

    @ViewInject(R.id.recycler_vlayout)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v_layout);
        x.view().inject(this);

        initUI();
    }

    private void initUI() {
        // 创建VirtualLayoutManager对象
        VirtualLayoutManager manager = new VirtualLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        // 设置组件复用回收池
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);

        // 添加divider
        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = ((VirtualLayoutManager.LayoutParams) view.getLayoutParams()).getViewPosition();
                outRect.set(2, 2, 2, 4);
            }
        };
        recyclerView.addItemDecoration(itemDecoration);

        // 设置适配器, 参数2 当true的时候，不论是不是属于同一个子adapter，相同类型的item都能复用。
        // 表示它们共享一个类型。 当false的时候，不同子adapter之间的类型不共享
        DelegateAdapter delegateAdapter = new DelegateAdapter(manager, true);
        recyclerView.setAdapter(delegateAdapter);
        // 下面再让让delegateAdapter添加所有的adapter, 就可以实现不同的类型的条目了
        final List<DelegateAdapter.Adapter> adapters = new LinkedList<>();

        // 第一个条目
        VLayout1thAdapter adapter1th = new VLayout1thAdapter(this, new LinearLayoutHelper(), 1);
        adapters.add(adapter1th);


        delegateAdapter.setAdapters(adapters);

    }

}
