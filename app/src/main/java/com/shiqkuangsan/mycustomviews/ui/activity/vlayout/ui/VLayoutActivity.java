package com.shiqkuangsan.mycustomviews.ui.activity.vlayout.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.ColumnLayoutHelper;
import com.alibaba.android.vlayout.layout.FixLayoutHelper;
import com.alibaba.android.vlayout.layout.FloatLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper;
import com.alibaba.android.vlayout.layout.StaggeredGridLayoutHelper;
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
                outRect.set(1, 1, 1, 4);
            }
        };
        recyclerView.addItemDecoration(itemDecoration);

        // 设置适配器, 参数2 当true的时候，不论是不是属于同一个子adapter，相同类型的item都能复用。
        // 表示它们共享一个类型。 当false的时候，不同子adapter之间的类型不共享
        DelegateAdapter delegateAdapter = new DelegateAdapter(manager, true);
        recyclerView.setAdapter(delegateAdapter);
        // 下面再让delegateAdapter添加所有的adapter, 就可以实现不同的类型的条目了
        final List<DelegateAdapter.Adapter> adapters = new LinkedList<>();

        // 第一个0    使用LinearLayoutHelper实现ViewPager(img)
        VLayout1thAdapter adapter1th = new VLayout1thAdapter(this, new LinearLayoutHelper(), 1);
        adapters.add(adapter1th);

        // 第二个1    浮动的一个正方形
        FloatLayoutHelper layoutHelper2th = new FloatLayoutHelper();
        layoutHelper2th.setAlignType(FixLayoutHelper.TOP_RIGHT);
        layoutHelper2th.setDefaultLocation(100, 400);    // 设置默认位置
        VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(200, 200);
        SimpleVLayoutAdapter adapter2th = new SimpleVLayoutAdapter(this, layoutHelper2th, 1, layoutParams);
        adapters.add(adapter2th);

        // 第三个2    就是一个简单的灰色条目
        LinearLayoutHelper layoutHelper3th = new LinearLayoutHelper();
        layoutHelper3th.setAspectRatio(2.0f);// 不通过params设置高度, 而通过宽高比实现 宽:高=2
        adapters.add(new SimpleVLayoutAdapter(this, layoutHelper3th, 1));

        // 第四个3-8    6个条目, 位置偶数的高度大一点
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

        // 第五个9  这个牛逼了, 就是头部粘性效果
        StickyLayoutHelper layoutHelper5th = new StickyLayoutHelper();
        layoutHelper5th.setOffset(1);// 距离头部多少高度粘性
        layoutHelper5th.setAspectRatio(4);
        adapters.add(new SimpleVLayoutAdapter(this, layoutHelper5th, 1));

        // 第六个10-14  一行多个, column
        ColumnLayoutHelper layoutHelper6th = new ColumnLayoutHelper();
        layoutHelper6th.setBgColor(0xff00f0f0);
        layoutHelper6th.setWeights(new float[]{10, 10, 20, 20, 40});
        adapters.add(new SimpleVLayoutAdapter(this, layoutHelper6th, 5) {

            @Override
            public void onBindViewHolder(VLayoutViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                /*
                    同样的这里的weight优先级要低于LayoutParams的AspectRatio
                    如果设置LayoutParams的高度, 然后设置LayoutParams的AspectRatio, 还是会根据高度重新计算宽度
                 */
            }

        });

        // 第七个15    底部粘性效果  构造传个false
        StickyLayoutHelper layoutHelper7th = new StickyLayoutHelper(false);
        layoutHelper7th.setOffset(100);
        VirtualLayoutManager.LayoutParams layoutParams1 = new VirtualLayoutManager.LayoutParams(
                VirtualLayoutManager.LayoutParams.MATCH_PARENT, 200);
        adapters.add(new SimpleVLayoutAdapter(this, layoutHelper7th, 1, layoutParams1));

        // 第八个固定布局16
        FixLayoutHelper layoutHelper8th = new FixLayoutHelper(FixLayoutHelper.TOP_LEFT, 100, 100);
        adapters.add(new SimpleVLayoutAdapter(this, layoutHelper8th, 1) {
            @Override
            public void onBindViewHolder(VLayoutViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(200, 200);
                holder.itemView.setLayoutParams(layoutParams);
            }
        });

        // 第九个17-18    1拖N小试
        OnePlusNLayoutHelper layoutHelper9th = new OnePlusNLayoutHelper();
        layoutHelper9th.setBgColor(0xffE551DC);
        layoutHelper9th.setAspectRatio(4.0f);
        layoutHelper9th.setColWeights(new float[]{20, 40});// 2列, 分别占20% 40%
        layoutHelper9th.setMargin(20, 20, 20, 20);
//        layoutHelper7th.setPadding(10, 10, 10, 10);
        adapters.add(new SimpleVLayoutAdapter(this, layoutHelper9th, 2));

        // 第十个19-22     继续1拖N    这种布局数量只能有1-5 而且怎么排是里面写好的
        OnePlusNLayoutHelper layoutHelper10th = new OnePlusNLayoutHelper();
        layoutHelper10th.setBgColor(0xff635EF8);
        layoutHelper10th.setAspectRatio(2.0f); // 宽高比2:1
        layoutHelper10th.setRowWeight(30f); // 第一行占30%高度
        layoutHelper10th.setColWeights(new float[]{30f}); // 第一列30%, 剩下的占满
        adapters.add(new SimpleVLayoutAdapter(this, layoutHelper10th, 4) {
            @Override
            public void onBindViewHolder(VLayoutViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                VirtualLayoutManager.LayoutParams lp = (VirtualLayoutManager.LayoutParams)
                        holder.itemView.getLayoutParams();
                if (position == 0) {
                    lp.rightMargin = 1;
                } else if (position == 1) {

                } else if (position == 2) {
                    lp.topMargin = 1;
                    lp.rightMargin = 1;
                }
            }
        });

        // 第十一个23-50     瀑布流2列, 条目间距10
        final StaggeredGridLayoutHelper layoutHelper11th = new StaggeredGridLayoutHelper(2, 10);
        layoutHelper11th.setBgColor(0xFF86345A);
        adapters.add(new SimpleVLayoutAdapter(this, layoutHelper11th, 28) {
            @Override
            public void onBindViewHolder(VLayoutViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
                if (position % 2 == 0) {
                    // 偶数位置正方形
                    layoutParams.mAspectRatio = 1.0f;
                } else {
                    // 奇数位置最低高度300
                    layoutParams.height = 300 + position % 7 * 30;
                }
                holder.itemView.setLayoutParams(layoutParams);
            }
        });

        // 第十二个51-70    Grid布局, 2列
        GridLayoutHelper layoutHelper12th = new GridLayoutHelper(2);
        layoutHelper12th.setAspectRatio(2f);
        layoutHelper12th.setMargin(0, 0, 0, 0);
        layoutHelper12th.setWeights(new float[]{50});// 第一列占50%
        layoutHelper12th.setGap(5);// setVGap竖直 setHGap水平间距
        adapters.add(new SimpleVLayoutAdapter(this, layoutHelper12th, 20));

        // 最后将所有的adapter设置给delegate
        delegateAdapter.setAdapters(adapters);

    }

}
