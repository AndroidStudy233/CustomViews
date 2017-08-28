package com.greendao.demo.avtivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.greendaodemo.R;

/*************************************************
 * <p>版权所有：2016-深圳市赛为安全技术服务有限公司</p>
 * <p>项目名称：安全眼</p>
 * <p/>
 * <p>类描述：${todo}(用一句话描述该文件做什么)</p>
 * <p>创建人：余志伟</p>
 * <p>创建时间 : 2017/8/28</p>
 * <p>修改人：       </p>
 * <p>修改时间：   </p>
 * <p>修改备注：   </p>
 *
 * @version V3.1
 *********************************/
/*
* 现象总结：
* TranslationY   
* view的最终位置坐标：
* x = left+translationX
* y = top+translationY
* 
* 
* scrollBy 表示的是移动的增量dx和dy，如果为负值则移动的是相反方向(整个view没有动，是view的内容移动 也可以理解成view的画布在移动)
* scrollTo 表示的是移动到哪个坐标点，坐标点的位置就会移动到屏幕原点的位置
* view本身没有动，是他的内容 ，例如viewgroup 就是子view动
* */
public class TranslateActivity extends AppCompatActivity {
    private View yDown;
    private View yUp;
    private LinearLayout layoutContain;
    private View reset;
    private ObjectAnimator objectAnimatorDown;
    private ObjectAnimator objectAnimatorUp;
    private LinearLayout greenLayout;
    private View gYDown;
    private View gYUp;
    private View greset;
    private int scrollX;
    private int scrollY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        yDown = findViewById(R.id.ydown);
        yUp = findViewById(R.id.yup);
        layoutContain = (LinearLayout) findViewById(R.id.layout);
        reset = findViewById(R.id.reset);
        greenLayout = (LinearLayout) findViewById(R.id.lay);
        scrollX = greenLayout.getScrollX();
        scrollY = greenLayout.getScrollY();
        gYDown = findViewById(R.id.gydown);
        gYUp = findViewById(R.id.gyup);
        greset = findViewById(R.id.greset);
        gYDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int scrollX = greenLayout.getScrollX();   //用这里获取的值 最后得到的效果和scrollBy一致
                int scrollY = greenLayout.getScrollY();
                greenLayout.scrollTo(scrollX, scrollY + 10);
//                greenLayout.scrollBy(0, 10);
            }
        });
        gYUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int scrollX = greenLayout.getScrollX();   //用这里获取的值 最后得到的效果和scrollBy一致
                int scrollY = greenLayout.getScrollY();
                greenLayout.scrollTo(scrollX, scrollY - 10);
//                greenLayout.scrollBy(0,  -10);
            }
        });
        greset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int scrollX = greenLayout.getScrollX();   //用这里获取的值 最后得到的效果和scrollBy一致
                int scrollY = greenLayout.getScrollY();
                greenLayout.scrollTo(scrollX, scrollY);
//                greenLayout.scrollBy(0,  -10);
            }
        });
        layoutContain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TranslateActivity.this, "...点击", Toast.LENGTH_SHORT).show();
            }
        });
        yDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float translationY = layoutContain.getTranslationY();
                objectAnimatorDown = ObjectAnimator.ofFloat(layoutContain, "translationY", translationY + 50);
                objectAnimatorDown.setDuration(300);
                objectAnimatorDown.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                objectAnimatorDown.start();
            }
        });
        yUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float translationY = layoutContain.getTranslationY();
                objectAnimatorUp = ObjectAnimator.ofFloat(layoutContain, "translationY", translationY - 50);
                objectAnimatorUp.setDuration(300);
                objectAnimatorUp.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                objectAnimatorUp.start();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectAnimatorUp = ObjectAnimator.ofFloat(layoutContain, "translationY", 0);
                objectAnimatorUp.setDuration(300);
                objectAnimatorUp.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                objectAnimatorUp.start();
            }
        });
    }
}
