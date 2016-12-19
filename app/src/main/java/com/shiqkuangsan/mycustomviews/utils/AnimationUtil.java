package com.shiqkuangsan.mycustomviews.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

public class AnimationUtil {

    // 是否有动画正在运行
    public static boolean oneAnimRunning = false;

    // 旋转出屏幕的动画
    public static void rotateOutofWindow(RelativeLayout layout, long delay) {
        /*
            补间动画就是这个缺点,虽然执行了旋转动画,但是其实本身还是在原来的位置,只是看上去旋转了,这个时候原来的按钮还是可以点的
            为了防止这种现象的发生,当执行了转出去的动画,让其所有的组件都不能用,转回来的时候都设置可用
         */
        int childCount = layout.getChildCount();
        // 如果隐藏. 则找到所有的子View, 禁用
        for (int i = 0; i < childCount; i++) {
            layout.getChildAt(i).setEnabled(false);
        }

        // 参数1,2: 从哪个角度旋转到哪个角度      参数3-6:都是相对于自身,也就是以自身为参考坐标系,左上角为原点,x坐标是自身0.5宽的距离,y坐标是自身高度
        RotateAnimation anim = new RotateAnimation(0f, -180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1.0f);

        anim.setDuration(500);
        anim.setFillAfter(true); // 设置动画停留在结束位置
        anim.setStartOffset(delay); // 设置动画开始延时
        anim.setAnimationListener(new MyAnimationListener()); // 添加监听

        layout.startAnimation(anim);
    }

    // 旋转进屏幕的动画
    public static void rotateBackofWindow(RelativeLayout layout, long delay) {

        int childCount = layout.getChildCount();
        // 如果隐藏. 则找到所有的子View, 启用
        for (int i = 0; i < childCount; i++) {
            layout.getChildAt(i).setEnabled(true);
        }

        RotateAnimation anim = new RotateAnimation(
                -180f, 0f, // 开始, 结束的角度, 顺时针
                Animation.RELATIVE_TO_SELF, 0.5f,  // 相对的x坐标点(指定旋转中心x值)
                Animation.RELATIVE_TO_SELF, 1.0f); // 相对的y坐标点(指定旋转中心y值)

        anim.setDuration(500);
        anim.setFillAfter(true);
        anim.setStartOffset(delay); // 设置动画开始延时
        anim.setAnimationListener(new MyAnimationListener()); // 添加监听

        layout.startAnimation(anim);
    }



    public static void rotateToTop(View view){
        RotateAnimation anim = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(250);
        anim.setFillAfter(true);
        view.startAnimation(anim);
    }
    public static void rotateToBottom(View view){
        RotateAnimation anim = new RotateAnimation(-180, -0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(250);
        anim.setFillAfter(true);
        view.startAnimation(anim);
    }




    static class MyAnimationListener implements AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
            oneAnimRunning = true;
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            oneAnimRunning = false;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

    }

}
