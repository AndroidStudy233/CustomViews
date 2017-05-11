package com.shiqkuangsan.mycustomviews.adapter.itemdecoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompatBase;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.shiqkuangsan.mycustomviews.R;

/**
 * Created by shiqkuangsan on 2017/5/10.
 * <p>
 * ClassName: NormalLineDecoration
 * Author: shiqkuangsan
 * Description: 简单的线条装饰(针对LinearLayoutManager)
 */
public class NormalLineDecoration extends RecyclerView.ItemDecoration {

    private Context context;
    private Paint mPaint;
    private Drawable mDivider;
    private int mDividerHeight = 1;//分割线高度，默认为1px
    private int mOrientation;
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    public static boolean enableDrawOver = false;
    private int tagWidth = 40; // 左边条形宽度, 绘制接覆盖在item内容上面
    private Paint leftPaint;
    private Paint rightPaint;

    /**
     * 默认分割线：高度为2px，颜色为灰色
     *
     * @param context     上下文
     * @param orientation 列表的方向(LinearLayoutManager.VERTICAL或LinearLayoutManager.HORIZONTAL)
     */
    public NormalLineDecoration(Context context, int orientation) {
        this.context = context;
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("请输入正确的参数！");
        }
        mOrientation = orientation;

        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();

        init();
    }

    /**
     * 自定义分割线
     *
     * @param context     上下文
     * @param orientation 列表的方向(LinearLayoutManager.VERTICAL或LinearLayoutManager.HORIZONTAL)
     * @param drawableId  分割线图片
     */
    public NormalLineDecoration(Context context, int orientation, int drawableId) {
        this(context, orientation);
        mDivider = ContextCompat.getDrawable(context, drawableId);
        mDividerHeight = mDivider.getIntrinsicHeight();
    }

    /**
     * 自定义分割线
     *
     * @param context       上下文
     * @param orientation   列表的方向(LinearLayoutManager.VERTICAL或LinearLayoutManager.HORIZONTAL)
     * @param dividerHeight 分割线高度
     * @param dividerColor  分割线颜色
     */
    public NormalLineDecoration(Context context, int orientation, int dividerHeight, int dividerColor) {
        this(context, orientation);
        mDividerHeight = dividerHeight;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(dividerColor);
        mPaint.setStyle(Paint.Style.FILL);
    }

    private void init() {
        leftPaint = new Paint();
        leftPaint.setColor(context.getResources().getColor(R.color.colorAccent));
        rightPaint = new Paint();
        rightPaint.setColor(context.getResources().getColor(R.color.colorPrimary));
    }

    /**
     * 类似在条目中给item加padding, 是item和内容之间的装饰
     *
     * @param outRect 控制padding大小, 其top/bottom/left/right属性分别就是对应padding
     * @param view    holder的itemView
     * @param parent  RecyclerView
     * @param state   state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, mDividerHeight);
    }

    /**
     * 有了padding, padding的背景具体是什么样子, 就是该方法实现.(假如界面背景和item背景不一样, 自然
     * 就看得出padding的效果, 当然可以自己画)
     *
     * @param c      Canvas对象
     * @param parent RecyclerView
     * @param state  state
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawHorizontal(c, parent);
        } else {
            drawVertical(c, parent);
        }
    }

    /**
     * 该方法绘制的效果是展现在内容上面的, 覆盖内容
     *
     * @param c      Canvas对象
     * @param parent RecyclerView
     * @param state  state
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        // 默认不启动该功能, 效果就在这儿
        if (enableDrawOver) {
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                int pos = parent.getChildAdapterPosition(child);
                if (pos % 2 == 0) {
                    float left = child.getLeft();
                    float right = left + tagWidth;
                    float top = child.getTop();
                    float bottom = child.getBottom();
                    c.drawRect(left, top, right, bottom, leftPaint);
                } else {
                    float right = child.getRight();
                    float left = right - tagWidth;
                    float top = child.getTop();
                    float bottom = child.getBottom();
                    c.drawRect(left, top, right, bottom, rightPaint);

                }
            }
        }
    }

    // 绘制横向 item 分割线
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + mDividerHeight;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    // 绘制纵向 item 分割线
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + mDividerHeight;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }
}
