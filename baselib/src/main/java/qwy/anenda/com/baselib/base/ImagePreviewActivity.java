package qwy.anenda.com.baselib.base;


import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.List;

import qwy.anenda.com.baselib.R;
import qwy.anenda.com.baselib.recycleradaper.ImagePreviewAdapter;


public class ImagePreviewActivity extends AppCompatActivity {
    public String TAG = this.getClass().getSimpleName();
    //    public class ImagePreviewActivity extends Activity  {
    public final static long ANIM_DURATION = 250L;
    public static final String IMAGE_INFO = "IMAGE_INFO";
    public static final String CURRENT_ITEM = "CURRENT_ITEM";
    public static final String SCREEN_LOCATION0 = "SCREEN_LOCATION0";
    public static final String SCREEN_LOCATION1 = "SCREEN_LOCATION1";
    public static final String THUMBNAIL_WIDTH = "THUMBNAIL_WIDTH";
    public static final String THUMBNAIL_HEIGHT = "THUMBNAIL_HEIGHT";

    private RelativeLayout rootView;
    private ImagePreviewAdapter imagePreviewAdapter;
    private List<String> imageInfo;
    private ViewPager viewPager;

    private int currentItem;
    private int thumbnailTop = 0;
    private int thumbnailLeft = 0;
    private int thumbnailWidth = 0;
    private int thumbnailHeight = 0;

    private int screenWidth;
    private int screenHeight;
    private int imageHeight;
    private int imageWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        final TextView tv_pager = (TextView) findViewById(R.id.tv_pager);
        rootView = (RelativeLayout) findViewById(R.id.rootView);

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenWidth = metric.widthPixels;
        screenHeight = metric.heightPixels;


        Bundle bundle = getIntent().getExtras();
        imageInfo = (List<String>) bundle.getSerializable(IMAGE_INFO);
        currentItem = bundle.getInt(CURRENT_ITEM, 0);
        thumbnailTop = bundle.getInt(SCREEN_LOCATION1);
        thumbnailLeft = bundle.getInt(SCREEN_LOCATION0);
        thumbnailWidth = bundle.getInt(THUMBNAIL_WIDTH);
        thumbnailHeight = bundle.getInt(THUMBNAIL_HEIGHT);
        imagePreviewAdapter = new ImagePreviewAdapter(this, imageInfo);
        viewPager.setAdapter(imagePreviewAdapter);
        viewPager.setCurrentItem(currentItem);
        viewPager.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                rootView.getViewTreeObserver().removeOnPreDrawListener(this);
                final View view = imagePreviewAdapter.getPrimaryItem();
                final ImageView imageView = imagePreviewAdapter.getPrimaryImageView();
                computeImageWidthAndHeight(imageView);

                final float vx = thumbnailWidth * 1.0f / imageWidth;
                final float vy = thumbnailHeight * 1.0f / imageHeight;
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1.0f);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        long duration = animation.getDuration();
                        long playTime = animation.getCurrentPlayTime();
                        float fraction = duration > 0 ? (float) playTime / duration : 1f;
                        if (fraction > 1) fraction = 1;
                        view.setTranslationX(evaluateInt(fraction, thumbnailLeft + thumbnailHeight / 2 - imageView.getWidth() / 2, 0));
                        view.setTranslationY(evaluateInt(fraction, thumbnailTop + thumbnailWidth / 2 - imageView.getHeight() / 2, 0));
                        view.setScaleX(evaluateFloat(fraction, vx, 1));
                        view.setScaleY(evaluateFloat(fraction, vy, 1));
                        view.setAlpha(fraction);
                        rootView.setBackgroundColor(evaluateArgb(fraction, Color.TRANSPARENT, Color.BLACK));
                    }
                });
                addIntoListener(valueAnimator);
                valueAnimator.setDuration(ANIM_DURATION);
                valueAnimator.start();
                return true;
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentItem = position;
                tv_pager.setText(String.format(getString(R.string.img_select), currentItem + 1 + "", imageInfo.size() + ""));
