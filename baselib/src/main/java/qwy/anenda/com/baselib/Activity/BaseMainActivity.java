package qwy.anenda.com.baselib.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import qwy.anenda.com.baselib.Fragment.FouthFragment;
import qwy.anenda.com.baselib.Fragment.IndexFragment;
import qwy.anenda.com.baselib.Fragment.SecondFragment;
import qwy.anenda.com.baselib.Fragment.ThirdFragment;
import qwy.anenda.com.baselib.R;
import qwy.anenda.com.baselib.base.BaseActivity;
import qwy.anenda.com.baselib.base.BaseFragment;
import qwy.anenda.com.baselib.utils.ToastUtils;

/**
 * Description: 应用程序主页面
 */
public class BaseMainActivity extends BaseActivity {
    private BottomNavigationBar bottomNavigationBar;
    private FrameLayout contenFrameLayout;
    private BaseFragment indexFragment;
    private BaseFragment secondFragment;
    private BaseFragment thirdFragment;
    private BaseFragment fouthFragment;
    List<BaseFragment> fragments = new ArrayList<>();
    private int currentPosition;


    @Override
    protected int layoutId() {
        return R.layout.activity_basemain;
    }


    @Override
    public void initView() {
        bottomNavigationBar = findViewById(R.id.bna);
        contenFrameLayout =  findViewById(R.id.content_layout);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.pic_eva, "首页"));
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.pic_reform, "整改"));
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.pic_notify, "公告"));
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.pic_mine, "我的"));
        bottomNavigationBar.selectTab(0);
        bottomNavigationBar.initialise();
        setBottomNavigationItem(bottomNavigationBar, 8, 24, 10);
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                if (currentPosition != position) {
                    switchFragment(fragments.get(currentPosition), fragments.get(position), getSupportFragmentManager(), R.id.content_layout);
                    currentPosition = position;
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
        initFragment();
        switchFragment(null, fragments.get(0), getSupportFragmentManager(), R.id.content_layout);
    }

    @Override
    public void BindEvent() {
    }

    public void initFragment() {
        indexFragment = new IndexFragment();
        secondFragment = new SecondFragment();
        thirdFragment = new ThirdFragment();
        fouthFragment = new FouthFragment();
        fragments.add(indexFragment);
        fragments.add(secondFragment);
        fragments.add(thirdFragment);
        fragments.add(fouthFragment);
    }




    /*
     * @param bottomNavigationBar，需要修改的 BottomNavigationBar
     * @param space 图片与文字之间的间距
     * @param imgLen 单位：dp，图片大小，应 <= 36dp
     * @param textSize 单位：dp，文字大小，应 <= 20dp
     *
     *  使用方法：直接调用setBottomNavigationItem(bottomNavigationBar, 6, 26, 10);
     *  代表将bottomNavigationBar的文字大小设置为10dp，图片大小为26dp，二者间间距为6dp
     *
     * */
    private void setBottomNavigationItem(BottomNavigationBar bottomNavigationBar, int space, int imgLen, int textSize) {
        Class barClass = bottomNavigationBar.getClass();
        Field[] fields = barClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            if (field.getName().equals("mTabContainer")) {
                try {
                    //反射得到 mTabContainer
                    LinearLayout mTabContainer = (LinearLayout) field.get(bottomNavigationBar);
//                    FrameLayout.LayoutParams mTabParams = (FrameLayout.LayoutParams) mTabContainer.getLayoutParams();
//                    mTabContainer.setLayoutParams(mTabParams);
                    for (int j = 0; j < mTabContainer.getChildCount(); j++) {
                        //获取到容器内的各个Tab
                        View view = mTabContainer.getChildAt(j);
//                        //获取到Tab内的各个显示控件
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(56));
                        FrameLayout container = view.findViewById(R.id.fixed_bottom_navigation_container);
                        container.setLayoutParams(params);
                        container.setPadding(dip2px(12), dip2px(0), dip2px(12), dip2px(0));

                        //获取到Tab内的文字控件
                        TextView labelView = view.findViewById(com.ashokvarma.bottomnavigation.R.id.fixed_bottom_navigation_title);
                        //计算文字的高度DP值并设置，setTextSize为设置文字正方形的对角线长度，所以：文字高度（总内容高度减去间距和图片高度）*根号2即为对角线长度，此处用DP值，设置该值即可。
                        labelView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
                        labelView.setIncludeFontPadding(false);
                        labelView.setPadding(0, 0, 0, dip2px(20 - textSize - space / 2));

                        //获取到Tab内的图像控件
                        ImageView iconView = view.findViewById(com.ashokvarma.bottomnavigation.R.id.fixed_bottom_navigation_icon);
                        //设置图片参数，其中，MethodUtils.dip2px()：换算dp值
                        params = new FrameLayout.LayoutParams(dip2px(imgLen), dip2px(imgLen));
                        params.setMargins(0, 0, 0, space / 2);
                        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                        iconView.setLayoutParams(params);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int dip2px(float dpValue) {
        final float scale = getApplication().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    // 双击退出登录
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.shortShowStr("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
