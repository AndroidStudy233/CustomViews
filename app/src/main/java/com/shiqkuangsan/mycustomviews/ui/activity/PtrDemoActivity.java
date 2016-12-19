package com.shiqkuangsan.mycustomviews.ui.activity;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.base.BaseActivity;
import com.shiqkuangsan.mycustomviews.utils.Dp2PxUtil;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * Created by dell on 2016/8/11.
 */

/**
 * 测试PtrFrameLayout的界面
 */
public class PtrDemoActivity extends BaseActivity {

    private PtrFrameLayout mPtrFrameLayout;
    private ListView lv_ptrdemo;
    private PtrDataListAdapter adapter;

    @Override
    public void initView() {
        setContentView(R.layout.activity_ptrdemo);
    }

    @Override
    public void initDataAndListener() {
        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.ptr_frame);
        lv_ptrdemo = (ListView) findViewById(R.id.lv_ptrdemo);

        initListView();
        initPtrLayout();
    }

    private List<String> dataList = new ArrayList<>();

    private void initListView() {
        for (int i = 0; i < 10; i++) {
            dataList.add("我是数据" + i);
        }

        adapter = new PtrDataListAdapter();
        lv_ptrdemo.setAdapter(adapter);
    }

    public void initPtrLayout() {
        // header 设置头布局
        final MaterialHeader header = new MaterialHeader(this);
        int[] colors = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN};
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, Dp2PxUtil.dip2qx(this, 15), 0, Dp2PxUtil.dip2qx(this, 10));
        header.setPtrFrameLayout(mPtrFrameLayout);

        mPtrFrameLayout.setLoadingMinTime(1000);
        mPtrFrameLayout.setDurationToCloseHeader(1500);
        mPtrFrameLayout.setHeaderView(header);
        mPtrFrameLayout.addPtrUIHandler(header);
        mPtrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrameLayout.autoRefresh(true);
            }
        }, 100);

        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dataList.add("刷新数据" + dataList.size());
                        frame.refreshComplete();
                        adapter.notifyDataSetChanged();
                    }
                }, 1500);
            }
        });

    }

    @Override
    public void processClick(View view) {

    }

    /**
     * 模拟数据适配器
     */
    class PtrDataListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dataList == null ? 0 : dataList.size();
        }

        @Override
        public String getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(PtrDemoActivity.this, R.layout.item_ptrdemo_list, null);
            TextView tv_ptrlist = (TextView) view.findViewById(R.id.tv_ptrlist);
            tv_ptrlist.setText(dataList.get(position));
            return view;
        }
    }
}
