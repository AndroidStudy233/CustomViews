package com.shiqkuangsan.mycustomviews.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.adapter.StickySideAdapter;
import com.shiqkuangsan.mycustomviews.adapter.itemdecoration.StickyHeaderDecoration;
import com.shiqkuangsan.mycustomviews.base.BaseActivity;
import com.shiqkuangsan.mycustomviews.utils.ToastUtil;
import com.shiqkuangsan.mycustomviews.utils.UIUitl;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * Created by shiqkuangsan on 2017/5/11.
 * <p>
 * ClassName: RecyclerSideslipSectionActivity
 * Author: shiqkuangsan
 * Description: 使用类库实现Recycler的侧滑删除和分组黏性头部效果
 */
@ContentView(R.layout.activity_recycler_sideslip_section)
public class RecyclerSideslipSectionActivity extends BaseActivity {

    @ViewInject(R.id.recycler_sideslip)
    LRecyclerView recycler_sideslip;
    @ViewInject(R.id.ptr_sideslip)
    PtrFrameLayout ptr_sideslip;

    private StickyHeaderDecoration decoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        initUI();
    }

    private void initUI() {
        // 不使用本身下拉刷新. 会有个小bug, 下拉刷新用的ptr包裹
        recycler_sideslip.setPullRefreshEnabled(false);

        DividerDecoration divider = new DividerDecoration.Builder(this)
                .setHeight(3.0f)
                .setPadding(0f)
                .setColor(0xff445566)
                .build();
        // item高度不变是时候设置这句可以提高性能
        recycler_sideslip.setHasFixedSize(true);
        recycler_sideslip.setLayoutManager(new LinearLayoutManager(this));
        recycler_sideslip.addItemDecoration(divider);

        StickySideAdapter stickyAdapter = new StickySideAdapter(this);
        decoration = new StickyHeaderDecoration(stickyAdapter);
        stickyAdapter.setOnSwipeMenuClickListener(new StickySideAdapter.OnSwipeMenuClick() {
            @Override
            public void onClickTop(RecyclerView.ViewHolder holder, int position) {
                String text = " 置顶 \n"
                        .concat("position: ")
                        .concat(String.valueOf(position))
                        .concat("\n")
                        .concat("adapterPosition: ")
                        .concat(String.valueOf(holder.getAdapterPosition()));
                ToastUtil.toastShort(RecyclerSideslipSectionActivity.this, text);
            }

            @Override
            public void onClickDelete(RecyclerView.ViewHolder holder, int position) {
                String text = " 侧滑 \n"
                        .concat("position: ")
                        .concat(String.valueOf(position))
                        .concat("\n")
                        .concat("adapterPosition: ")
                        .concat(String.valueOf(holder.getAdapterPosition()));
                ToastUtil.toastShort(RecyclerSideslipSectionActivity.this, text);
            }
        });

        LRecyclerViewAdapter adapter = new LRecyclerViewAdapter(stickyAdapter);
        recycler_sideslip.setAdapter(adapter);
        recycler_sideslip.addItemDecoration(decoration);

        initPtrFrameLayout();
    }

    private void initPtrFrameLayout() {
        // 更换下拉刷新头部
        MaterialHeader header = new MaterialHeader(this);
        int[] colors = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN};
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, UIUitl.dip2px(this, 15), 0, UIUitl.dip2px(this, 12));
        header.setPtrFrameLayout(ptr_sideslip);
        ptr_sideslip.setLoadingMinTime(1000);
        // mPtrFrameLayout.setDurationToCloseHeader(1500);
        ptr_sideslip.setHeaderView(header);
        ptr_sideslip.addPtrUIHandler(header);

        // 设置支持自动刷新
        ptr_sideslip.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptr_sideslip.autoRefresh(true);
            }
        }, 100);

        ptr_sideslip.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                // 刷新时需要处理的逻辑(自己的需求)
                new Handler(getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptr_sideslip.refreshComplete();
                    }
                }, 1000);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }
}
