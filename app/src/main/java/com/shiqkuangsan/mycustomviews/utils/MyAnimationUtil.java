package com.shiqkuangsan.mycustomviews.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

public class MyAnimationUtil {

    public static void rotateToTop(View view) {
        RotateAnimation anim = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(250);
        anim.setFillAfter(true);
        view.startAnimation(anim);
    }

    public static void rotateToBottom(View view) {
        RotateAnimation anim = new RotateAnimation(-180, -0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(250);
        anim.setFillAfter(true);
        view.startAnimation(anim);
    }

}
