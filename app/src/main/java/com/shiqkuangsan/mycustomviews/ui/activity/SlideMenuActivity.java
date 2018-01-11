package com.shiqkuangsan.mycustomviews.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.base.FrameBaseActivity;
import com.shiqkuangsan.mycustomviews.ui.custom.SlideDeleteView;

/**
 * Created by shiqkuangsan on 2016/5/6.
 */
public class SlideMenuActivity extends FrameBaseActivity {

    private SlideDeleteView slidemenu;
    private ImageButton ib_back;
    private Button btn_back;

    @Override
    public void initView() {
        setContentView(R.layout.activity_slidemenu);

        slidemenu = (SlideDeleteView) findViewById(R.id.slidemenu);
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
                if (slidemenu.getCurrent_Mode() == SlideDeleteView.IN_MENU_MODE)
                    slidemenu.changeState();
                break;

        }
    }
}
