package com.shiqkuangsan.mycustomviews.ui.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.shiqkuangsan.baiducityselector.CitySelectorActivity;
import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.base.BaseActivity;
import com.shiqkuangsan.mycustomviews.utils.AnimationUtil;
import com.shiqkuangsan.mycustomviews.utils.MyLogUtil;

// 主界面,并且负责演示炫酷菜单
public class MainActivity extends BaseActivity {

    private RelativeLayout rl_level1;
    private RelativeLayout rl_level2;
    private RelativeLayout rl_level3;
    boolean isLevel3Display = true;
    boolean isLevel2Display = true;
    boolean isLevel1Display = true;
    private ImageButton ib_menu;
    private ImageButton ib_home;


    @Override
    public void initView() {
        setContentView(R.layout.activity_main);

        // 添加点击事件
        ib_home = (ImageButton) findViewById(R.id.ib_home);
        ib_menu = (ImageButton) findViewById(R.id.ib_menu);

        rl_level1 = (RelativeLayout) findViewById(R.id.rl_level1);
        rl_level2 = (RelativeLayout) findViewById(R.id.rl_level2);
        rl_level3 = (RelativeLayout) findViewById(R.id.rl_level3);

    }

    @Override
    public void initDataAndListener() {
        ib_home.setOnClickListener(this);
        ib_menu.setOnClickListener(this);
        initLocation();
    }

    private AMapLocationClient mLocationClient;

    private void initLocation() {
        mLocationClient = new AMapLocationClient(this);
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
//        option.setOnceLocation(true);
        option.setNeedAddress(true);
        option.setInterval(10000);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        String city = aMapLocation.getCity();
                        String district = aMapLocation.getDistrict();
                        MyLogUtil.d("city: " + city);
                        MyLogUtil.d("district: " + district);
                    } else {
                        //定位失败
                    }
                }
            }
        });
        mLocationClient.setLocationOption(option);
        mLocationClient.startLocation();
    }

    @Override
    public void processClick(View v) {
        if (AnimationUtil.oneAnimRunning)
            // 当前有动画正在执行, 取消当前事件
            return;

        switch (v.getId()) {
            case R.id.ib_home:

                if (isLevel2Display) {
                    long delay = 0;
                    // 如果当前三级菜单已经显示, 先转出去
                    if (isLevel3Display) {
                        AnimationUtil.rotateOutofWindow(rl_level3, 0);
                        isLevel3Display = false;
                        delay += 200;
                    }

                    // 如果当前二级菜单已经显示, 转出去
                    AnimationUtil.rotateOutofWindow(rl_level2, delay);
                    isLevel2Display = !isLevel2Display;
                } else {
                    // 如果当前二级菜单没有显示, 转出来
                    AnimationUtil.rotateBackofWindow(rl_level2, 0);

                    // 置反
                    isLevel2Display = !isLevel2Display;
                }
                break;

            case R.id.ib_menu:
                if (isLevel3Display) {
                    // 如果当前三级菜单已经显示, 转出去
                    AnimationUtil.rotateOutofWindow(rl_level3, 0);
                } else {
                    // 如果当前三级菜单没有显示, 转出来
                    AnimationUtil.rotateBackofWindow(rl_level3, 0);
                }
                // 置反
                isLevel3Display = !isLevel3Display;
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // keyCode 事件码
//        System.out.println("onKeyDown: " + keyCode);
//			如果按下的是菜单按钮
        if (keyCode == KeyEvent.KEYCODE_MENU) {

            if (AnimationUtil.oneAnimRunning)
                // 当前有动画正在执行, 取消当前事件
                return true;

            if (isLevel1Display) {
                long delay = 0;
                // 隐藏三级菜单
                if (isLevel3Display) {
                    AnimationUtil.rotateOutofWindow(rl_level3, 0);
                    isLevel3Display = false;
                    delay += 200;
                }

                // 隐藏二级菜单
                if (isLevel2Display) {
                    AnimationUtil.rotateOutofWindow(rl_level2, delay);
                    isLevel2Display = false;
                    delay += 200;
                }

                // 隐藏一级菜单
                AnimationUtil.rotateOutofWindow(rl_level1, delay);

            } else {
                // 顺次转进来
                AnimationUtil.rotateBackofWindow(rl_level1, 0);
                AnimationUtil.rotateBackofWindow(rl_level2, 200);
                AnimationUtil.rotateBackofWindow(rl_level3, 400);

                isLevel3Display = true;
                isLevel2Display = true;
            }
            isLevel1Display = !isLevel1Display;

            return true;// 消费了当前事件
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * 轮播图
     *
     * @param view
     */
    public void switchpicture(View view) {
        startActivity(new Intent(this, SwitchPictureActivity.class));
    }

    /**
     * 自定义小开关
     *
     * @param view
     */
    public void myonoff(View view) {
        startActivity(new Intent(this, MyOnOffActivity.class));
    }

    /**
     * 下拉刷新
     *
     * @param view
     */
    public void droprefresh(View view) {
        startActivity(new Intent(this, PullRefreshActivity.class));
    }

    /**
     * 侧滑菜单
     *
     * @param view
     */
    public void slidemenu(View view) {
        startActivity(new Intent(this, SlideMenuActivity.class));
    }

    /**
     * 下拉刷新测试
     *
     * @param view
     */
    public void ptrtest(View view) {
        startActivity(new Intent(this, PtrDemoActivity.class));
    }

    /**
     * 图片选择
     *
     * @param view
     */
    public void picchoser(View view) {
        startActivity(new Intent(this, PicChooserActivity.class));
    }

    /**
     * 图片查看
     *
     * @param view
     */
    public void piclooker(View view) {
        startActivity(new Intent(this, PicLookerActivity.class));
    }

    /**
     * xUtils测试
     *
     * @param view
     */
    public void xutils(View view) {
        startActivity(new Intent(this, XUtilsActivity.class));
    }

    /**
     * CoodinatorLayout
     *
     * @param view
     */
    public void coordinator(View view) {
        startActivity(new Intent(this, CoordinatorActivity.class));
    }

    /**
     * RecyclerView和AutoRatioLayout和CardView
     *
     * @param view
     */
    public void recycler(View view) {
        startActivity(new Intent(this, RecyclerViewActivity.class));
    }

    /**
     * 调试沉浸式的页面
     *
     * @param view
     */
    public void immerse(View view) {
        startActivity(new Intent(this, ImmerseActivity.class));
    }

    /**
     * 城市列表页面
     *
     * @param view
     */
    public void citypicker(View view) {
        startActivity(new Intent(this, CitySelectorActivity.class));
    }

    /**
     * 滑动返回上个界面演示
     *
     * @param view
     */
    public void swipeback(View view) {
        startActivity(new Intent(this, TestSwipeBackActivity.class));
    }

    /**
     * MD风格设置界面
     *
     * @param view
     */
    public void mdsettings(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }

}
