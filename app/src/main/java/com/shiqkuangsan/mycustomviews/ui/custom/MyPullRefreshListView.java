package com.shiqkuangsan.mycustomviews.ui.custom;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.utils.MyAnimationUtil;

/**
 * Created by shiqkuangsan on 2016/5/6.
 */

/**
 * 演示下拉刷新和下拉加载的界面
 */
public class MyPullRefreshListView extends ListView implements AbsListView.OnScrollListener {


    private View headView;
    private int topMeasuredHeight;
    private int footMeasueredHeight;
    private TextView tv_head_time;
    private TextView tv_head_head;
    private ImageView iv_arrow;
    private int paddingTop;
    private ProgressBar pb_loading;

    public MyPullRefreshListView(Context context) {
        super(context);
        init();
    }

    public MyPullRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyPullRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        initHeadView();

        initFootView();
        setOnScrollListener(this);
    }

    private View footView;

    /**
     * 给ListView添加脚布局
     */
    private void initFootView() {
        footView = View.inflate(getContext(), R.layout.layout_foot_listview, null);

        footView.measure(0, 0);
        footMeasueredHeight = footView.getMeasuredHeight();
        footView.setPadding(0, -footMeasueredHeight, 0, 0);
        addFooterView(footView);
    }

    /**
     * 给ListView添加头布局,并且设置初始化的时候默认头布局隐藏,然后根据下拉程度显示
     */
    private void initHeadView() {
        headView = View.inflate(getContext(), R.layout.layout_head_listview, null);
        tv_head_head = (TextView) headView.findViewById(R.id.tv_head_head);
        tv_head_time = (TextView) headView.findViewById(R.id.tv_head_time);
        iv_arrow = (ImageView) headView.findViewById(R.id.iv_arrow);
        pb_loading = (ProgressBar) headView.findViewById(R.id.pb_loading);

        /*
            这里注意了, 自定义控件的该方法在构造中执行的,构造又是在加载XML文件的时候执行的,加载xml文件又是在onCreate()中执行
            所以,这个时候onMeasure() / onDraw() 方法还没有执行,拿不到自定义控件的宽高,解决方法是进行测量
            int height = headView.getWidth();
         */
        // 该方法是测量控件自身的宽高, 参数给0,0意思是按照控件申明的时候规则,可以测量到自身的高度,但是本身显示的高度这时候还是0,因为还没渲染
        headView.measure(0, 0);
//        Log.info("measure: ", headView.getMeasuredWidth() + " --- " + headView.getMeasuredHeight());
        topMeasuredHeight = headView.getMeasuredHeight();
        // 给头布局设置内边距, 隐藏自身
        headView.setPadding(0, -topMeasuredHeight, 0, 0);
        addHeaderView(headView);
    }


    private float currentY;
    private static final int PULL_TO_REFRESH = 0;
    private static final int RELEASE_REFRESH = 1;
    private static final int REFRESHING = 2;
    private int mode = PULL_TO_REFRESH;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                currentY = ev.getY();
                break;

            case MotionEvent.ACTION_MOVE:

                if (mode == REFRESHING)
                    return super.onTouchEvent(ev);

                int moveY = (int) (ev.getY() - currentY);
                paddingTop = -topMeasuredHeight + moveY;

                // 往下拉才生效, 往上滑的时候该方法不必执行,还有就是如果当前可见条目不是第一个,该方法也不必执行
                if (moveY > 0 && getFirstVisiblePosition() == 0) {

                    headView.setPadding(0, paddingTop, 0, 0);

                    // 当正好头布局完全显示的时候,更新动画,旋转箭头,改变提示(这里来搞个模式记录变量)
                    if (paddingTop >= 0 && mode != RELEASE_REFRESH) {
                        // 进入松开刷新模式(如果模式没改变就不用调用该方法)
                        mode = RELEASE_REFRESH;
                        updateHeadView();

                    } else if (paddingTop < 0 && mode != PULL_TO_REFRESH) {
                        // 进入下拉刷新模式
                        mode = PULL_TO_REFRESH;
                        updateHeadView();
                    }
                    /*
                        如果这里不直接给该事件消费掉,手指移动到下一个位置的时候界面不会刷新
                     */
                    return true;
                }

                break;

            case MotionEvent.ACTION_UP:
                // 当你手松开的时候,如果当前头布局完全显示了,就是你松开刷新模式情况下你松手了,给你执行刷新
                if (mode == RELEASE_REFRESH) {
                    mode = REFRESHING;
                    headView.setPadding(0, 0, 0, 0);
                    updateHeadView();
                /*
                    这里又得注意了,如果不消费掉事件,没法继续下拉了
                 */
                } else if (mode == PULL_TO_REFRESH) {
                    // 如果没完全显示的话,就直接给你弹回去了
                    headView.setPadding(0, -topMeasuredHeight, 0, 0);
                }
                break;

        }

        invalidate();
        return super.onTouchEvent(ev);
    }


    /**
     * 根据当前模式刷新头布局的状态
     */
    private void updateHeadView() {
        switch (mode) {
            case PULL_TO_REFRESH:
                tv_head_head.setText("下拉刷新");
                MyAnimationUtil.rotateToBottom(iv_arrow);
                break;

            case RELEASE_REFRESH:
                tv_head_head.setText("松开刷新");
                MyAnimationUtil.rotateToTop(iv_arrow);
                break;

            case REFRESHING:
                tv_head_head.setText("正在刷新...");
                /*
                    又是一个注意点,这里执行动画之后如果不清除,将无法隐藏iv_arrow
                 */
                iv_arrow.clearAnimation();
                iv_arrow.setVisibility(View.INVISIBLE);
                pb_loading.setVisibility(View.VISIBLE);

                if (listener != null) {
                    listener.OnRefreshing();
                }
                break;

        }
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        // 如果你已经在加载了,就不能给你继续加载
        if (isLoadingMoreMode)
            return;

//      SCROLL_STATE_IDLE (空闲状态) / SCROLL_STATE_FLING (快速滑屏状态) / SCROLL_STATE_TOUCH_SCROLL(缓慢滑动状态)
        // 如果你滑到了最后面,而且松手了,我就给你弹出加载更多,并且调用接口方法,其方法具体逻辑由调用实现
        if (getLastVisiblePosition() >= getCount() - 1 && scrollState == SCROLL_STATE_IDLE) {
            isLoadingMoreMode = true;
            footView.setPadding(0, 0, 0, 0);
            // 设置选中的条目为最后一个,这样可以保证当你滑到最下边脚布局直接自动弹出来
            setSelection(getCount() - 1);
            if (listener != null)
                listener.onLoadingMore();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }


    private OnListViewRefreshListener listener;

    /**
     * 自定义的ListView进入刷新状态的时候提供的一个状态刷新监听接口
     */
    public interface OnListViewRefreshListener {
        void OnRefreshing();

        void onLoadingMore();
    }

    public void setOnListViewRefreshListener(OnListViewRefreshListener listener) {
        this.listener = listener;
    }


    public static boolean LOADING_MODE = true;
    public static boolean REFRESHING_MODE = false;
    private boolean isLoadingMoreMode = false;

    /**
     * 刷新结束的时候让外界调用的方法
     *
     * @param pullOrDrag true - 上拉加载更多执行的逻辑, false - 下拉刷新执行的逻辑
     */
    public void onRefreshCompleted(boolean pullOrDrag) {

        if (isLoadingMoreMode) {
            footView.setPadding(0, -footMeasueredHeight, 0, 0);
            isLoadingMoreMode = false;
        } else {
            mode = PULL_TO_REFRESH;
            tv_head_head.setText("下拉刷新");
            pb_loading.setVisibility(View.INVISIBLE);
            iv_arrow.setVisibility(View.VISIBLE);
            headView.setPadding(0, -topMeasuredHeight, 0, 0);
            tv_head_time.setText("上次刷新时间: " + DateFormat.getTimeFormat(getContext()).format(System.currentTimeMillis()));
        }
    }


}
