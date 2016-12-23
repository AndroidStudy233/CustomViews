package com.shiqkuangsan.mycustomviews.ui.custom.photoview;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.animation.TranslateAnimation;

/**
 * 滑到左右两端可以继续滑一点点的ViewPager
 * 使用的时候只要调用下面两个方法就可以了
 * setpagerCount()      请在初始化的时候调用它
 * setCurrentIndex()    请在onPageSelect方法中调用它
 */
public class OverScrollViewPager extends ViewPager {

    private Rect mRect = new Rect();//用来记录初始位置
    private int pagerCount = 3;
    private int currentItem = 0;
    private boolean handleDefault = true;
    private float preX = 0f;
    private static final float FRICTION = 0.5f;//摩擦系数
    private static final float SCROLL_WIDTH = 30f;

    public OverScrollViewPager(Context context) {
        super(context);
    }

    public OverScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //设置总共有多少页,请记得调用它
    public void setpagerCount(int pagerCount) {
        this.pagerCount = pagerCount;
    }

    //这是当前是第几页，请在onPageSelect方法中调用它。
    public void setCurrentIndex(int currentItem) {
        this.currentItem = currentItem;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (arg0.getAction() == MotionEvent.ACTION_DOWN) {
            preX = arg0.getX();//记录起点
        }
        return super.onInterceptTouchEvent(arg0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        switch (arg0.getAction()) {
            case MotionEvent.ACTION_UP:
                onTouchActionUp();
                break;
            case MotionEvent.ACTION_MOVE:
                //当时滑到第一项或者是最后一项的时候。
                if ((currentItem == 0 || currentItem == pagerCount - 1)) {
                    float nowX = arg0.getX();
                    float offset = nowX - preX;
                    preX = nowX;
                    if (currentItem == 0) {
                        if (offset > SCROLL_WIDTH) {//手指滑动的距离大于设定值
                            whetherConditionIsRight(offset);
                        } else if (!handleDefault) {//这种情况是已经出现缓冲区域了，手指慢慢恢复的情况
                            if (getLeft() + (int) (offset * FRICTION) >= mRect.left) {
                                layout(getLeft() + (int) (offset * FRICTION), getTop(), getRight() + (int) (offset * FRICTION), getBottom());
                            }
                        }
                    } else {
                        if (offset < -SCROLL_WIDTH) {
                            whetherConditionIsRight(offset);
                        } else if (!handleDefault) {
                            if (getRight() + (int) (offset * FRICTION) <= mRect.right) {
                                layout(getLeft() + (int) (offset * FRICTION), getTop(), getRight() + (int) (offset * FRICTION), getBottom());
                            }
                        }
                    }
                } else {
                    handleDefault = true;
                }

                if (!handleDefault) {
                    return true;
                }
                break;

            default:
                break;
        }
        return super.onTouchEvent(arg0);
    }

    private void whetherConditionIsRight(float offset) {
        if (mRect.isEmpty()) {
            mRect.set(getLeft(), getTop(), getRight(), getBottom());
        }
        handleDefault = false;
        layout(getLeft() + (int) (offset * FRICTION), getTop(), getRight() + (int) (offset * FRICTION), getBottom());
    }

    private void onTouchActionUp() {
        if (!mRect.isEmpty()) {
            recoveryPosition();
        }
    }

    private void recoveryPosition() {
        TranslateAnimation ta = null;
        ta = new TranslateAnimation(getLeft(), mRect.left, 0, 0);
        ta.setDuration(300);
        startAnimation(ta);
        layout(mRect.left, mRect.top, mRect.right, mRect.bottom);
        mRect.setEmpty();
        handleDefault = true;
    }
}