package com.shiqkuangsan.testmodule;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/*************************************************
 * <p>版权所有：2016-深圳市赛为安全技术服务有限公司</p>
 * <p>项目名称：安全眼</p>
 * <p/>
 * <p>创建人：余志伟</p>
 * <p>创建时间 : 2016/12/14</p>
 * <p>修改人：       </p>
 * <p>修改时间：   </p>
 * <p>修改备注：   </p>
 *
 * @version V3.1
 *********************************/
public class BeSaier extends View implements View.OnClickListener {
    private Path path;
    private Paint pathPaint;
    private Paint pointPaint;
    private float centerY;
    //平移偏移量
    private float mOffset;
    //波长
    private int mWaveLength;
    Path linePath;
    Paint linePaint;
    private Path cPatch;
    private Paint clinePaint;
    public BeSaier(Context context) {
        super(context);
    }

    public BeSaier(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BeSaier(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerY = getHeight()/2f;
        mWaveLength = getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();
        path.moveTo(-mWaveLength + mOffset, centerY);
        path.quadTo(-mWaveLength * 3 / 4 + mOffset, centerY - 100f, -mWaveLength / 2 + mOffset, centerY);
        path.quadTo(-mWaveLength*1/4+mOffset,centerY+100f,mOffset,centerY);
        path.quadTo(mWaveLength*1/4+mOffset,centerY-100f,mWaveLength/2+ mOffset,centerY);
        path.quadTo(mWaveLength * 3 / 4 + mOffset, centerY + 100f, mWaveLength+ mOffset, centerY);
        path.lineTo(mWaveLength+ mOffset, getHeight());
        path.lineTo(-mWaveLength + mOffset, getHeight());
        path.close();
        canvas.drawPath(path, pointPaint);


    }

    public void init() {
        setOnClickListener(this);
        path = new Path();
        linePath = new Path();
        cPatch = new Path();
        pointPaint = new Paint();
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setColor(Color.BLUE);
        pointPaint.setStrokeWidth(15);
        pointPaint.setAntiAlias(true);
        pointPaint.setStrokeCap(Paint.Cap.ROUND);

    }
    @Override
    public void onClick(View view) {
        ValueAnimator animator = ValueAnimator.ofFloat(0,mWaveLength);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffset = (float) animation.getAnimatedValue();
                Log.e("mOffset:", mOffset + "");
                invalidate();
            }
        });
        animator.start();
    }
}
