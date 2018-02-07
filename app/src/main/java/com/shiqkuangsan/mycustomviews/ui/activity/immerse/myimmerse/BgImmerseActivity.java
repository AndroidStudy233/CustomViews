package com.shiqkuangsan.mycustomviews.ui.activity.immerse.myimmerse;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.base.BaseActivity;
import com.shiqkuangsan.mycustomviews.utils.MyLogUtil;
import com.shiqkuangsan.mycustomviews.utils.MyStatusBarUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by shiqkuangsan on 2016/10/20.
 *
 * @author shiqkuangsan
 * @summary 背景沉浸式页面, 主题NoActionBar, 外加实现ToolBar实现搜索框, 并且关联ListView
 */
public class BgImmerseActivity extends BaseActivity implements SearchView.OnQueryTextListener {

    private ListView lv_test;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bg_immerse);
        initTestListView();

        initToolBar();

        MyStatusBarUtil.setStatusTransparent(this, false);
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    List<String> dataList = new ArrayList<>();
    List<String> tempList;

    private void initTestListView() {
        lv_test = (ListView) findViewById(R.id.lv_bgimmerse_test);
        for (int i = 0; i < 30; i++) {
            dataList.add("测试数据" + i);
            dataList.add("测试数据" + i);
        }
        tempList = new ArrayList<>(dataList);
        lv_test.setAdapter(new TestAdapter());

    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_bgimmerse);
//        toolbar.setNavigationIcon(R.mipmap.icon_btn_back);//设置导航栏图标
//        toolbar.setLogo(R.mipmap.ic_launcher);//设置 logo
        toolbar.setTitle("背景沉浸式");// 设置标题
        toolbar.setTitleTextColor(Color.WHITE);// 标题颜色
//        toolbar.setSubtitle("Subtitle");//设置子标题
//        toolbar.inflateMenu(R.menu.menu_bgimmerse);// 填充右侧菜单

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bgimmerse, menu);
        // 初始化搜索框,设置侦听
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_bgimmerse_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
        return true;
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        MyLogUtil.debug("输入文字: " + newText);
        // 如果是空还原数据
        if (TextUtils.isEmpty(newText.trim()))
            dataList = new ArrayList<>(tempList);
        else {
            dataList.clear();
            for (int i = 0; i < tempList.size(); i++) {
                String data = tempList.get(i);
                if (data.contains(newText.trim()))
                    dataList.add(data);
            }
        }
        BaseAdapter adapter = (BaseAdapter) lv_test.getAdapter();
        adapter.notifyDataSetChanged();
        lv_test.setSelection(0);
        return true;
    }


    class TestAdapter extends BaseAdapter {

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
            View view;
            ViewHolder holder;
            if (convertView == null) {
                view = getLayoutInflater().inflate(R.layout.item_bgimmerse_testlist, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            holder.iv_logo.setImageResource(R.mipmap.img_oumu_logo2);
            holder.iv_apple.setImageResource(R.mipmap.img_apple_logo2);
            holder.tv_big.setText(dataList.get(position));
            holder.tv_small.setText(dataList.get(position));
            return view;
        }

        class ViewHolder {
            private final ImageView iv_logo;
            private final ImageView iv_apple;
            private final TextView tv_big;
            private final TextView tv_small;

            public ViewHolder(View view) {
                iv_logo = (ImageView) view.findViewById(R.id.iv_immerse_itemlogo);
                tv_big = (TextView) view.findViewById(R.id.tv_immerse_itembig);
                tv_small = (TextView) view.findViewById(R.id.tv_immerse_itemsmall);
                iv_apple = (ImageView) view.findViewById(R.id.iv_immerse_itemapple);
            }
        }
    }
}
