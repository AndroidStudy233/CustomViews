package com.shiqkuangsan.mycustomviews.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by dell on 2016/8/pic5.
 */

/**
 * 限制滑动速度的ScrollView
 */
public class SlowScrollView extends ScrollView {
    public SlowScrollView(Context context) {
        super(context);
    }

    public SlowScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlowScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY / 10);
    }
}
