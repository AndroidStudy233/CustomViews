package com.shiqkuangsan.mycustomviews.ui.fragment.coordinator;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.adapter.NormalFragPagerAdapter;
import com.shiqkuangsan.mycustomviews.ui.fragment.NormalSimpleFragment;
import com.shiqkuangsan.mycustomviews.utils.ToastUtil;
import com.shiqkuangsan.mycustomviews.utils.UIUitl;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by shiqkuangsan on 2017/8/7. <p>
 * ClassName: MusicFragment <p>
 * Author: shiqkuangsan <p>
 * Description: Coordinator中的Music界面
 */
@ContentView(R.layout.fragment_parallax_music)
public class MusicFragment extends Fragment {

    @ViewInject(R.id.toolbar_parallax_music)
    Toolbar toolbar;
    @ViewInject(R.id.tab_parallax_music)
    TabLayout tabLayout;
    @ViewInject(R.id.pager_parallax_music)
    ViewPager viewPager;

    String[] shiningTitles = new String[]{
            "富强", "民主", "文明", "和谐", "自由", "平等", "公正", "法治", "爱国", "敬业", "诚信", "友善"
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        compatToolbar();
        initTabLayout();
        setupViewPager();
    }

    private void compatToolbar() {
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_parallax_music);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
            params.height = UIUitl.dip2px(getActivity(), 64);
            toolbar.setLayoutParams(params);
            toolbar.setPadding(0, UIUitl.dip2px(getActivity(), 20), 0, 0);
        }
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_parallax_search:
                        ToastUtil.toastShort(getActivity(), "search");
                        break;
                    case R.id.menu_parallax_scan:
                        ToastUtil.toastShort(getActivity(), "scan");
                        break;
                    case R.id.menu_parallax_share:
                        ToastUtil.toastShort(getActivity(), "share");
                        break;
                }
                return true;
            }
        });
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
        NormalFragPagerAdapter adapter = new NormalFragPagerAdapter(getActivity().getSupportFragmentManager(), shiningTitles);
        for (int i = 0; i < shiningTitles.length; i++) {
            adapter.addFragment(NormalSimpleFragment.newInstance());
        }
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_parallax_music, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_parallax_search:
                ToastUtil.toastShort(getActivity(), "search");
                break;
            case R.id.menu_parallax_scan:
                ToastUtil.toastShort(getActivity(), "scan");
                break;
            case R.id.menu_parallax_share:
                ToastUtil.toastShort(getActivity(), "share");
                break;
        }
        return true;
    }
}
