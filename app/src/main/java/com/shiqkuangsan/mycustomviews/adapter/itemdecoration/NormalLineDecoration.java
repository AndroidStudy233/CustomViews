package com.shiqkuangsan.mycustomviews.adapter.itemdecoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by shiqkuangsan on 2017/5/10.
 * <p>
 * ClassName: NormalLineDecoration
 * Author: shiqkuangsan
 * Description: 简单的横线装饰
 */
public class NormalLineDecoration extends RecyclerView.ItemDecoration {

    private int dividerHeight;

    public NormalLineDecoration(int dividerHeight){
        this.dividerHeight = dividerHeight;
    }

    /**
     * 该方法类似在条目中给item加padding, 是item和我们的内容之间装饰
     *
     * @param outRect 内容四个边的矩形.其中top/bottom等属性可以设置padding效果
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = dividerHeight;//类似加了一个bottom padding
    }

    /**
     * 该方法是给我们的内容加上背景
     *
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    /**
     * 该方法是绘制在内容上面, 覆盖内容
     *
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
}
