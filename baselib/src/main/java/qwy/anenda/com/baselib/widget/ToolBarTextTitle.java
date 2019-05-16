package qwy.anenda.com.baselib.widget;
 /* 
 -----------------------------------------------------------------
 * Author: yzw
 * Create: 2019/3/19
 * Descride:
 * 
 *-----------------------------------------------------------------
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import qwy.anenda.com.baselib.R;


public class ToolBarTextTitle extends android.support.v7.widget.AppCompatTextView {


    public ToolBarTextTitle(Context context) {
        super(context);
        resolveAttribute(context, null, R.attr.toolbarStyle);
    }

    public ToolBarTextTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        resolveAttribute(context, attrs, R.attr.toolbarStyle);
    }

    public ToolBarTextTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        resolveAttribute(context, attrs, defStyleAttr);
    }

    public void resolveAttribute(Context context, @Nullable AttributeSet attrs, int defStyleAttr){
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Toolbar, defStyleAttr, 0);
        final int titleTextAppearance = a.getResourceId(R.styleable.Toolbar_titleTextAppearance, 0);
        setTextAppearance(context, titleTextAppearance);
    }
}
