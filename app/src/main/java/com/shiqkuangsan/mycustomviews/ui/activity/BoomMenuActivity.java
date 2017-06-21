package com.shiqkuangsan.mycustomviews.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.shiqkuangsan.mycustomviews.MyApplication;
import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.adapter.ViewPagerAdapter;
import com.shiqkuangsan.mycustomviews.ui.BuilderManager;
import com.shiqkuangsan.mycustomviews.utils.ToastUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by shiqkuangsan on 2017/6/14.
 * <p>
 * ClassName: BoomMenuActivity
 * Author: shiqkuangsan
 * Description: 弹出多个按钮的菜单界面
 */
public class BoomMenuActivity extends AppCompatActivity {

    @ViewInject(R.id.navigation_menu)
    BottomNavigationView navigationView;
    @ViewInject(R.id.bmb_boom)
    BoomMenuButton bmb_boom;
    @ViewInject(R.id.viewpager_boommenu)
    ViewPager viewpager_boommenu;

    MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boom_menu);
        x.view().inject(this);

        initNavigation();
        initBoomButton();
        initViewPager();
    }

    private void initViewPager() {
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.setTITLES(new String[]{"Movies","Music","Picture","Books","Newpaper"});
        viewpager_boommenu.setAdapter(pagerAdapter);
        viewpager_boommenu.setCurrentItem(2);
        viewpager_boommenu.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    navigationView.getMenu().getItem(0).setChecked(false);
                }
                navigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = navigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initBoomButton() {
        bmb_boom.setCancelable(false);
        bmb_boom.setButtonEnum(ButtonEnum.TextOutsideCircle);
        bmb_boom.setPiecePlaceEnum(PiecePlaceEnum.DOT_6_1);
        bmb_boom.setButtonPlaceEnum(ButtonPlaceEnum.SC_6_1);
        for (int i = 0; i < bmb_boom.getPiecePlaceEnum().pieceNumber() - 1; i++) {
            bmb_boom.addBuilder(BuilderManager.getTextOutsideCircleButtonBuilder());
        }
        bmb_boom.addBuilder(new TextOutsideCircleButton.Builder()
                .normalImageRes(R.drawable.bat)
                .normalText("点我啊")
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        MyApplication.getHandler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(BoomMenuActivity.this, BoomMenu2ndActivity.class));
                            }
                        }, 800);
                    }
                }));
    }

    private void initNavigation() {
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_movie:
                        ToastUtil.toastShort(BoomMenuActivity.this, "Moview");
                        navigationView.setItemBackgroundResource(R.color.sienna);
                        viewpager_boommenu.setCurrentItem(0);
                        break;
                    case R.id.menu_music:
                        ToastUtil.toastShort(BoomMenuActivity.this, "Music");
                        navigationView.setItemBackgroundResource(R.color.red);
                        viewpager_boommenu.setCurrentItem(1);
                        break;
                    case R.id.menu_picture:
                        ToastUtil.toastShort(BoomMenuActivity.this, "Picutre");
                        navigationView.setItemBackgroundResource(R.color.colorPrimary);
                        viewpager_boommenu.setCurrentItem(2);
                        break;
                    case R.id.menu_book:
                        ToastUtil.toastShort(BoomMenuActivity.this, "Book");
                        navigationView.setItemBackgroundResource(R.color.green);
                        viewpager_boommenu.setCurrentItem(3);
                        break;
                    case R.id.menu_newspaper:
                        ToastUtil.toastShort(BoomMenuActivity.this, "News");
                        navigationView.setItemBackgroundResource(R.color.darkorchid);
                        viewpager_boommenu.setCurrentItem(4);
                        break;
                }
                return true;
            }
        });
        navigationView.setSelectedItemId(R.id.menu_picture);
    }

}
