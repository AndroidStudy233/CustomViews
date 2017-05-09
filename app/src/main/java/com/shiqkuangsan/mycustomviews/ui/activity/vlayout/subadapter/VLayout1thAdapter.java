package com.shiqkuangsan.mycustomviews.ui.activity.vlayout.subadapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.shiqkuangsan.mycustomviews.R;

import static java.security.AccessController.getContext;

/**
 * Created by shiqkuangsan on 2017/5/9.
 * <p>
 * ClassName: VLayout1thAdapter
 * Author: shiqkuangsan
 * Description: VLayout的子适配器, 都继承DelegateAdapter.Adapter,
 * 写好之后再让DelegateAdapter的addAdapter方法添加进去
 */
public class VLayout1thAdapter extends DelegateAdapter.Adapter<VLayout1thAdapter.VLayoutViewHolder> {

    private Context context;
    private LayoutHelper layoutHelper;
    private int count = 0;

    public VLayout1thAdapter(Context context, LayoutHelper layoutHelper, int count) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.count = count;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public VLayoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_vlayout1th, null);
            return new VLayoutViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(VLayoutViewHolder holder, int position) {

        /*
            这里注意有个遗留问题, 如果不给viewPager或者他爹设置布局参数LayoutParams(特别是高度)
            他将不知道自己高多少  导致看不见条目
         */
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();

        holder.viewPager.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, (int) (width / 1.7)));
        holder.viewPager.setAdapter(new ViewPagerAdapter(context));
    }

    @Override
    public void onViewRecycled(VLayoutViewHolder holder) {
        holder.viewPager.setAdapter(null);
    }

    @Override
    public int getItemCount() {
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    class VLayoutViewHolder extends RecyclerView.ViewHolder {

        private final ViewPager viewPager;

        VLayoutViewHolder(View itemView) {
            super(itemView);
            viewPager = (ViewPager) itemView.findViewById(R.id.pager_vlayout1th);
        }
    }

    class ViewPagerAdapter extends PagerAdapter {

        Context context;

        ViewPagerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_vlayout1th_pageritem, null);
            ImageView iv_pic = (ImageView) view.findViewById(R.id.iv_vlayout1th_cardimg);
            // 固定写死就5个条目
            if (position % 2 == 0)
                iv_pic.setImageResource(R.mipmap.img800x470_1);
            else
                iv_pic.setImageResource(R.mipmap.img800x470_2);

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
