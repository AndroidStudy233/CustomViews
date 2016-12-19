package com.shiqkuangsan.mycustomviews.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.utils.MyLogUtil;

/**
 * Created by shiqkuangsan on 2016/6/3.
 */

/**
 * 图片流查看的时候,如果RecyclerView或者ListView的item的高度写死了
 * 适配上回有一点问题.而且图片的scaleType也不好写,写centerCrop会裁剪
 * 写fitXY会拉伸.如果有一个布局他的宽高比就是根据加载的图片的宽高比
 * 而他的宽高比又是可以在布局文件中定义.这样宽度每次都使用屏幕宽度
 * 高度会自动根据宽高比计算出来,centerCrop下的图片就是完美展示的
 * (适用于所有图片大小不一但是宽高比一样,而且不用做适配)
 */
public class AutoRatioLayout extends FrameLayout {

    /**宽高比*/
    private float ratio;

    public AutoRatioLayout(Context context) {
        super(context);
    }

    public AutoRatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 使用逼格手法获取自定义属性值
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AutoRatioLayout);
        ratio = array.getFloat(R.styleable.AutoRatioLayout_ratio, -1);
        array.recycle();
    }

    public AutoRatioLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 1.获取宽度
        // 2.根据获取的宽高比,计算控件的高度
        // 3.让系统重新测量宽高然后去绘制

//        System.out.println("widthMeasureSpec: " + widthMeasureSpec);
        // 该值为: 1073742520
        // 转化为二进制: 1000000000000000000001010111000,后面的1010111000转化为十进制: 696
        // 前面代表模式,后面是具体值的二进制数据,696+padding就是屏幕的宽度了
        // 三种模式:
        // MeasureSpec.AT_MOST : 至多模式,控件有多大显示多大,wrap_content
        // MeasureSpec.EXACTLY : 精确模式,宽高值写死成一个值或者填充父元素,100dp/match_parent
        // MeasureSpec.UNSPECIFIED : 未指定模式

        // 拿到该控件的宽高及其模式
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        // 如果布局文件中你定义的宽度是确定了,高度不确定,ratio值正常,我就去按照比例计算高度
        // 也就是按照我的要求宽度Match,高度Wrap,ratio正常
        if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY && ratio > 0) {
            // 拿到应该显示的图片的宽高,为了防止以后用设置了padding,这里加点代码
            int imageWidth = width - getPaddingLeft() - getPaddingRight();
            int imageHeight = (int) (imageWidth / ratio + 0.5f);

            // 根据图片应该显示的高度,计算控件的高度(其实如果该控件用的时候不定义padding,图片的宽高就是
            // 控件的宽高,上面两步是就不用了)
            height = imageHeight + getPaddingTop() + getPaddingBottom();
//            MyLogUtil.d("height: " + height);
            // 控件的高度写好了,这时候重新定义下,然后让系统按照新的规则去测量
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
