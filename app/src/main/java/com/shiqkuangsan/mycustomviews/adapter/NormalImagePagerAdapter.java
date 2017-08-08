package com.shiqkuangsan.mycustomviews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.shiqkuangsan.mycustomviews.R;

/**
 * Created by shiqkuangsan on 2017/8/8. <p>
 * ClassName: NormalImagePagerAdapter <p>
 * Author: shiqkuangsan <p>
 * Description: 常规的图片pagerAdapter
 */
public class NormalImagePagerAdapter extends android.support.v4.view.PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    final int[] mItems;

    public NormalImagePagerAdapter(Context context, int[] items) {
        this.mContext = context;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item_normalpager_imge, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.iv_normal_pager);
        imageView.setImageResource(mItems[position]);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
