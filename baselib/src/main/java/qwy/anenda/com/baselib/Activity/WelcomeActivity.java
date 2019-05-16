package qwy.anenda.com.baselib.Activity;
 /* 
 -----------------------------------------------------------------
 * Author: yzw
 * Create: 2019/3/25
 * Descride:
 * 
 *-----------------------------------------------------------------
 */


import qwy.anenda.com.baselib.R;
import qwy.anenda.com.baselib.base.BaseActivity;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected int layoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView() {
        try {
            Thread.sleep(1000);
            chooseEnter();
            finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void BindEvent() {

    }


    private void splashAnim() {
//        AlphaAnimation anim = new AlphaAnimation(0.1f, 1.0f);
//        anim.setDuration(2000);
//        anim.setFillAfter(true);
//        anim.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                chooseEnter();
//                finish();
//            }
//        });
//        mRlSplash.startAnimation(anim);
    }

    public void chooseEnter() {
        goToActivity(LoginActivity.class);
        finish();
    }
}
