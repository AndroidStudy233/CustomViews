package com.shiqkuangsan.mycustomviews.ui.activity;

import android.os.SystemClock;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.base.BaseActivity;
import com.shiqkuangsan.mycustomviews.ui.custom.MyPullRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiqkuangsan on 2016/5/5.
 */
// 演示下拉刷新的界面
public class PullRefreshActivity extends BaseActivity {

    private List<String> list = new ArrayList<>();
    private MyPullRefreshListView mListView;
    private MyAdapter adapter;

    @Override
    public void initView() {
        setContentView(R.layout.activity_droprefresh);
        mListView = (MyPullRefreshListView) findViewById(R.id.listview_custom);
        // 设置滑动摩擦力可以改变ListView的滑动速度
        mListView.setFriction(ViewConfiguration.getScrollFriction() * 2);
    }

    @Override
    public void initDataAndListener() {
        // 模拟一些数据
        for (int x = 0; x < 40; x++) {
            list.add("模拟LsitView的数据: " + x);
        }

        // 适配器直接用内部类了
        adapter = new MyAdapter();
        mListView.setAdapter(adapter);
        mListView.setOnListViewRefreshListener(new MyPullRefreshListView.OnListViewRefreshListener() {
            @Override
            public void OnRefreshing() {
                new Thread() {
                    @Override
                    public void run() {
                        SystemClock.sleep(1500);
                        list.add(0, "我是上头出来的数据");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                mListView.onRefreshCompleted(MyPullRefreshListView.REFRESHING_MODE);
                            }
                        });
                    }
                }.start();
            }

            @Override
            public void onLoadingMore() {
                new Thread() {
                    @Override
                    public void run() {
                        SystemClock.sleep(1500);
                        list.add("我是下头出来的数据1");
                        list.add("我是下头出来的数据2");
                        list.add("我是下头出来的数据3");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                mListView.onRefreshCompleted(MyPullRefreshListView.LOADING_MODE);
                            }
                        });
                    }
                }.start();
            }
        });


    }

    @Override
    public void processClick(View view) {

    }


    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null)
                view = View.inflate(PullRefreshActivity.this, R.layout.item_listview, null);
            else
                view = convertView;

            TextView tv_lv = (TextView) view.findViewById(R.id.tv_lv);
            tv_lv.setText(list.get(position));
            return view;
        }
    }
}
