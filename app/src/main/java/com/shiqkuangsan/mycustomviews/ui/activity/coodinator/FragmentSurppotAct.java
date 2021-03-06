package com.shiqkuangsan.mycustomviews.ui.activity.coodinator;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.adapter.NormalFragPagerAdapter;
import com.shiqkuangsan.mycustomviews.base.BaseActivity;
import com.shiqkuangsan.mycustomviews.ui.fragment.coordinator.BooksFragment;
import com.shiqkuangsan.mycustomviews.ui.fragment.coordinator.MovieFragment;
import com.shiqkuangsan.mycustomviews.ui.fragment.coordinator.MusicFragment;
import com.shiqkuangsan.mycustomviews.ui.fragment.coordinator.NewspaperFrament;
import com.shiqkuangsan.mycustomviews.ui.fragment.coordinator.PictureFrament;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Field;

/**
 * Created by shiqkuangsan on 2017/8/6.
 * <p>
 * ClassName: FragmentSurppotAct
 * Author: shiqkuangsan
 * Description: 比如有的App是一个主界面, 然后BottomBar做的, 其中只有首页和个人信息页需要沉浸+parallax <p>
 * tips: bug -> 如果viewpagerlimit为3, 那么movie界面回收再重建会高度bug
 */
@ContentView(R.layout.activity_fragment_parallax)
public class FragmentSurppotAct extends BaseActivity {

    @ViewInject(R.id.navigation_parallax)
    BottomNavigationView navigationView;
    @ViewInject(R.id.pager_parallax)
    ViewPager viewPager;

    String[] TITLES = {"Movies", "Music", "Picture", "Books", "Newspaper"};
    int[] COLORS = {R.color.colorPrimary, R.color.green, R.color.red, R.color.darkorchid, R.color.sienna};
    MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        initViewPager();
        initNavigation();
        // 初始选中第一个
        navigationView.setSelectedItemId(R.id.menu_movie);
        updateNavigation(0);
        disableShiftMode(navigationView);

        transparentStatus();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void transparentStatus() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            return;
        Window window = getWindow();
        View decorView = window.getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(option);
        window.setStatusBarColor(Color.TRANSPARENT);
//        window.setNavigationBarColor(Color.TRANSPARENT);
    }

    private void initViewPager() {
        viewPager.setOffscreenPageLimit(5);
        NormalFragPagerAdapter pagerAdapter = new NormalFragPagerAdapter(getSupportFragmentManager(), TITLES);
        pagerAdapter.addFragment(new MovieFragment());
        pagerAdapter.addFragment(new MusicFragment());
        pagerAdapter.addFragment(new PictureFrament());
        pagerAdapter.addFragment(new BooksFragment());
        pagerAdapter.addFragment(new NewspaperFrament());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    navigationView.getMenu().getItem(0).setChecked(false);
                }
                navigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = navigationView.getMenu().getItem(position);
                navigationView.setItemBackgroundResource(COLORS[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initNavigation() {
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int index = -1;
                switch (item.getItemId()) {
                    case R.id.menu_movie:
                        index = 0;
                        break;
                    case R.id.menu_music:
                        index = 1;
                        break;
                    case R.id.menu_picture:
                        index = 2;
                        break;
                    case R.id.menu_book:
                        index = 3;
                        break;
                    case R.id.menu_newspaper:
                        index = 4;
                        break;
                }
                updateNavigation(index);
                return true;
            }
        });
    }

    private void updateNavigation(int index) {
        navigationView.setItemBackgroundResource(COLORS[index]);
        viewPager.setCurrentItem(index);
    }

    /**
     * 关闭BottomNavigationView的切换动画
     *
     * @param view BottomNavigationView
     */
    private static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
