package com.shiqkuangsan.mycustomviews.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by shiqkuangsan on 2016/5/4.
 */

/**
 * 轮播图界面的ViewPager适配器
 */
public class PicPagerAdapter extends PagerAdapter {

    private List<View> list;

    public PicPagerAdapter(List<View> list) {
        this.list = list;
    }

    /*
        ViewPager的条目数量由该方法返回值决定,要想实现无线循环,这里直接给个最大值,然后对其返回的条目做
        适当的操作即可
     */
    @Override
    public int getCount() {
//        return list.size();
        return Integer.MAX_VALUE;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        // 当滑到新的条目又返回来的时候view是否可以被复用
        return view == object;
    }

    // 返回的对象就是要显示的条目的内容
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        // 这里我进行一个小操作,本来当你滑到5的时候list没数据了,不过我让你从0再开始,这就可以了,这就是先往右无限循环
        // 往左无限循环怎么实现呢,我给你再设置好适配器之后,直接跳转到Integer.MAX_VALUE的某个位置就好了啊
        int newPosition = position % list.size();
        ImageView view = (ImageView) list.get(newPosition);
        // 必须要手动将显示的条目添加到容器中,然后将条目返回
        container.addView(view);

        return view;
    }

    // 销毁条目
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 这儿的object就是上面返回的条目(其实是个ImageView)
        container.removeView((View) object);
    }
}
