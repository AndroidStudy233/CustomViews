package com.shiqkuangsan.mycustomviews.ui.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by shiqkuangsan on 2016/5/5.
 */

/**
 * 自定义开关控件
 */
public class MyOnOffView extends View {

    private Bitmap mswitch_bg;
    private Bitmap mswitch_slide;
    private Paint mpaint;
    private boolean mSwitchState = false;

    // 该构造一般用于代码创建
    public MyOnOffView(Context context) {
        super(context);

        init();
    }

    // 该构造用于组件内容xml加载的时候调用,其参数attrs就是自定义的属性,如果还引用了样式就走下面的构造
    public MyOnOffView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

        // 直接调用相应的方法拿到值,直接给设置上去
        int switch_bg = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res-auto", "switch_bg", -1);
        int switch_slide = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res-auto", "switch_slide", -1);
        boolean switch_state = attrs.getAttributeBooleanValue("http://schemas.android.com/apk/res-auto", "switch_state", false);

        setSwitchBackground(switch_bg);
        setSwitchSlideButton(switch_slide);
        setSwitchState(switch_state);
    }

    // 参数2:控件在布局文件中的所有属性都封装在这儿,参数3:style目录下的自定义属性
    public MyOnOffView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }


    // 在该方法中指定组件的宽高, 该方法和onDraw()都是在OnResume()方法之后调用
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 该组件的宽高就是开关背景的宽高,调用这个方法即可setMeasuredDimension()
        setMeasuredDimension(mswitch_bg.getWidth(), mswitch_bg.getHeight());
    }


    private boolean isSlideMode = false;

    // 在该方法中绘制组件的内容,只有该方法执行并且设置了内容界面上才会显示
    @Override
    protected void onDraw(Canvas canvas) {
//        Paint mpaint = new Paint();    // 绘制需要用到paint,android不建议在该方法中创建对象,去构造中初始化
        // pic1.绘制背景, 参数2,3: 以该组件为坐标系,左上角为原点,指定绘制的图片的x,y值
        canvas.drawBitmap(mswitch_bg, 0, 0, mpaint);

        // 2.绘制滑块,需要根据当前开关状态来绘制,开,滑块在右边;关,滑块在左边
        // 而如果用户在滑动的时候,绘制又得根据滑动的距离来定,搞个滑动模式的记录变量,然后这里分情况
        if (isSlideMode) {
//            float left = currentX;
            // 如果是这样的话,用户手指滑到哪儿,滑块的左上角就在哪儿,不好,我们让用户的手指在哪儿,滑块的中
            // 间位置就在哪儿. 好看.还有一个问题是会划出界面. 所以得限定一下范围
            float left = currentX - mswitch_slide.getWidth() / 2;
            if (left < 0)
                left = 0;
            if (left > (mswitch_bg.getWidth() - mswitch_slide.getWidth()))
                left = mswitch_bg.getWidth() - mswitch_slide.getWidth();

            canvas.drawBitmap(mswitch_slide, left, 0, mpaint);

        } else {
            if (mSwitchState) {
                float left = mswitch_bg.getWidth() - mswitch_slide.getWidth();
                canvas.drawBitmap(mswitch_slide, left, 0, mpaint);
            } else
                canvas.drawBitmap(mswitch_slide, 0, 0, mpaint);
        }
    }


    private float currentX;
    private OnSwitchStateChangeListener listener;

    // 重写触摸事件,响应用户的触摸
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentX = event.getX();
                isSlideMode = true;
                break;

            // 移动的时候重新绘制界面
            case MotionEvent.ACTION_MOVE:
                currentX = event.getX();
                break;

            // 抬起的时候看情况改变开关的状态
            case MotionEvent.ACTION_UP:
                currentX = event.getX();
                isSlideMode = false;
                // 如果最后抬起的位置大于背景的一半,开关打开,否则开关关闭
                boolean state = currentX > mswitch_bg.getWidth() / 2;
                // 把当前的状态返回给控件使用者.得加判断,首先listener不能为null,再一个状态没改变就不用调用该方法
                if (listener != null && mSwitchState != state)
                    listener.onStateChange(state);

                mSwitchState = state;
                break;
        }

        // 该操作非常重要了,因为上面已经绘制好了内容,该方法会重新调用onDraw()方法.
        invalidate();

        return true;
    }

    private void init() {
        mpaint = new Paint();
    }

    /**
     * 设置开关的背景图片,由调用者自己传进来
     *
     * @param swithBackground 需要设置的图片的资源id
     */
    public void setSwitchBackground(int swithBackground) {
        mswitch_bg = BitmapFactory.decodeResource(getResources(), swithBackground);
    }

    /**
     * 设置开关的滑块,由调用者自己传进来
     *
     * @param slideButton 需要设置的图片的资源id
     */
    public void setSwitchSlideButton(int slideButton) {
        mswitch_slide = BitmapFactory.decodeResource(getResources(), slideButton);
    }

    /**
     * 设置开关的初始状态
     *
     * @param state true - 开, false - 关
     */
    public void setSwitchState(boolean state) {
        mSwitchState = state;
    }


    // 给开关设置状态监听,让调用者拿到开关的状态
    public interface OnSwitchStateChangeListener {
        // 该方法在抬起的时候被调用
        void onStateChange(boolean state);
    }

    public void setOnSwitchStateChangeListener(OnSwitchStateChangeListener listener) {
        this.listener = listener;
    }
}
