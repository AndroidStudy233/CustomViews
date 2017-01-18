package com.shiqkuangsan.mycustomviews.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;

import com.nineoldandroids.animation.ValueAnimator;
import com.shiqkuangsan.mycustomviews.R;

/**
 * Created by shiqkuangsan on 2017/1/18.
 * <p>
 * ClassName: ScaleHeadListView
 * Author: shiqkuangsan
 * Description: 实现头条目下拉放大效果的ListView,参考QQ空间,不过QQ空间是橡皮筋拉伸
 * 需要设置下拉刷新接口,刷新完成调用onRefreshCompleted()方法
 */
public class ScaleHeadListView extends ListView {

    private float deltaY;
    /**
     * 下拉到该距离才触发刷新
     */
    public static final int PIXELS_REFRESH = 350;

    public ScaleHeadListView(Context context) {
        super(context);
        init();
    }

    public ScaleHeadListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScaleHeadListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private ImageView iv_progress;

    public void init() {
        View headerView = View.inflate(getContext(), R.layout.layout_qq_headerview, null);
        iv_progress = (ImageView) headerView.findViewById(R.id.iv_progress);
        addHeaderView(headerView);
        ImageView iv_header = (ImageView) headerView.findViewById(R.id.iv_header);
        setmImageView(iv_header);
    }

    private ImageView iv_header;
    /**
     * 可以拖动的最大高度
     */
    private int maxHeight;
    /**
     * 图片的原始高度
     */
    private int srcHeight;
    /**
     * 盛放图片的ImageView的高度
     */
    private int ivHeight;

    /**
     * 给ListView设置ImageView,目的是获取到ImageView的参数
     *
     * @param iv_header 头部ImageView
     */
    public void setmImageView(final ImageView iv_header) {
        this.iv_header = iv_header;

        // 获取ImageView高度,该方法可能获取不到高度.解决办法是添加观察者,或者在布局中使用dimens
        // int ivHeight = iv_header.getHeight();
        iv_header.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                iv_header.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                // 获取ImageView高度
                ivHeight = iv_header.getHeight();
                // 获取图片原始高度
                srcHeight = iv_header.getDrawable().getIntrinsicHeight();
                // 如果ImageView高度(布局中写的200dp)比原图大,那最大高度就设置为ImageView的2倍(400dp),不然就以图片最大高度
                maxHeight = ivHeight > srcHeight ? ivHeight * 2 : srcHeight;
            }
        });

    }

    /**
     * 该方法在ListView滑动到上头或者下头的时候调用该方法
     *
     * @param deltaX         继续滑动的时候x方向的变化量
     * @param deltaY         继续滑动的时候y方向的变化量,正值表示滑动到底部继续滑,负值是滑动到头部继续滑
     * @param scrollX        Current X scroll value in pixels before applying deltaX
     * @param scrollY
     * @param scrollRangeX   Maximum content scroll range along the X axis scrollX
     * @param scrollRangeY
     * @param maxOverScrollX x方向可以滚动的最大距离
     * @param maxOverScrollY y方向可以滚动的最大距离
     * @param isTouchEvent   true-当前是手指滑动,false-当前是惯性滑动
     * @return
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

        // 如果是手指滑动到顶部了,继续滑动的时候,给头iv_header设置高度,由于iv_header是centerCrop的,增加高度就会自动裁剪
        if (iv_header != null && deltaY < 0 && isTouchEvent) {
            int newHeight = iv_header.getHeight() + (-deltaY / 3);    // △Y/3 这样更好看
            // 定义个最大高度,不能让你一直增大
            if (newHeight > maxHeight)
                newHeight = maxHeight;
            iv_header.getLayoutParams().height = newHeight;
            iv_header.requestLayout();
        }

        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY,
                maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    /**
     * 准备刷新模式,松开即可刷新
     */
    private static final int MODE_READY = 0;
    /**
     * 正在刷新模式,防止重复执行刷新
     */
    private static final int MODE_REFRESHING = 1;
    /**
     * 空闲模式,一般情况
     */
    private static final int MODE_IDLE = 2;
    private float startY = -1;
    /**
     * 当前模式
     */
    private int currentMode = -1;
    /**
     * 监听接口
     */
    private OnListViewRefreshListener listener;

    /**
     * 从头部图片下拉才可以实现下拉刷新
     */
    private boolean isClickOnHeader = false;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = ev.getRawY();

                if (ev.getY() <= iv_header.getBottom())
                    isClickOnHeader = true;
                break;

            case MotionEvent.ACTION_MOVE:
                deltaY = ev.getRawY() - startY;
                if (currentMode == MODE_REFRESHING)
                    return super.onTouchEvent(ev);
                if (isClickOnHeader) {
                    if (deltaY > PIXELS_REFRESH && getFirstVisiblePosition() == 0) {
                        iv_progress.setVisibility(View.VISIBLE);
                        currentMode = MODE_READY;
                    } else {
                        currentMode = MODE_IDLE;
                        iv_progress.clearAnimation();
                        iv_progress.setVisibility(View.GONE);
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                recoverImage();
                if (currentMode == MODE_REFRESHING)
                    break;
                if (currentMode == MODE_READY) {
                    currentMode = MODE_REFRESHING;
                    rotateProgress(iv_progress);
                    if (listener != null)
                        listener.onRefreshing();
                }
                break;
        }
        invalidate();
        return super.onTouchEvent(ev);
    }

    /**
     * 刷新完成
     */
    public void onRefreshCompleted() {
        currentMode = MODE_IDLE;
        iv_progress.clearAnimation();
        iv_progress.setVisibility(View.GONE);
        isClickOnHeader = false;
    }

    /**
     * loading条旋转动画
     *
     * @param view custom
     */
    public void rotateProgress(View view) {
        RotateAnimation animation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        animation.setRepeatCount(Animation.INFINITE);
        view.setAnimation(animation);
    }

    /**
     * 回复ImageView到原来的样子
     */
    public void recoverImage() {
        // 恢复布局中写的ImageView高度,执行一个属性动画,从当前高度到布局中原来ImageView的高度
        final ValueAnimator animator = ValueAnimator.ofInt(iv_header.getHeight(), ivHeight);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (Integer) animator.getAnimatedValue();
                // 逐渐恢复到原始高度
                iv_header.getLayoutParams().height = value;
                iv_header.requestLayout();
            }
        });
        // 加一个回弹插补器
        animator.setInterpolator(new OvershootInterpolator(1.5f));
        animator.setDuration(500);
        animator.start();
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
    }

    public interface OnListViewRefreshListener {
        void onRefreshing();
    }

    public void setOnListViewRefreshListener(OnListViewRefreshListener listener) {
        this.listener = listener;
    }
}
