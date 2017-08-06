package com.shiqkuangsan.mycustomviews.ui.activity.coodinator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.adapter.NormalFragPagerAdapter;
import com.shiqkuangsan.mycustomviews.ui.fragment.NormalSimpleFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by shiqkuangsan on 2017/8/4. <p>
 * ClassName: FollowScollToolbarAct <p>
 * Author: shiqkuangsan <p>
 * Description: 跟随滚动的Toolbar, 下面的TabLayout的tab却会固定, 其实就是AppBarLayout中的Toolbar有
 * ScrollFlag而TabLayout没有, 因此导致TabLayout粘性
 */
@ContentView(R.layout.activity_coordinator_stickytab)
public class StickyTabScrollToolbarAct extends AppCompatActivity {

    @ViewInject(R.id.toolbar_coordinator_stickytab)
    Toolbar toolbar;
    @ViewInject(R.id.tab_coordinator_stickytab)
    TabLayout tabLayout;
    @ViewInject(R.id.pager_coordinator_stickytab)
    ViewPager viewPager;

    String[] shiningTitles = new String[]{
            "富强", "民主", "文明", "和谐", "自由", "平等", "公正", "法治", "爱国", "敬业", "诚信", "友善"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        initToolbar();
        initTabLayout();
        setupViewPager();
    }

    private void initToolbar() {
        toolbar.setTitleTextAppearance(this, R.style.toolbarTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_coordinator, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // menuItem 需要的自定义设置的时候在该方法中进行, 下面方法中只能设置点击事件
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
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

    private void initTabLayout() {
        for (int i = 0; i < shiningTitles.length; i++) {
            String shiningTitle = shiningTitles[i];
            tabLayout.addTab(tabLayout.newTab().setText(shiningTitle));
        }
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(1).select();// 默认选中第2个页面
    }

    private void setupViewPager() {
        NormalFragPagerAdapter adapter = new NormalFragPagerAdapter(getSupportFragmentManager(), shiningTitles);
        for (int i = 0; i < shiningTitles.length; i++) {
            adapter.addFragment(NormalSimpleFragment.newInstance());
        }
        viewPager.setAdapter(adapter);
    }

}
