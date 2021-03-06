package com.shiqkuangsan.mycustomviews.ui.activity;

import android.os.SystemClock;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

import com.shiqkuangsan.mycustomviews.MyApplication;
import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.adapter.SimpleRecyclerAdapter;
import com.shiqkuangsan.mycustomviews.base.FrameBaseActivity;
import com.shiqkuangsan.mycustomviews.bean.ImgAndText;
import com.shiqkuangsan.mycustomviews.ui.custom.OnRecyclerItemTouchCallBack;
import com.shiqkuangsan.mycustomviews.utils.MyLogUtil;
import com.shiqkuangsan.mycustomviews.utils.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

import static com.shiqkuangsan.mycustomviews.R.color.swipe_schema_blue;

/**
 * Created by shiqkuangsan on 2016/10/13.
 *
 * @author shiqkuangsan
 * @summary 学习RecyclerView的界面, 重要是列表item的布局AutoRatioLayout, 实现根据
 * 具体宽度和比例(布局中属性定义)来达到自动计算高度的效果,图片展示使用CardView.
 * 添加了条目的侧滑移除功能和长按拖拽功能.
 */
@ContentView(R.layout.activity_recycler)
public class RecyclerViewActivity extends FrameBaseActivity {

    private Toolbar toolbar;
    @ViewInject(R.id.frame_recycler_main)
    private FrameLayout frame_main;
    private RecyclerView recycler_main;
    private SwipeRefreshLayout swipe_main;
    private SimpleRecyclerAdapter adapter;

    @Override
    public void initView() {
        // 页面主要承担了两个framlayout,一个是主页面,另一个是drawer
        x.view().inject(this);

        // ToolBar的基础上支持ActionBar,先在style文件中样式使用NoActionBar
        toolbar = (Toolbar) findViewById(R.id.toolbar_recycler);
        setSupportActionBar(toolbar);

        // ActionBar支持DrawerLayout
        DrawerLayout drawerlayout = (DrawerLayout) findViewById(R.id.drawer_recycler_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        toggle.syncState();
        drawerlayout.addDrawerListener(toggle);

        ToastUtil.toastShort(this, "单击双击, 侧滑拖拽");
    }

    @Override
    public void initDataAndListener() {
        // 填充主页面数据
        fillContentView();

        // 填充drawer页面数据
        fillDrawerView();
    }

    final long[] hits = new long[2];

    private void fillContentView() {
        View view = View.inflate(this, R.layout.layout_recycler_main, null);

        // 初始化SwipeRefreshLayout
        swipe_main = (SwipeRefreshLayout) view.findViewById(R.id.swipe_recycler_main);
        swipe_main.setColorSchemeResources(R.color.swipe_schema_red, swipe_schema_blue, R.color.swipe_schema_green);
        swipe_main.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MyApplication.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showToast("刷新成功~");
                        swipe_main.setRefreshing(false);
                    }
                }, 1500);
            }
        });

        // 初始化RecyclerView
        recycler_main = (RecyclerView) view.findViewById(R.id.recycler_recycler_main);
        // 设置布局管理者,采用线性布局管理者,竖直排列,不用反转(反转就是数据反序)
        recycler_main.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new SimpleRecyclerAdapter(this, new SimpleRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, ImgAndText bean) {
                /*
                    这里有个要注意的地方就是,在adapter中设置侦听的时候你要是用onBindViewHolder方法中的参数position
                    那么remove之后会position错乱BUG.这是RecyclerView的一个大坑...要用holder.getAdapterPosition传递
                 */
                System.arraycopy(hits, 1, hits, 0, hits.length - 1);
                hits[hits.length - 1] = SystemClock.uptimeMillis();
                if (hits[0] >= SystemClock.uptimeMillis() - 500) {
                    MyLogUtil.debug("position: " + position);
                    adapter.removeItem(position);
                } else {
                    ToastUtil.toastShort(RecyclerViewActivity.this, "点击了图片");
                }
            }
        });

        // 设置拖拽监听, 首先定义一个callback定义拖拽功能, callback中定义listener与adapter传递事件
        // 然后将callback传给helper, 并由helper绑定recycle
        ItemTouchHelper.Callback callback = new OnRecyclerItemTouchCallBack(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recycler_main);
        /*
            细节注意, 上述方式是通过长按条目来实现拖拽, 比如条目右边有个图标, 希望按住图标实现拖拽呢?
            adapter的holder中设置图标触摸事件setOnTouchListener. 这就需要在onTouch方法中调用上面helper的
            onStartDrag方法, 怎么调用呢? adapter定义一个接口OnIconDragHandler. 里边定义
            onHandleDrag(RecyclerView.ViewHolder viewHolder); 然后通过构造传进来.
            这样再让当前activity实现接口, 调用helper的onStartDrag方法. 然后new adapter的时候传过去.
            最后具体一下图标的ouTouch方法
             如果按下
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN){
                    回调RecyclerViewActivity中的onHandleDrag方法
                    让mItemTouchHelper执行拖拽操作
                    handler.startDrag(holder);
                }
         */

//        recycler_main.setAdapter(adapter);
        // 使用第三方库中简单的动画来设置适配器,其对象可以setDuration、setInterpolator,甚至可以继续传入形成装饰者
//        recycler_main.setAdapter(new ScaleInAnimationAdapter(adapter));
        recycler_main.setAdapter(new SlideInLeftAnimationAdapter(adapter));

        /*
            这里有个非常重要的问题,如果你想使用增删动画,那么在增删操作的时候
            你就不能使用notifyDataSetChanged,不然动画不会执行的,需要使用notifyItemRemoved(posi)、notifyItemInserted(posi)
         */
        // 设置条目动画,增删的时候会用到
        SlideInLeftAnimator animator = new SlideInLeftAnimator();
        animator.setInterpolator(new OvershootInterpolator());
        recycler_main.setItemAnimator(animator);
        // 将填充好的View放到主页面上
        frame_main.addView(view);
    }

    private void fillDrawerView() {
        // 预留
    }

    @Override
    public void processClick(View view) {

    }

}
