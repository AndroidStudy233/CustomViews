package com.shiqkuangsan.mycustomviews.ui.custom;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by shiqkuangsan on 2017/8/6.
 * <p>
 * ClassName: NestedViewPager
 * Author: shiqkuangsan
 * Description: 嵌套ViewPager
 */
public class NestedViewPager extends ViewPager {

    public NestedViewPager(Context context) {
        super(context);
    }

    public NestedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.onInterceptTouchEvent(ev);
    }
}
