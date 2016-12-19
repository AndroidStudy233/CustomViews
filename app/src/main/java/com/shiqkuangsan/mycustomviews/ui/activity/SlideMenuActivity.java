package com.shiqkuangsan.mycustomviews.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.base.BaseActivity;
import com.shiqkuangsan.mycustomviews.ui.custom.SlideMenuView;

/**
 * Created by shiqkuangsan on 2016/5/6.
 */
public class SlideMenuActivity extends BaseActivity {

    private SlideMenuView slidemenu;
    private ImageButton ib_back;
    private Button btn_back;

    @Override
    public void initView() {
        setContentView(R.layout.activity_slidemenu);

        slidemenu = (SlideMenuView) findViewById(R.id.slidemenu);
        ib_back = (ImageButton) findViewById(R.id.ib_back);
        btn_back = (Button) findViewById(R.id.btn_back);
    }

    @Override
    public void initDataAndListener() {
        ib_back.setOnClickListener(this);
        btn_back.setOnClickListener(this);
    }

    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                slidemenu.changeState();
                break;

            case R.id.btn_back:
                if (slidemenu.getCurrent_Mode() == SlideMenuView.IN_MENU_MODE)
                    slidemenu.changeState();
                break;

        }
    }
}
