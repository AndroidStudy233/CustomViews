package com.shiqkuangsan.mycustomviews.ui.fragment.coordinator;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.adapter.NormalFragPagerAdapter;
import com.shiqkuangsan.mycustomviews.adapter.NormalRecyclerAdapter;
import com.shiqkuangsan.mycustomviews.adapter.itemdecoration.NormalLineDecoration;
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
@ContentView(R.layout.fragment_parallax_book)
public class BooksFragment extends Fragment {

    @ViewInject(R.id.toolbar_parallax_book)
    Toolbar toolbar;
    @ViewInject(R.id.recycler_parallax_book)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        compatToolbar();
        initRecyclerView();
    }

    private void compatToolbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) toolbar.getLayoutParams();
            params.height = UIUitl.dip2px(getActivity(), 64);
            toolbar.setLayoutParams(params);
            toolbar.setPadding(0, UIUitl.dip2px(getActivity(), 20), 0, 0);
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        NormalRecyclerAdapter adapter = new NormalRecyclerAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        NormalLineDecoration.enableDrawOver = false; // 是否观看DrawOver方法的效果
        NormalLineDecoration lineDecoration = new NormalLineDecoration(getActivity(), LinearLayoutManager.VERTICAL, 3, 0xffE4E1E9);
        recyclerView.addItemDecoration(lineDecoration);
    }

}
