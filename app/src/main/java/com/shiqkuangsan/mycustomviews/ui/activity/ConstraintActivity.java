package com.shiqkuangsan.mycustomviews.ui.activity;

import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.utils.MyLogUtil;
import com.shiqkuangsan.mycustomviews.utils.SimplexUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by shiqkuangsan on 2017/1/15.
 * <p>
 * ClassName: ConstraintActivity
 * Author: shiqkuangsan
 * Description: 学习使用ConstraintLayout的界面
 */
public class ConstraintActivity extends AppCompatActivity {

    final String url = "http://c.hiphotos.baidu.com/zhidao/pic/item/a8773912b31bb0510a0a938e357adab44aede06e.jpg";

    @ViewInject(R.id.photov_test)
    PhotoView photo_test;

    PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint);
        x.view().inject(this);

        // 1. 加载本地的
        //        Drawable bitmap = ContextCompat.getDrawable(this, R.mipmap.img1920x1080_1);
//        photo_test.setImageDrawable(bitmap);
        mAttacher = new PhotoViewAttacher(photo_test);

        // 2. 异步加载需要使用update方法
        x.image().bind(photo_test,url,
                SimplexUtil.getSimpleImageOptions(0),new SimplexUtil.SimpleRequstCallBack<Drawable>(){
                    @Override
                    public void onSuccess(Drawable result) {
                        mAttacher.update();
                    }
                });

        // 3. 简单试了下glide库
//        Glide.with(this)
//                .load(url)
//                .fitCenter()
////                .placeholder(R.drawable.pic_loading)
//                .crossFade()
//                .into(photo_test);


        mAttacher.setOnMatrixChangeListener(new MatrixChangeListener());
        mAttacher.setOnPhotoTapListener(new PhotoTapListener());
        mAttacher.setOnSingleFlingListener(new SingleFlingListener());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAttacher != null)
            mAttacher.cleanup();
    }

    private class PhotoTapListener implements PhotoViewAttacher.OnPhotoTapListener {

        @Override
        public void onPhotoTap(View view, float x, float y) {
            float xPercentage = x * 100f;
            float yercentage = y * 100f;

            MyLogUtil.d("xPercentage: " + xPercentage + " --- yercentage: " + yercentage);
        }

        @Override
        public void onOutsidePhotoTap() {
            MyLogUtil.d("点击了图片以外的区域");
        }
    }

    private class MatrixChangeListener implements PhotoViewAttacher.OnMatrixChangedListener {

        @Override
        public void onMatrixChanged(RectF rect) {
            MyLogUtil.d("rect: " + rect.toString());
        }
    }

    private class SingleFlingListener implements PhotoViewAttacher.OnSingleFlingListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            MyLogUtil.i("onFling: " + velocityX + " --- " + velocityY);
            return true;
        }
    }
}
