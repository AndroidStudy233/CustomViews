package com.shiqkuangsan.mycustomviews.ui.activity;

import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.adapter.PicPagerAdapter;
import com.shiqkuangsan.mycustomviews.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiqkuangsan on 2016/5/4.
 */
// 演示可以切换图片广告的界面
public class SwitchPictureActivity extends BaseActivity {

    private ViewPager pager;
    private TextView tv_msg;
    private PicPagerAdapter adapter;
    private List<View> list;
    private LinearLayout ll_point_container;
    private String[] msg = new String[]{
            "巩俐不低俗，我就不能低俗",
            "扑树又回来啦！再唱经典老歌引万人大合唱",
            "揭秘北京电影如何升级",
            "乐视网TV版大派送",
            "热血屌丝的反杀"
    };

    private boolean needRefresh = true;

    @Override
    public void initView() {
        setContentView(R.layout.activity_switchpicture);

        pager = (ViewPager) findViewById(R.id.viewpager_pics);
        tv_msg = (TextView) findViewById(R.id.tv_desc);
        ll_point_container = (LinearLayout) findViewById(R.id.ll_point_container);
        // 要给这个线性下面加几个小白点, 当前界面显示哪个图片,对应位置的小白点就变亮,
        // 小白点个数由图片的个数即上面的集合size决定,所以得在下面进行
    }

    //    private View[] pointViews;
    private int lastPosition = 0;

    @Override
    public void initDataAndListener() {
        list = new ArrayList<>();

        ImageView iv1 = new ImageView(this);
        iv1.setImageResource(R.drawable.a);
        list.add(iv1);
        ImageView iv2 = new ImageView(this);
        iv2.setImageResource(R.drawable.b);
        list.add(iv2);
        ImageView iv3 = new ImageView(this);
        iv3.setImageResource(R.drawable.c);
        list.add(iv3);
        ImageView iv4 = new ImageView(this);
        iv4.setImageResource(R.drawable.d);
        list.add(iv4);
        ImageView iv5 = new ImageView(this);
        iv5.setImageResource(R.drawable.e);
        list.add(iv5);


//        pointViews = new View[list.size()];
        View whitePoint;
        LinearLayout.LayoutParams params;
        for (int x = 0; x < list.size(); x++) {
            whitePoint = new View(this);
            // 背景界面自己去画一个形状,然后使用选择器,这里不是按钮状态按下与否,使用的状态是enable和disable
            whitePoint.setBackgroundResource(R.drawable.selector_bg_whitepoint);
            // 默认设置显示第一张图片,所以除了第一个点以外状态都是disable,
            if (x == 0)
                whitePoint.setEnabled(true);
            else
                whitePoint.setEnabled(false);

            // 然后设置布局参数,不然这些小白点都黏在一起,又得注意这是线性布局下的参数布局指明
            params = new LinearLayout.LayoutParams(8, 8);
            if (x != 0)
                params.leftMargin = 10;
            ll_point_container.addView(whitePoint, params);

//            pointViews[x] = whitePoint;
        }

        // 本例子中只有5个图片,我想滑到第5个的时候,继续滑动,怎么实现? 这就要去adapter中设置了
        adapter = new PicPagerAdapter(list);
        pager.setAdapter(adapter);
        pager.setCurrentItem(1000000);

        // 最后就是设置每隔2s自动跳转到下一个条目,需要开子线程,又不能注意刷新UI的问题,最后还得控制开始结束
        new Thread() {
            @Override
            public void run() {
                // 控制条件,界面销毁的时候取消任务
                while (needRefresh) {
                    SystemClock.sleep(3500);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pager.setCurrentItem(pager.getCurrentItem() + 1);
                        }
                    });
                }
            }
        }.start();


        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                // 让当前位置的小白点变亮, 如果用for循环做,每次都得循环,效率太低,搞个记录变量,由于适配器中实现无限循环
                // 这里必须都要调整位置
                tv_msg.setText(msg[position % list.size()]);
                ll_point_container.getChildAt(lastPosition).setEnabled(false);
                ll_point_container.getChildAt(position % list.size()).setEnabled(true);
                lastPosition = position % list.size();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        needRefresh = false;
    }


    @Override
    public void processClick(View view) {

    }

}