//                tv_pager.setText(String.format(getString(R.string.select), currentItem + 1+"", imageInfo.size()+""));
            }
        });
        tv_pager.setText(String.format(getString(R.string.img_select), currentItem + 1 + "", imageInfo.size() + ""));
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * activity的退场动画
     */
    public void finishActivityAnim() {
        final View view = imagePreviewAdapter.getPrimaryItem();
        final ImageView imageView = imagePreviewAdapter.getPrimaryImageView();
        computeImageWidthAndHeight(imageView);

        final float vx = thumbnailWidth * 1.0f / imageWidth;
        final float vy = thumbnailHeight * 1.0f / imageHeight;
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1.0f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                long duration = animation.getDuration();
                long playTime = animation.getCurrentPlayTime();
                float fraction = duration > 0 ? (float) playTime / duration : 1f;
                if (fraction > 1) fraction = 1;
                view.setTranslationX(evaluateInt(fraction, 0, thumbnailLeft + thumbnailWidth / 2 - imageView.getWidth() / 2));
                view.setTranslationY(evaluateInt(fraction, 0, thumbnailTop + thumbnailHeight / 2 - imageView.getHeight() / 2));
                view.setScaleX(evaluateFloat(fraction, 1, vx));
                view.setScaleY(evaluateFloat(fraction, 1, vy));
                view.setAlpha(1 - fraction);
//                Log.e(TAG,"finishActivityAnim fraction="+fraction);
                rootView.setBackgroundColor(evaluateArgb(fraction, Color.BLACK, Color.TRANSPARENT));
            }
        });
        addOutListener(valueAnimator);
        valueAnimator.setDuration(ANIM_DURATION);
        valueAnimator.start();
    }

    /**
     * 计算图片的宽高
     */
    private void computeImageWidthAndHeight(ImageView imageView) {

        // 获取真实大小
        Drawable drawable = imageView.getBackground();
        if (drawable == null) {
            drawable = imageView.getDrawable();
        }
        if (drawable != null) {
            int intrinsicHeight = drawable.getIntrinsicHeight();
            int intrinsicWidth = drawable.getIntrinsicWidth();
            // 计算出与屏幕的比例，用于比较以宽的比例为准还是高的比例为准，因为很多时候不是高度没充满，就是宽度没充满
            float h = screenHeight * 1.0f / intrinsicHeight;
            float w = screenWidth * 1.0f / intrinsicWidth;
            if (h > w) h = w;
            else w = h;

            // 得出当宽高至少有一个充满的时候图片对应的宽高
            imageHeight = (int) (intrinsicHeight * h);
            imageWidth = (int) (intrinsicWidth * w);
        }
    }

    @Override
    public void onBackPressed() {
//        finishActivityAnim();
        finishActivityAnim();
    }

    /**
     * 进场动画过程监听
     */
    private void addIntoListener(ValueAnimator valueAnimator) {
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                rootView.setBackgroundColor(0x0);
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
    }

    /**
     * 退场动画过程监听
     */
    private void addOutListener(ValueAnimator valueAnimator) {
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                rootView.setBackgroundColor(0x0);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

                finish();
                overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
       /* new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(300);
                    finish();
                    overridePendingTransition(0, 0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/
    }

    /**
     * Integer 估值器
     */
    public Integer evaluateInt(float fraction, Integer startValue, Integer endValue) {
        int startInt = startValue;
        return (int) (startInt + fraction * (endValue - startInt));
    }

    /**
     * Float 估值器
     */
    public Float evaluateFloat(float fraction, Number startValue, Number endValue) {
        float startFloat = startValue.floatValue();
        return startFloat + fraction * (endValue.floatValue() - startFloat);
    }

    /**
     * Argb 估值器
     */
    public int evaluateArgb(float fraction, int startValue, int endValue) {
        int startA = (startValue >> 24) & 0xff;
        int startR = (startValue >> 16) & 0xff;
        int startG = (startValue >> 8) & 0xff;
        int startB = startValue & 0xff;

        int endA = (endValue >> 24) & 0xff;
        int endR = (endValue >> 16) & 0xff;
        int endG = (endValue >> 8) & 0xff;
        int endB = endValue & 0xff;

        return (startA + (int) (fraction * (endA - startA))) << 24//
                | (startR + (int) (fraction * (endR - startR))) << 16//
                | (startG + (int) (fraction * (endG - startG))) << 8//
                | (startB + (int) (fraction * (endB - startB)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imagePreviewAdapter = null;
        imageInfo.clear();
        imageInfo = null;
        viewPager.clearAnimation();
        viewPager.addOnPageChangeListener(null);
        viewPager = null;
    }
}
