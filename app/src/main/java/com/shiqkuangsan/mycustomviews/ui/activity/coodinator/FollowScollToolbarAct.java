package com.shiqkuangsan.mycustomviews.ui.activity.coodinator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.adapter.NormalRecyclerAdapter;
import com.shiqkuangsan.mycustomviews.ui.fragment.coordinator.MusicFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by shiqkuangsan on 2017/8/4. <p>
 * ClassName: FollowScollToolbarAct <p>
 * Author: shiqkuangsan <p>
 * Description: 跟随滚动的Toolbar demo.根布局采用CoordinatorLayout, 实现Toolbar的跟随滚动需要AppbarLayout,
 * 通过给他设置layout_scrollFlags(AppbarLayout子View才可以)属性, 或者代码中使用setScrollFlags()方法
 * 为了让AppBarLayout能够知道何时滚动其子View，必须要在CoordinatorLayout布局中提供一个可滚动View.
 * 如果界面用不到RecyclerView等列表的话, 有提供NestedScrollView. 这个界面有用到{@link DrawerEmmaInfoActivity}
 * 而此View的和AppbarLayout之间的关联通过给该View设置Behavior(layout_behavior属性)来决定<p>
 * Tip:
 * <p>1.滚动View -> ListView不生效. 一般都用RecyclerView
 * <p>2. CoordinatorLayout下使用FloatingActionButton会默认有个Behavior弹出SnackBar的时候上移
 */
@ContentView(R.layout.activity_coordinator_followedtoolbar)
public class FollowScollToolbarAct extends AppCompatActivity {

    @ViewInject(R.id.toolbar_coordinator_follow)
    Toolbar toolbar;
    @ViewInject(R.id.recycler_coordinator_follow)
    RecyclerView recyclerView;
    @ViewInject(R.id.fab_coordinator_follow)
    FloatingActionButton floatingBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        initRecyclerView();
        initFloatingButton();
    }

    private void initFloatingButton() {
        floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "I am coming ~", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        NormalRecyclerAdapter adapter = new NormalRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
    }

}
