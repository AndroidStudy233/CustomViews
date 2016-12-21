package com.shiqkuangsan.mycustomviews.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;


/**
 * Created by shiqkuangsan on 2016/5/6.
 */

/**
 * 演示带左侧滑菜单的界面
 */
public class SlideMenuView extends ViewGroup {

    private View leftMenu;
    private View main;
    private float startX;
    private float currentX;
    private Scroller scroller;

    public SlideMenuView(Context context) {
        super(context);
        init();
    }

    public SlideMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlideMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        leftMenu = getChildAt(0);
//        leftMenu.measure(0,0);        // 得到的测量宽高是 480 / 2268
//        leftMenu.measure(widthMeasureSpec, heightMeasureSpec);      // 得到的测量宽高是 720 / 1230

        // 参数的大概意思就是,测量规则是宽度按照自己来,高度按照父亲来,得到的结果也是我们希望看到的结果
        leftMenu.measure(0, heightMeasureSpec);     // 得到的测量宽高是 480 / 1230
        Log.i("measure: ", leftMenu.getMeasuredWidth() + " --- " + leftMenu.getMeasuredHeight());

        // 而主界面的测量我们希望都按照父控件来,就是匹配父元素
        main = getChildAt(1);
        main.measure(widthMeasureSpec, heightMeasureSpec);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 进行内容的绘制, 传递过来的参数依次是: 当前控件位置是否变化; 自定义的控件的左上右下的边距(左右距离左边框,上下距离上边框)
        // 我们希望把侧滑菜单画在屏幕左边距离自定义控件其菜单自身宽度的位置,主界面画在自定义控件的正面
        // 还是得注意,这个时候是拿不到它的宽度的,因为该方法执行完毕才绘制出该组件要用测量宽度
        leftMenu.layout(-leftMenu.getMeasuredWidth(), 0, 0, b);

        main.layout(l, t, r, b);
    }

    public static final int IN_MAIN_MODE = 0;
    public static final int IN_MENU_MODE = 1;
    private int current_mode;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 手指按下的时候记录当前的x位置
                startX = event.getX();
                break;

            case MotionEvent.ACTION_MOVE:
                /*
                    这里得注意,View类下的方法 scrollBy() 和 scrollTo( )的区别了. 先说scrollTo吧,他的意思是
                    滑动到,比如第一次调用给的参数10, 0  那么就会滑动到相对于原点 10,0 的位置,再调用给 20,0
                    那么就会滑动到相对于原点20,0的位置.

                    而scrollBy不一样,他会记录上次的位置,从上次的基础上滑动, 比如第一次调用给的参数是10,0,
                    滑动到相对于原点10,0的位置,再调用给参数 20,0   不一样了,他会滑动到 30 , 0 的位置再给-40,0 就是-10,0

                    最后要注意的是,调用滑动方法的对象是我们的控件,是控件在移动不是里面的内容在移动    (一定要注意正负)
                 */
                currentX = event.getX();
                int moveX = (int) (startX - currentX);
                // 获取当前滑动的距离,如果已经滑动的距离加上移动的距离超出左边菜单或者右边边界的宽度,不能让你再滑了
                int stopX = getScrollX() + moveX;
                if (stopX < -leftMenu.getMeasuredWidth())
                    scrollTo(-leftMenu.getMeasuredWidth(), 0);
                else if (stopX > 0)
                    scrollTo(0, 0);
                else
                    scrollBy(moveX, 0);

                startX = currentX;
                break;

            case MotionEvent.ACTION_UP:
                // 手指离开的时候,判断当前滚动的距离,如果大于菜单的一半,直接显示出菜单,小于就把菜单缩回去
                /*
                    这里又来个问题,秒滑回去了,怎么看着都不爽,希望它平缓的滑过去,这就是改进了,这就是新技术.来实现下
                    顺便搞个模式记录变量
                 */
                if (Math.abs(getScrollX()) > leftMenu.getMeasuredWidth() / 2) {
//                    scrollTo(-leftMenu.getMeasuredWidth(), 0);
                    current_mode = IN_MENU_MODE;
                    gentleScroll();
                } else {
//                    scrollTo(0, 0);
                    current_mode = IN_MAIN_MODE;
                    gentleScroll();
                }

                break;

        }

        return true;
    }

    /**
     * 平缓滑动的方法,该方法需要另一个重写方法computeScroll()的支持
     */
    private void gentleScroll() {
        /*
            scroller可以对滑动的动画进行模拟,然后结合computeScroll()方法不断互相调用实现平缓滑动
            其实该方法只是模拟拿到位置参数,真正实现滑动的方法还是后者实现的
         */
        int startX = getScrollX();
        int offsetX = 0;
        // 要从当前位置滑动到原点,其位移就是最终位置(0,0) - 当前已经滑动位置,注意区分正负的
        if (current_mode == IN_MAIN_MODE)
            offsetX = -startX;
            // 要从当前位置滑动到,其位移也是最终位置(-菜单宽) - 当前已经滑动位置,注意区分正负的
        else if (current_mode == IN_MENU_MODE)
            offsetX = -leftMenu.getMeasuredWidth() - startX;

        // 该方法可以模拟平滑滑动的动画,参数1,2: 是从哪个位置开始滑动  参数3,4: x,y各自的位移是多少,这里y是不用滑动的.
        // 总共持续时间: 这里持续时间不要写死,因为距离短,持续时间段,距离长持续时间长.offsetX 可能为负所以要用绝对值
        scroller.startScroll(startX, 0, offsetX, 0, Math.abs(offsetX * 3));
        // 就这在这里调用重新绘制界面的方法,computeScroll()方法就会执行
        invalidate();
    }


    @Override
    public void computeScroll() {
        super.computeScroll();

        // 在你设置的持续时间内该值一直为true
        if (scroller.computeScrollOffset()) {
            /*
                进行滑动,会从之前上面设置的模拟参数开始一点一点不断得到新的值,也就是时候其实所谓的scroller平缓滑动
                是将要滑动的距离拆分成很多步骤,然后依次连续执行,这样看起来平缓

                需要注意的是,该方法重新绘制界面的时候再次调用,而上面调用了 invalidate(); 而每次该方法执行完再调用
                 invalidate(); 这样又能重新绘制,于是此方法不断被调用,在设置的持续时间内不断得到新的 X 位移
              */
            scrollTo(scroller.getCurrX(), 0);

            invalidate();
        }
    }

    // 初始化动画模拟器,用来执行平缓滑动的动画的
    private void init() {
        scroller = new Scroller(getContext());
    }

    /**
     * 根据当前状态弹出还是收回侧滑菜单
     */
    public void changeState() {
        if (current_mode == IN_MENU_MODE) {
            current_mode = IN_MAIN_MODE;
            gentleScroll();
        } else {
            current_mode = IN_MENU_MODE;
            gentleScroll();
        }
    }

    public int getCurrent_Mode() {
        return current_mode;
    }

}
