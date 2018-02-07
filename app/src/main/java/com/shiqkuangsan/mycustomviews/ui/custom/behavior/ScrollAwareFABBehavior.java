package com.shiqkuangsan.mycustomviews.ui.custom.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;

import com.shiqkuangsan.mycustomviews.utils.MyLogUtil;

/**
 * Created by shiqkuangsan on 2017/9/15. <p>
 * ClassName: ScrollAwareFABBehavior <p>
 * Author: shiqkuangsan <p>
 * Description: 自定义Behavior实现FAB跟随滚动显示隐藏
 */
public class ScrollAwareFABBehavior extends FloatingActionButton.Behavior {

    private boolean mIsAnimatingOut = false;

    public ScrollAwareFABBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(final CoordinatorLayout coordinatorLayout, final FloatingActionButton child,
                                       final View directTargetChild, final View target, final int nestedScrollAxes) {
        // 确定是在垂直方向上滑动
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(final CoordinatorLayout coordinatorLayout, final FloatingActionButton child,
                               final View target, final int dxConsumed, final int dyConsumed,
                               final int dxUnconsumed, final int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);

        if (dyConsumed > 0 && dyUnconsumed == 0) {
            MyLogUtil.info("上滑中。。。");
        }
        if (dyConsumed == 0 && dyUnconsumed > 0) {
            MyLogUtil.info("到边界了还在上滑。。。");
        }
        if (dyConsumed < 0 && dyUnconsumed == 0) {
            MyLogUtil.info("下滑中。。。");
        }
        if (dyConsumed == 0 && dyUnconsumed < 0) {
            MyLogUtil.info("到边界了，还在下滑。。。");
        }

        if (dyConsumed > 0 && !this.mIsAnimatingOut && child.getVisibility() == View.VISIBLE) {
            // 不显示FAB
            animateHide(child);
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            // 显示FAB
            animateShow(child);
        }
    }

    /**
     * 隐藏FAB
     *
     * @param fab 要执行动画的view
     */
    private void animateHide(View fab) {
        ViewCompat.animate(fab)
                .scaleX(0.0F)
                .scaleY(0.0F)
                .alpha(0.0F)
                .setDuration(800)
                .setInterpolator(new FastOutSlowInInterpolator())
                .withLayer()
                .setListener(new ViewPropertyAnimatorListener() {
                    public void onAnimationStart(View view) {
                        ScrollAwareFABBehavior.this.mIsAnimatingOut = true;
                    }

                    public void onAnimationCancel(View view) {
                        ScrollAwareFABBehavior.this.mIsAnimatingOut = false;
                    }

                    public void onAnimationEnd(View view) {
                        ScrollAwareFABBehavior.this.mIsAnimatingOut = false;
                        view.setVisibility(View.INVISIBLE);
                    }
                })
                .start();

    }

    /**
     * 显示FAB
     *
     * @param fab 要执行动画的view
     */
    private void animateShow(View fab) {
        fab.setVisibility(View.VISIBLE);
        ViewCompat.animate(fab)
                .scaleX(1.0F)
                .scaleY(1.0F)
                .alpha(1.0F)
                .setDuration(800)
                .setInterpolator(new FastOutSlowInInterpolator())
                .withLayer()
                .setListener(null)
                .start();
    }
}
