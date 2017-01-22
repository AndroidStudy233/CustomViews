package com.shiqkuangsan.mycustomviews.ui.custom.photoview;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.ui.custom.MaterialProgressBar;
import com.shiqkuangsan.mycustomviews.ui.custom.OverScrollViewPager;

import java.util.ArrayList;

public class ViewPagerFragment extends Fragment {

    private OverScrollViewPager viewPager;
    private TextView tips; //viewpager indicator
    private ArrayList<String> imgs;
    private Info imageInfo;
    private View mask;//background custom
    private ArrayList<Info> imageInfos;
    private int position;

    public static ViewPagerFragment getInstance(Bundle bundle) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_viewpager, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = (OverScrollViewPager) view.findViewById(R.id.viewpager_piclooker);
        tips = (TextView) view.findViewById(R.id.tv_piclooker_index);
        mask = view.findViewById(R.id.view_piclooker_back);

        runEnterAnimation();

        Bundle bundle = getArguments();
        imgs = bundle.getStringArrayList("imgs");
        imageInfo = bundle.getParcelable("info");
        imageInfos = bundle.getParcelableArrayList("infos");

        position = bundle.getInt("position", 0);
        tips.setText((position + 1) + "/" + imgs.size());

        viewPager.setpagerCount(imgs.size());
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imgs.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int pos) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_view_detail, null, false);
                final PhotoView myPhotoView = (PhotoView) view.findViewById(R.id.iv_piclooker_detail);
                final MaterialProgressBar progressBar = (MaterialProgressBar) view.findViewById(R.id.pb_piclooker);
                if (position == pos && ImageLoader.getInstance().getDiscCache().get(imgs.get(pos)) != null) {//only animate when position equals u click in pre layout
                    myPhotoView.animaFrom(imageInfo);
                }
                //load pic from remote
                ImageLoader.getInstance().displayImage(imgs.get(pos), myPhotoView,
                        new DisplayImageOptions.Builder()
                                .cacheInMemory(true)
                                .cacheOnDisc(true)
                                .build(),
                        new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                progressBar.setVisibility(View.GONE);
                            }

                        });

                //force to get focal point,to listen key listener
                myPhotoView.setFocusableInTouchMode(true);
                myPhotoView.requestFocus();
                myPhotoView.setOnKeyListener(pressKeyListener);//add key listener to listen back press
                myPhotoView.setOnClickListener(onClickListener);
                myPhotoView.setTag(pos);
                myPhotoView.enable();
                container.addView(view);

                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tips.setText((position + 1) + "/" + imgs.size());
                viewPager.setCurrentIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //set current position
//        viewPager.getOverscrollView().setCurrentItem(position);
        viewPager.setCurrentItem(position);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            exitFragment(v);
        }
    };

    private void exitFragment(View v) {
        //退出时点击的位置
        int position = (int) v.getTag();
        //回到上个界面该view的位置
        if (((FrameLayout) v.getParent()).getChildAt(1).getVisibility() == View.VISIBLE) {
            popFragment();
        } else {
            runExitAnimation(v);
            ((PhotoView) v).animaTo(imageInfos.get(position), new Runnable() {
                @Override
                public void run() {
                    popFragment();
                }
            });
        }
    }

    private void popFragment() {
        if (!ViewPagerFragment.this.isResumed()) {
            return;
        }
        final FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            fragmentManager.popBackStack();
        }
    }


    private View.OnKeyListener pressKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {//只监听返回键
                if (event.getAction() != KeyEvent.ACTION_UP) {
                    return true;
                }
                exitFragment(v);
                return true;
            }
            return false;
        }
    };


    private void runEnterAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(300);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        mask.startAnimation(alphaAnimation);
    }

    public void runExitAnimation(final View view) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(300);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mask.setVisibility(View.GONE);
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mask.startAnimation(alphaAnimation);
    }

}
