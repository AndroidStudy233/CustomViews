package com.shiqkuangsan.mycustomviews.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.adapter.EasyPagerAdapter;
import com.shiqkuangsan.mycustomviews.ui.custom.IndicatorView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/*************************************************
 * <p>创建人：余志伟</p>
 * <p>创建时间 : 2017/5/26</p>
 * <p>修改人：       </p>
 * <p>修改时间：   </p>
 * <p>修改备注：   </p>
 *
 * @version V3.1
 *********************************/
public class IndicatorActivity extends AppCompatActivity {
    @ViewInject(R.id.indicator_viewpager)
    ViewPager viewPager;
    @ViewInject(R.id.indicator_view)
    IndicatorView indicatorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator);
        x.view().inject(this);
        indicatorView.setCircleNum(9);//和数据个数关联
        indicatorView.setDefautSelectPosition(2);//设置默认选中
        indicatorView.setViewPager(viewPager);//设置指示view和viewpager关联
        viewPager.setAdapter(new EasyPagerAdapter(getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                indicatorView.setPoints(position,positionOffset,positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position);
                indicatorView.setcurrentSelect(position) ;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(2);//viewpager和设置默认的相同
    }
}
