package com.shiqkuangsan.mycustomviews.ui.activity.immerse;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shiqkuangsan.mycustomviews.R;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import static com.shiqkuangsan.mycustomviews.utils.UIUitl.calculateColorWithOpacity;

/**
 * Created by shiqkuangsan on 2017/8/2. <p>
 * ClassName: ColorImmerse2Act <p>
 * Author: shiqkuangsan <p>
 * Description: 颜色沉浸式界面2 -> 自定义Topbar
 */
public class ColorImmerse2Act extends AppCompatActivity {

    @ViewInject(R.id.rl_immerse_topbar)
    LinearLayout topbar;
    @ViewInject(R.id.tv_immerse_title)
    TextView title;
    @ViewInject(R.id.btn_immerse_red1)
    Button btn_red;
    @ViewInject(R.id.btn_immerse_green1)
    Button btn_green;
    @ViewInject(R.id.btn_immerse_blue1)
    Button btn_blue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_immerse2);
        x.view().inject(this);

    }

    @Event(value = {R.id.iv_immerse_back, R.id.btn_immerse_red2, R.id.btn_immerse_green2, R.id.btn_immerse_blue2})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_immerse_back:
                finish();
                break;
            case R.id.btn_immerse_red2:
                setTopbarColor(R.color.red);
                setStatusBarColor(R.color.red);
                break;
            case R.id.btn_immerse_green2:
                setTopbarColor(R.color.green);
                setStatusBarColor(R.color.green);
                break;
            case R.id.btn_immerse_blue2:
                setTopbarColor(R.color.blue);
                setStatusBarColor(R.color.blue, 0);
                break;
        }
    }

    private void setTopbarColor(int colorId) {
        int color = getResources().getColor(colorId);
        topbar.setBackgroundColor(color);
    }

    @TargetApi(21)
    private void setStatusBarColor(int colorId) {
        setStatusBarColor(colorId, 50);
    }

    @TargetApi(21)
    private void setStatusBarColor(int colorId, int alpha) {
        int color = getResources().getColor(colorId);
        Window window = getWindow();
        // 相当于style中配置windowTranslucentStatus -> true
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // 相当于style中配置windowTranslucentNavigation -> false
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 手动设置状态颜色
        window.setStatusBarColor(calculateColorWithOpacity(color, alpha));
        // 手动设置导航栏颜色
        window.setNavigationBarColor(calculateColorWithOpacity(color, alpha));
    }

}
