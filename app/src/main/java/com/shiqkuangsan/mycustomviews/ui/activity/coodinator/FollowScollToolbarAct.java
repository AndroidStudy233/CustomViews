package com.shiqkuangsan.mycustomviews.ui.activity.coodinator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.adapter.NormalRecyclerAdapter;
import com.shiqkuangsan.mycustomviews.base.BaseActivity;

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
public class FollowScollToolbarAct extends BaseActivity {

    @ViewInject(R.id.toolbar_coordinator_follow)
    Toolbar toolbar;
    @ViewInject(R.id.recycler_coordinator_follow)
    RecyclerView recyclerView;
    @ViewInject(R.id.fab_coordinator_follow)
    FloatingActionButton floatingBtn;

    private LinearLayoutManager layoutManager;

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
//                Snackbar.make(view, "Fly to position 0 ~", Snackbar.LENGTH_SHORT)
//                        .setAction("Action", null).show();
//                layoutManager.scrollToPositionWithOffset(0, 0);
//                layoutManager.setStackFromEnd(true);
                smoothMoveToPosition(recyclerView, 0);
            }
        });
    }

    private void initRecyclerView() {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        NormalRecyclerAdapter adapter = new NormalRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        // 控制FAB点击滑动(这里是滑动到0所以无所谓  但是要是滑动到后面的位置就要这样做)
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mShouldScroll) {
                    mShouldScroll = false;
                    smoothMoveToPosition(recyclerView, mToPosition);
                }
            }
        });
    }

    private boolean isInitializeFAB = false;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!isInitializeFAB) {
            isInitializeFAB = true;
            // 进入界面的时候隐藏FAB.
//            hideFAB();
        }
    }

    private void hideFAB() {
        floatingBtn.postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewCompat.animate(floatingBtn)
                        .scaleX(0.0f)
                        .scaleY(0.0f)
                        .alpha(0.0f)
                        .setDuration(500)
                        .setInterpolator(new FastOutSlowInInterpolator())
                        .setListener(new ViewPropertyAnimatorListener() {
                            @Override
                            public void onAnimationStart(View view) {

                            }

                            @Override
                            public void onAnimationEnd(View view) {
                                floatingBtn.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationCancel(View view) {

                            }
                        })
                        .start();
            }
        }, 500);
    }

    /**
     * 目标项是否在最后一个可见项之后
     */
    private boolean mShouldScroll;
    /**
     * 记录目标项位置
     */
    private int mToPosition;

    /**
     * 滑动到指定位置
     *
     * @param mRecyclerView
     * @param position
     */
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));

        if (position < firstItem) {
            // 如果跳转位置在第一个可见位置之前，就smoothScrollToPosition可以直接跳转
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 跳转位置在第一个可见项之后，最后一个可见项之前
            // smoothScrollToPosition根本不会动，此时调用smoothScrollBy来滑动到指定位置
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 如果要跳转的位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，执行上一个判断中的方法
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }
}
