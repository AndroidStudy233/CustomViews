package com.shiqkuangsan.mycustomviews.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.ui.BuilderManager;
import com.shiqkuangsan.mycustomviews.utils.ToastUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by shiqkuangsan on 2017/6/15.
 * <p>
 * ClassName: BoomMenu2ndActivity
 * Author: shiqkuangsan
 * Description: 演示BoomMenu方式2
 */
public class BoomMenu2ndActivity extends AppCompatActivity {

    @ViewInject(R.id.bmb_boom2nd)
    BoomMenuButton bmb_boom2nd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boom_menu2nd);
        x.view().inject(this);

        initBoomButton();
    }

    private void initBoomButton() {
        // 设置可拖拽
        bmb_boom2nd.setDraggable(true);
        bmb_boom2nd.setCancelable(true);
        bmb_boom2nd.setButtonEnum(ButtonEnum.Ham);
        bmb_boom2nd.setPiecePlaceEnum(PiecePlaceEnum.HAM_4);
        bmb_boom2nd.setButtonPlaceEnum(ButtonPlaceEnum.HAM_4);
        for (int i = 0; i < bmb_boom2nd.getPiecePlaceEnum().pieceNumber() - 1; i++) {
            bmb_boom2nd.addBuilder(BuilderManager.getHamButtonBuilder());
        }
        bmb_boom2nd.addBuilder(new HamButton.Builder()
                .normalImageRes(R.drawable.cat)
                .normalText("再点, 再点我就...")
                .subNormalText("嘿嘿嘿")
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        ToastUtil.toastShort(BoomMenu2ndActivity.this, "Fuck away!");
                    }
                }));

    }
}
