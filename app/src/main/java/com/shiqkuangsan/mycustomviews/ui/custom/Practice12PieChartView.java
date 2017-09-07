package com.shiqkuangsan.mycustomviews.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class Practice12PieChartView extends View {
    private Paint paint1;
    private Paint paint2;
    private Paint paint3;
    private Paint paint4;
    private Paint paint5;
    private Paint paint6;
    private Path path ;
    private Paint paint7;
    private Paint paint8;
    private Paint textPaint;
    
    private float circleX;
    private float circleY;
    private float radious;
    private float left;
    private float top;
    private float right;
    private float bottom;
    public Practice12PieChartView(Context context) {
        super(context);
        init();
    }

    public Practice12PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Practice12PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public void init(){
        paint1 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setColor(Color.RED);
        paint1.setStyle(Paint.Style.FILL);
        paint2= new Paint();
        paint2.setAntiAlias(true);
        paint2.setColor(Color.GREEN);
        paint2.setStyle(Paint.Style.FILL);
        paint3 = new Paint();
        paint3.setAntiAlias(true);
        paint3.setColor(Color.GRAY);
        paint3.setStyle(Paint.Style.FILL);
        paint4 = new Paint();
        paint4.setColor(Color.BLUE);
        paint4.setAntiAlias(true);
        paint4.setStyle(Paint.Style.FILL);
        paint5 = new Paint();
        paint5.setAntiAlias(true);
        paint5.setColor(Color.YELLOW);
        paint5.setStyle(Paint.Style.FILL);
        paint6 = new Paint();
        paint6.setColor(Color.WHITE);
        paint6.setStyle(Paint.Style.FILL);
        paint6.setAntiAlias(true);
        path = new Path();

        paint7 = new Paint();
        paint7.setColor(Color.WHITE);
        paint7.setStrokeWidth(2);
        paint7.setAntiAlias(true);
        paint7.setStyle(Paint.Style.STROKE);

        paint8 = new Paint();
        paint8.setColor(Color.WHITE);
        paint8.setStrokeWidth(2);
        paint8.setAntiAlias(true);
        paint8.setStyle(Paint.Style.STROKE);

        textPaint = new TextPaint();
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setTextSize(16);
        left = 300;
        top = 100;
        right= 800;
        bottom = 600;
        float v = (right - left) / 2;
        float v1 = (bottom - top) / 2;
        if(v1<v)
            radious=v1;
        else
            radious=v;
        circleX = left+(right-left)/2;
        circleY=top+(bottom-top)/2;
        
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLine(canvas);
//        canvas.drawRect(300,100,800,600,paint7);
        canvas.drawArc(left,top,right,bottom,-45,45,true,paint1);
        canvas.drawArc(left,top,right,bottom,0,20,true,paint2);
        canvas.drawArc(left,top,right,bottom,20,50,true,paint3);
        canvas.drawArc(left,top,right,bottom,70,100,true,paint4);
        canvas.drawArc(left-30,top-20,right-30,bottom-20,170,100,true,paint5);
        canvas.drawArc(left,top,right,bottom,270,45,true,paint6);
        canvas.drawPath(path,paint8);
        
//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画饼图
    }
    public void drawLine(Canvas canvas){
        float x = (float) (circleX+radious* Math.cos(35 * 3.14 / 180));
        float y = (float) (circleY+radious*Math.sin(35*3.14/180));
        path.moveTo(x,y);
        path.lineTo(x+50,y);
        path.lineTo(x+100,y+20);
        canvas.drawText("造轮子",x+102,y+22,textPaint);

        float x1 = (float) (circleX+radious* Math.cos(10 * 3.14 / 180));
        float y1 = (float) (circleY+radious*Math.sin(10*3.14/180));
        path.moveTo(x1,y1);
        path.lineTo(x1+50,y1);
        path.lineTo(x1+100,y1+20);
        canvas.drawText("测试",x1+102,y1+22,textPaint);

        float x2 = (float) (circleX+radious* Math.cos(120 * 3.14 / 180));
        float y2 = (float) (circleY+radious*Math.sin(120*3.14/180));
        path.moveTo(x2,y2);
        path.lineTo(x2-50,y2);
        path.lineTo(x2-100,y2+50);
        canvas.drawText("三比",x2-130,y2+70,textPaint);

        float x3 = (float) (circleX+radious* Math.cos(220 * 3.14 / 180));
        float y3 = (float) (circleY+radious*Math.sin(220*3.14/180));
        path.moveTo(x3,y3);
        path.lineTo(x3-100,y3);
        path.lineTo(x3-150,y3+20);
        canvas.drawText("木木",x3-180,y3+40,textPaint);

        float x4 = (float) (circleX+radious* Math.cos(292 * 3.14 / 180));
        float y4 = (float) (circleY+radious*Math.sin(292*3.14/180));
        path.moveTo(x4,y4);
        path.lineTo(x4+100,y4);
        path.lineTo(x4+150,y4+20);
        canvas.drawText("不要脸祖",x4+152,y4+22,textPaint);

        float x5 = (float) (circleX+radious* Math.cos(335 * 3.14 / 180));
        float y5 = (float) (circleY+radious*Math.sin(335*3.14/180));
        path.moveTo(x5,y5);
        path.lineTo(x5+100,y5);
        path.lineTo(x5+150,y5+20);
        canvas.drawText("帅比安",x5+152,y5+22,textPaint);
    }
}
