package com.shiqkuangsan.qqspecialeffects;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shiqkuangsan.qqspecialeffects.custom.MyListView;
import com.shiqkuangsan.qqspecialeffects.custom.SwipeLayout;
import com.shiqkuangsan.qqspecialeffects.custom.SwipeLayoutManager;

import java.util.ArrayList;

/**
 * QQ特效之侧滑菜单、删除条目、ListView头部放大
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MyListView lv_main;
    private MyAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initData();
        initView();

    }

    ArrayList<String> dataList = new ArrayList<>();

    private void initData() {
        for (int i = 0; i < 10; i++) {
            String s = "我是条目" + i;
            dataList.add(s);
        }
    }


    private void initView() {
//        ImageView iv_toggle = (ImageView) findViewById(R.id.iv_titlebar_menu);
//        iv_toggle.setOnClickListener(this);

        lv_main = (MyListView) findViewById(R.id.lv_main);
        adapter = new MyAdapter();

        // 设置滑动到顶部继续滑没有蓝色阴影
        lv_main.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
        lv_main.setAdapter(adapter);
        lv_main.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    SwipeLayoutManager.getInstance().closeCurrentLayout();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        lv_main.setOnListViewRefreshListener(new MyListView.OnListViewRefreshListener() {
            @Override
            public void onRefreshing() {
                handler.sendEmptyMessageDelayed(1, 1500);
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            lv_main.onRefreshCompleted();
            dataList.add("我是条目" + dataList.size());
            adapter.notifyDataSetChanged();
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder;
            if (convertView == null) {
                view = View.inflate(MainActivity.this, R.layout.item_list, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }

            holder.tv_name.setText(dataList.get(position));
            holder.tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("我还是能点的");
                }
            });
            holder.iv_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("我特么也是能点的");
                }
            });
            holder.custom_swipelayout.setTag(position); // 通过setTag()可以将数据传递,在监听中获取到

            return view;
        }

        class ViewHolder {
            private final TextView tv_name;
            private final TextView tv_up;
            private final TextView tv_delete;
            private final SwipeLayout custom_swipelayout;
            private final ImageView iv_icon;

            public ViewHolder(View view) {
                iv_icon = (ImageView) view.findViewById(R.id.iv_item_icon);
                tv_name = (TextView) view.findViewById(R.id.tv_item_name);
                tv_up = (TextView) view.findViewById(R.id.tv_item_up);
                tv_up.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("置顶了");
                    }
                });
                tv_delete = (TextView) view.findViewById(R.id.tv_item_delete);
                tv_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("删除啪啪啪");
                    }
                });

                custom_swipelayout = (SwipeLayout) view.findViewById(R.id.custom_swipelayout);
                custom_swipelayout.setOnSwipeStateChangeListener(new SwipeLayout.OnSwipeStateChangeListener() {
                    @Override
                    public void onOpen(Object tag) {
                        showToast("第" + (Integer) tag + "打开了");
                    }

                    @Override
                    public void onClose(Object tag) {
                        showToast("第" + (Integer) tag + "关闭了");
                    }
                });
            }
        }
    }

    private Toast toast;

    private void showToast(String msg) {
        if (toast == null)
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        else
            toast.setText(msg);
        toast.show();
    }
}
