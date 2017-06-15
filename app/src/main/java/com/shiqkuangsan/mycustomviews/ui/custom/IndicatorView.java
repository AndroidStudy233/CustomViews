package com.shiqkuangsan.mycustomviews.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.shiqkuangsan.mycustomviews.R;

import java.util.ArrayList;
import java.util.List;


/*************************************************
 * <p>创建人：余志伟</p>
 * <p>创建时间 : 2017/5/26</p>
 * <p>修改人：       </p>
 * <p>修改时间：   </p>
 * <p>修改备注：   </p>
 *
 * @version V3.1
 *********************************/
public class IndicatorView extends View {
    private Paint selectCirclePaint;//选中圆的画笔
    private Paint unSelectCirclePaint;//未选中圆的画笔
    private float radius;//圆的半径
    private List<PointF> pointFs;//圆的中心坐标点集合
    private int circleNum = 5;//指示圆的个数
    private float mSpace ;//两圆之间的距离
    private int selectPosition ;
    private ViewPager viewPager;
    private PointF selectCirclePoint;
    private float allCircleLength; //绘制的圆总宽度
    private int selectCircleColor;
    private int unSelectCircleColor;
    private float borderWidth;
    //    private int textColor;
    public IndicatorView(Context context) {
        super(context);
        init(context,null);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public void init(Context context ,AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IndicatorView);
        selectCircleColor = typedArray.getResourceId(R.styleable.IndicatorView_selectCircleColor, Color.YELLOW);
        unSelectCircleColor = typedArray.getResourceId(R.styleable.IndicatorView_unSelectCircleColor, Color.parseColor("#D88577"));
        selectPosition = typedArray.getInteger(R.styleable.IndicatorView_defautSelectPosition, 0);
        radius = typedArray.getDimension(R.styleable.IndicatorView_radius, 20);
        mSpace = typedArray.getDimension(R.styleable.IndicatorView_circleSpace,50);
        borderWidth =typedArray.getDimension(R.styleable.IndicatorView_unSelectCircleBorderWidth,5);
        typedArray.recycle();
        
        selectCirclePaint = new Paint();
        selectCirclePaint.setAntiAlias(true);
        selectCirclePaint.setStyle(Paint.Style.FILL);
        selectCirclePaint.setColor(selectCircleColor);


        unSelectCirclePaint = new Paint();
        unSelectCirclePaint.setAntiAlias(true);
        unSelectCirclePaint.setStyle(Paint.Style.STROKE);
        unSelectCirclePaint.setStrokeWidth(borderWidth);
        unSelectCirclePaint.setColor(unSelectCircleColor);

        pointFs = new ArrayList<>();
        selectCirclePoint = new PointF();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        measureCircle();
        PointF pointF1 = pointFs.get(selectPosition);
        selectCirclePoint.x = pointF1.x;
        selectCirclePoint.y = pointF1.y;
    }

    public void measureCircle() {
        int height = getHeight();
        allCircleLength = circleNum*(radius*2)+(circleNum-1)*mSpace;
        float firstDistance = (getMeasuredWidth() - allCircleLength)/2;
        float cx = 0;
        for (int i = 0; i < circleNum; i++) {
            PointF point = new PointF();
            if (i == 0) {
                cx = radius + firstDistance;
            } else {
                cx += radius * 2 + mSpace;
            }
            point.x = cx;
            point.y = getMeasuredHeight() / 2;
            pointFs.add(point);
        }
     
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < circleNum; i++) {
            PointF pointF = pointFs.get(i);
            canvas.drawCircle(pointF.x, pointF.y, radius, unSelectCirclePaint);
            if (i == selectPosition) {
                canvas.drawCircle(selectCirclePoint.x, selectCirclePoint.y, radius, selectCirclePaint);
            }

        }
    }

    public void setCircleNum(int num) {
        this.circleNum = num;
        invalidate();
    }

    public void setcurrentSelect(int position) {
        this.selectPosition = position;
        if(!pointFs.isEmpty()&&selectPosition<pointFs.size()){
            PointF pointF1 = pointFs.get(selectPosition);
            selectCirclePoint.x = pointF1.x;
            selectCirclePoint.y = pointF1.y;
            invalidate();
        }
    }

    public void setDefautSelectPosition(int position) {
        this.selectPosition = position;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xPoint = 0;
        float yPoint = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xPoint = event.getX();
                yPoint = event.getY();
                handleActionDown(xPoint, yPoint);
                break;

        }

        return super.onTouchEvent(event);
    }

    private void handleActionDown(float xDis, float yDis) {
        for (int i = 0; i < pointFs.size(); i++) {
            PointF pointF = pointFs.get(i);
            if (xDis < (pointF.x + radius)
                    && xDis >= (pointF.x - (radius))
                    && yDis >= (pointF.y - radius)
                    && yDis < (pointF.y + radius)) {
                viewPager.setCurrentItem(i);
                setcurrentSelect(i);
                break;
            }
        }
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public void setPoints(int position, float positionOffset, int positionOffsetPixels) {
        if (pointFs.isEmpty()) {
            return;
        }
        int currentPosition = Math.min(pointFs.size() - 1, position);
        int nextPosition = Math.min(pointFs.size() - 1, position + 1);
        PointF current = pointFs.get(currentPosition);
        PointF next = pointFs.get(nextPosition);
//       
        selectCirclePoint.x = current.x + (next.x - current.x)  * positionOffset;

        invalidate();
    }
    
    public void setSelectCircleColor(int risd){
        this.selectCircleColor = risd;
        invalidate();
    }

    public void setUnSelectCircleColor(int risd){
        this.unSelectCircleColor = risd;
        invalidate();
    }
}
