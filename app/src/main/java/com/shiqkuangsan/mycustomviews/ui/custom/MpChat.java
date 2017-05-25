package com.shiqkuangsan.mycustomviews.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/*************************************************
 * <p>类描述：${todo}(用一句话描述该文件做什么)</p>
 * <p>创建人：余志伟</p>
 * <p>创建时间 : 2017/5/25</p>
 * <p>修改人：       </p>
 * <p>修改时间：   </p>
 * <p>修改备注：   </p>
 *
 * @version V3.1
 *********************************/
public class MpChat extends View implements  Runnable{
    Paint paint;
    Paint paint1;
    Paint paint3;
    private float radias;//圆的半径 （即长条形的宽为radias*2）
    private float distance;//两个条形之间的横向距离
    private float startX; //坐标原点
    private float startY;//坐标原点
    private float startLength;//坐标轴的长宽
    private int canDrawNum;
    private float circleCenterX;
    private float circleCenterY;
    private List<PointF> points;

    public MpChat(Context context) {
        super(context);
        init();
    }

    public MpChat(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MpChat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(20);
        paint.setAntiAlias(true);


        paint1 = new Paint();
        paint1.setColor(Color.GREEN);
        paint1.setStrokeCap(Paint.Cap.ROUND);
        paint1.setAntiAlias(true);
        paint1.setStyle(Paint.Style.FILL);

        paint3 = new Paint();
        paint3.setColor(Color.GRAY);
        paint3.setStrokeCap(Paint.Cap.ROUND);
        paint3.setStrokeWidth(3);
        paint3.setStyle(Paint.Style.FILL);

        startLength = 1000;
        startX = 50;
        startY = 1000;
        distance = 100;
        radias = 20;
        canDrawNum = (int) (startLength / (radias * 2 + distance));//可画个数

        points = new ArrayList<>();
//        Point point = new Point();
//        point.y = 100;
//        Point point1 = new Point();
//        point1.y = 200;
//        Point point2 = new Point();
//        point2.y = 500;
//        Point point3 = new Point();
//        point3.y = 800;
//        Point point4 = new Point();
//        point4.y = 1000;
        for (int i = 0; i < canDrawNum; i++) {
            PointF point = new PointF();
            point.y = 0;
            points.add(point);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(startX, startX, startX, startLength, paint3);
        canvas.drawLine(startX, startLength, startLength, startLength, paint3);
        for (int i = 0; i < canDrawNum; i++) {
            RectF rectF = new RectF();
            rectF.left = distance*(i+1);
            rectF.top = startLength-points.get(i).y;
            rectF.right = distance*(i+1)+radias*2;
            rectF.bottom = startY;
            canvas.drawRect(rectF, paint1);
            circleCenterX = distance*(i+1)+radias;
            circleCenterY =  startLength-points.get(i).y;
            canvas.drawCircle(circleCenterX, circleCenterY, radias, paint1);
            canvas.drawText("月份"+i,distance*(i+1),startY+50,paint);
        }
    }
    @Override
    public void run() {  
        /* 
         * 确保线程不断执行不断刷新界面 
         */
        while (true) {
            try {
                for (int i = 0; i < canDrawNum; i++) {
                    points.get(i).y= points.get(i).y+(i+1)*2;
                    if( points.get(i).y>startLength){
                        points.get(i).y=startLength;
                    }
                }
                if(points.get(0).y>=120){
                    break;
                }
                // 每执行一次暂停40毫秒  
                Thread.sleep(30);
                postInvalidate();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
