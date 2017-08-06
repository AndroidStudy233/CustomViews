package com.shiqkuangsan.mycustomviews.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shiqkuangsan.mycustomviews.ui.fragment.EasyPagerFragment;

/**
 * <p>创建人：余志伟</p>
 * <p>创建时间：2016-03-15 16:35</p>
 * <p>修改人：       </p>
 * <p>修改时间：   </p>
 * <p>修改备注：   </p>
 *
 * @version V3.1
 */
public class EasyPagerAdapter extends FragmentPagerAdapter {
    private String[] TITLES = {"全部", "成都", "重庆", "全部1", "成都1", "重庆1", "全部2", "成都2", "重庆2",};

    public EasyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setTitles(String[] titles){
        TITLES = titles;
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        return EasyPagerFragment.getInstance(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

}
