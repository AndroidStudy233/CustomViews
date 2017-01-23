package com.shiqkuangsan.mycustomviews.ui.activity.piclook;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.rd.PageIndicatorView;
import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.ui.custom.MaterialProgressBar;
import com.shiqkuangsan.mycustomviews.ui.custom.OverScrollViewPager;
import com.shiqkuangsan.mycustomviews.utils.SimplexUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by shiqkuangsan on 2017/1/22.
 * <p>
 * ClassName: PhotoDetailActivity
 * Author: shiqkuangsan
 * Description: 图片详情查看页面
 * 依赖   'com.romandanylyk:pageindicatorview:X.X.X' 指示器类库
 */
public class PhotoDetailActivity extends AppCompatActivity {

    @ViewInject(R.id.pager_pic_detail)
    OverScrollViewPager pager_detail;
    @ViewInject(R.id.indicator_pic_detail)
    PageIndicatorView indicator_detail;

    private ArrayList<String> list_pics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去状态
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_photo_detail);
        x.view().inject(this);

        list_pics = getIntent().getExtras().getStringArrayList("photos");
        if (list_pics == null){
            finish();
        }
        int position = getIntent().getIntExtra("position", 0);
        PhotosAdapter adapter = new PhotosAdapter();
        pager_detail.setpagerCount(list_pics == null ? 0 : list_pics.size());
        pager_detail.setAdapter(adapter);
        pager_detail.setCurrentItem(position);
        pager_detail.setOffscreenPageLimit(3);
        pager_detail.setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        pager_detail.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                pager_detail.updateCurrentIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    class PhotosAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list_pics == null ? 0 : list_pics.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo_detail, container, false);

            final MaterialProgressBar probar_loading = (MaterialProgressBar) view.findViewById(R.id.probar_pic_detail);
            probar_loading.setBarColor(0xf2f2f2);
            probar_loading.setBarWidth(3);

            PhotoView photo_detail = (PhotoView) view.findViewById(R.id.photo_detail_item);
            // 卡卡的,还是在xml中配置来的顺畅
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                photo_detail.setTransitionName(list_pics.get(position));
//                getWindow().setSharedElementEnterTransition(new Slide().setDuration(500));
//                getWindow().setSharedElementExitTransition(new Slide().setDuration(500));
//            }
            /*
                对于PhotoView类库还可以直接使用一个ImageView,然后通过PhotoViewAttacher达到PhotoView的效果
                PhotoViewAttacher mAttacher = new PhotoViewAttacher(photo_test);
                // 继承自PhotoViewAttacher类的listener即可
                mAttacher.setOnMatrixChangeListener(new MatrixChangeListener());
                mAttacher.setOnPhotoTapListener(new PhotoTapListener());
                mAttacher.setOnSingleFlingListener(new SingleFlingListener());
             */
            photo_detail.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    onBackPressed();
                }

                @Override
                public void onOutsidePhotoTap() {
                    onBackPressed();
                }
            });
            SimplexUtil.loadImage(photo_detail, list_pics.get(position), SimplexUtil.getSimpleImageOptions(0), new SimplexUtil.SimpleRequstCallBack<Drawable>() {
                @Override
                public void onSuccess(Drawable result) {
                    probar_loading.setVisibility(View.GONE);
                }
            });

            container.addView(view);
            return view;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
