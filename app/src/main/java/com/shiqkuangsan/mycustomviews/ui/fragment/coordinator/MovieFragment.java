package com.shiqkuangsan.mycustomviews.ui.fragment.coordinator;

import android.os.Build;
import android.os.Bundle;
import android.provider.Contacts;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rd.PageIndicatorView;
import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.utils.UIUitl;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by shiqkuangsan on 2017/8/6.
 * <p>
 * ClassName: MovieFragment
 * Author: shiqkuangsan
 * Description: Coordinator中的Movie界面
 */
@ContentView(R.layout.fragment_parallax_movie)
public class MovieFragment extends Fragment {

    @ViewInject(R.id.pager_parallax_movie)
    ViewPager pager;
    @ViewInject(R.id.indicator_parallax_movie)
    PageIndicatorView indicator;
    @ViewInject(R.id.toolbar_parallax_movie)
    Toolbar toolbar;

    int[] imgRes = {R.mipmap.img800x470_1, R.mipmap.img800x470_2, R.mipmap.img1366x768_1, R.mipmap.img1366x768_2};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        compatToolbar();
        pager.setAdapter(new ImageAdapter());
        indicator.setViewPager(pager);
    }

    private void compatToolbar() {
        // 投机取巧暂且实现
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CollapsingToolbarLayout.LayoutParams params = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
            params.height = UIUitl.dip2px(getActivity(), 64);
            toolbar.setLayoutParams(params);
            toolbar.setPadding(0, UIUitl.dip2px(getActivity(), 20), 0, 0);
        }
    }

    private class ImageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imgRes.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(imgRes[position]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
