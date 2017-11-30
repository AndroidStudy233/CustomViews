package com.shiqkuangsan.mycustomviews.ui.custom;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

import com.shiqkuangsan.mycustomviews.R;

/**
 * Created by yu on 2017/10/31.
 */

public class DragHelperView extends LinearLayout {
    private ViewDragHelper viewDragHelper;
    private View viewScrollEvery;
    private View viewAutoBack;
    private View viewNotTouch;
    private int viewAutoBackOriginLeft;
    private int viewAutoBackOriginTop;
    private int touchSlop;

    public DragHelperView(Context context) {
        super(context);
        init(context);
    }

    public DragHelperView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DragHelperView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public DragHelperView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void init(Context context) {
        viewDragHelper = ViewDragHelper.create(this, new customViewDragHelperListener());
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        touchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(viewConfiguration);
        viewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);//设置ViewGroup左边缘可以被拖拽
//        viewDragHelper.smoothSlideViewTo() 这个方法可以移动view
    }


    class customViewDragHelperListener extends ViewDragHelper.Callback {
        /**
         * @desc: tryCaptureView：如果返回true表示捕获相关View，你可以根据第一个参数child决定捕获哪个View。
         **/
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == viewAutoBack || child == viewScrollEvery;
        }

        /**
         * @desc: clampViewPositionHorizontal：与clampViewPositionVertical类似，只不过是控制水平方向的位置。
         **/
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (left > getWidth()-child.getWidth()) {
                left =getWidth()-child.getWidth();
            } else if (left < 0) {
                left = 0;
            }
            return left;  //返回水平可拖动到达的最大边界  left 可以理解为拖动view的左上角x坐标
        }

        /**
         * @desc: clampViewPositionVertical：计算child垂直方向的位置，top表示y轴坐标（相对于ViewGroup），默认返回0（如果不复写该方法）。这里，你可以控制垂直方向可移动的范围。
         **/
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            if (top < 0) {
                top = 0;
            } else if (top > getHeight()-child.getHeight()) {
                top = getHeight()-child.getHeight();
            }
            return top;//返回垂直可拖动到达的最大边界     top 可以理解为拖动view的左上角y坐标
        }

        /**
         * @desc: onViewReleased方法会在被捕获的子View释放之后调用，我们判断释放的View：releasedChild是viewAutoBack，使用dragHelper.settleCapturedViewAt方法设置viewAutoBack的位置为它的初始位置。
         * 注意，此方法内部是通过Scroller实现的，所以我们需要使用invalidate来刷新，同时需要重写computeScroll方法：
         **/
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (releasedChild == viewAutoBack) {
                viewDragHelper.settleCapturedViewAt(viewAutoBackOriginLeft, viewAutoBackOriginTop);
                invalidate();
            }
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            //Capture a specific child view for dragging within the parent.
            // The callback will be notified but {@link Callback#tryCaptureView(android.view.View, int)} will not be asked permission to capture this view. (这个方法可以绕开tryCaptureView)
            viewDragHelper.captureChildView(viewNotTouch, pointerId);
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }


        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            super.onEdgeTouched(edgeFlags, pointerId);
        }

        @Override
        public boolean onEdgeLock(int edgeFlags) {
            return super.onEdgeLock(edgeFlags);
        }

        @Override
        public int getOrderedChildIndex(int index) {
            return super.getOrderedChildIndex(index);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return super.getViewHorizontalDragRange(child);
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return super.getViewVerticalDragRange(child);
        }
    }

    // 使用viewDragHelper需要处理ViewGroup触摸事件   两步（1 onInterceptTouchEvent 修改返回值，2 onTouchEvent 事件交给viewdraghelper 并且返回true）
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (viewDragHelper.continueSettling(true)) // dragHelper.continueSettling方法是用来判断当前被捕获的子View是否还需要继续移动，类似Scroller的computeScrollOffset方法一样，我们需要在返回true的时候使用invalidate刷新。
        {
            invalidate();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        viewScrollEvery = findViewById(R.id.view1);
        viewNotTouch = findViewById(R.id.view2);
        viewAutoBack = findViewById(R.id.view3);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        viewAutoBackOriginLeft = viewAutoBack.getLeft();
        viewAutoBackOriginTop = viewAutoBack.getTop();
    }
}
