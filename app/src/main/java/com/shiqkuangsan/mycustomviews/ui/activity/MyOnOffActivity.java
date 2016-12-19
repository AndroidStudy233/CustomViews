package com.shiqkuangsan.mycustomviews.ui.activity;

import android.view.View;
import android.widget.Toast;
import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.base.BaseActivity;
import com.shiqkuangsan.mycustomviews.ui.custom.MyOnOffView;

/**
 * Created by shiqkuangsan on 2016/5/4.
 */
// 演示自定义开关的界面
public class MyOnOffActivity extends BaseActivity{

    private MyOnOffView mySwtich;

    @Override
    public void initView() {
        setContentView(R.layout.activity_my_onoff);

        // 使用自定义的组件
        mySwtich = (MyOnOffView) findViewById(R.id.custom_switch);
        // 直接使用自定义属性,可以在xml文件中直接使用了
//        mySwtich.setSwitchBackground(R.drawable.bg_swith_onoff);
//        mySwtich.setSwitchSlideButton(R.drawable.img_switch_btn);
//        mySwtich.setSwitchState(false);
        mySwtich.setOnSwitchStateChangeListener(new MyOnOffView.OnSwitchStateChangeListener() {
            @Override
            public void onStateChange(boolean state) {
                Toast.makeText(MyOnOffActivity.this, "state: " + state, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initDataAndListener() {

    }

    @Override
    public void processClick(View view) {

    }
}
