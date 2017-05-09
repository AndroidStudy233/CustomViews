package com.shiqkuangsan.mycustomviews.ui.activity.vlayout.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.FixLayoutHelper;
import com.alibaba.android.vlayout.layout.FloatLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;
import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.ui.activity.vlayout.subadapter.SimpleVLayoutAdapter;
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
        // 下面再让delegateAdapter添加所有的adapter, 就可以实现不同的类型的条目了
        final List<DelegateAdapter.Adapter> adapters = new LinkedList<>();

        // 第一个    使用LinearLayoutHelper实现ViewPager(img)
        VLayout1thAdapter adapter1th = new VLayout1thAdapter(this, new LinearLayoutHelper(), 1);
        adapters.add(adapter1th);

        // 第二个    浮动的一个正方形
        FloatLayoutHelper layoutHelper2th = new FloatLayoutHelper();
        layoutHelper2th.setAlignType(FixLayoutHelper.TOP_RIGHT);
        layoutHelper2th.setDefaultLocation(100, 400);    // 设置默认位置
        VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(150, 150);
        SimpleVLayoutAdapter adapter2th = new SimpleVLayoutAdapter(this, layoutHelper2th, 1, layoutParams);
        adapters.add(adapter2th);

        // 第三个    就是一个简单的灰色条目
        LinearLayoutHelper layoutHelper3th = new LinearLayoutHelper();
        layoutHelper3th.setAspectRatio(2.0f);// 不通过params设置高度, 而通过宽高比实现 宽:高=2
        adapters.add(new SimpleVLayoutAdapter(this, layoutHelper3th, 1));

        // 第四个    7个条目, 位置偶数的高度大一点
        LinearLayoutHelper layoutHelper4th = new LinearLayoutHelper();
        layoutHelper4th.setAspectRatio(2.0f);
        layoutHelper4th.setDividerHeight(10);
        layoutHelper4th.setMargin(0, 30, 0, 10);
        layoutHelper4th.setPadding(10, 30, 10, 10);
        layoutHelper4th.setBgColor(0xFFA9FFF8);
        adapters.add(new SimpleVLayoutAdapter(this, layoutHelper4th, 6) {
            @Override
            public void onBindViewHolder(VLayoutViewHolder holder, int position) {
                /*
                    这里主要提一个AspectRatio的优先级问题, LayoutHelper的AspectRatio是要低于
                    layoutParams的AspectRatio的, 因此上面设置的宽高比2就无效了
                    另外这里的position指的是SimpleVLayoutAdapter下的条目item, 是从0开始的
                    别和总共的Recycler弄混了
                 */
                VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(
                        VirtualLayoutManager.LayoutParams.MATCH_PARENT, 300);
                if (position % 2 == 0) {
                    layoutParams.mAspectRatio = 3;
                } else {
                    layoutParams.mAspectRatio = 5;
                }
                holder.itemView.setLayoutParams(layoutParams);
            }
        });

        // 第五个  这个牛逼了, 就是头部粘性效果
        StickyLayoutHelper layoutHelper5th = new StickyLayoutHelper();
        layoutHelper5th.setOffset(1);// 距离头部多少高度粘性
        layoutHelper5th.setAspectRatio(4);
        adapters.add(new SimpleVLayoutAdapter(this, layoutHelper5th, 1));

        delegateAdapter.setAdapters(adapters);

    }

}
