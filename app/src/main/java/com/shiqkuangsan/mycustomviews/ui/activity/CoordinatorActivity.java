package com.shiqkuangsan.mycustomviews.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.ui.fragment.DetailFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiqkuangsan on 2016/9/28.
 *
 * @author shiqkaungsan
 * @summary CoordinatorLayout测试界面, 头部显示图片, 上滑显示ToolBar, 下拉显示图片
 * 侧滑显示DrawerLayout,drawer采用NavigationView
 */
public class CoordinatorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager mViewPager;
    protected Toast toast;
    private DrawerLayout drawerlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_coordinator);
        setSupportActionBar(toolbar);
        // 显示返回键并响应
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View custom) {
//                onBackPressed();
//            }
//        });

        // ActionBar支持DrawerLayout
        drawerlayout = (DrawerLayout) findViewById(R.id.drawer_coordinator_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        toggle.syncState();
        drawerlayout.addDrawerListener(toggle);

        // NavigationView设置条目点击侦听
        NavigationView navigation_drawer =  (NavigationView) findViewById(R.id.navigation_drawer);
        navigation_drawer.setNavigationItemSelectedListener(this);

        // Collapsing标题
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("艾玛 · 沃特森");
        // 没收缩时的字体颜色,显示在ToolBar后的字体颜色
//        collapsingToolbar.setExpandedTitleTextColor();
//        collapsingToolbar.setCollapsedTitleTextColor();

        // 头部背景图
        ImageView ivImage = (ImageView) findViewById(R.id.iv_coordinator_head);
        ivImage.setImageResource(R.mipmap.emma_waston);

        // 初始化ViewPager,绑定TabLayout
        mViewPager = (ViewPager) findViewById(R.id.viewpager_coordinator);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout_emma_intro);
        tabLayout.addTab(tabLayout.newTab().setText("人物简介"));
        tabLayout.addTab(tabLayout.newTab().setText("作品简介"));
        tabLayout.addTab(tabLayout.newTab().setText("其他相关"));
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(1).select();// 默认选中第2个页面
    }

    private void setupViewPager(ViewPager mViewPager) {
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(DetailFragment.newInstance(getResources().getString(R.string.emma_introduction)), "人物简介");
        adapter.addFragment(DetailFragment.newInstance(getResources().getString(R.string.emma_movie_intro)), "作品简介");
        adapter.addFragment(DetailFragment.newInstance(getResources().getString(R.string.emma_extra)), "其他相关");
        mViewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 加载菜单
        getMenuInflater().inflate(R.menu.menu_coordinator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 根据不同的id设置响应事件
        switch (item.getItemId()) {
            case R.id.menu_coordinator_1:
                break;
            case R.id.menu_coordinator_2:
                break;
            case R.id.menu_coordinator_3:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_night:
                showToast("夜间模式");
                break;
            case R.id.navigation_abandon:
                showToast("勿扰模式");
                break;
            case R.id.navigation_share:
                showToast("分享应用");
                break;
            case R.id.navigation_delete:
                showToast("点击删除");
                break;
            case R.id.navigation_help:
                showToast("点击帮助");
                break;
            case R.id.navigation_settings:
                showToast("点击设置");
                break;
        }

        drawerlayout.closeDrawer(GravityCompat.START);

        return true;
    }

    protected void showToast(String msg) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(this, msg + "", Toast.LENGTH_SHORT);
        toast.show();
    }

    static class MyPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
