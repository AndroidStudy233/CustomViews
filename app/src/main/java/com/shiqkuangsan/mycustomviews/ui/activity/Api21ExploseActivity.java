package com.shiqkuangsan.mycustomviews.ui.activity;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.ui.activity.api21.Api21Activity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import tyrantgit.explosionfield.ExplosionField;

/**
 * 5.0Activity切换动画和共享元素转场动画.以及圆形揭示/隐藏
 * view碎裂效果是explosionfield类库做的
 * 'tyrantgit:explosionfield:1.0.1'
 */
@TargetApi(21)
@ContentView(R.layout.activity_api21_demo)
public class Api21ExploseActivity extends AppCompatActivity {

    @ViewInject(R.id.iv_explode_1)
    ImageView iv_explode_1;
    @ViewInject(R.id.iv_explode_2)
    ImageView iv_explode_2;
    @ViewInject(R.id.iv_explode_3)
    ImageView iv_explode_3;
    @ViewInject(R.id.iv_explode_4)
    ImageView iv_explode_4;
    @ViewInject(R.id.iv_explode_5)
    ImageView iv_explode_5;
    @ViewInject(R.id.iv_explode_6)
    ImageView iv_explode_6;
    private ExplosionField explose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        explose = ExplosionField.attach2Window(this);

    }

    @Event(value = {R.id.btn_ripple_explode, R.id.iv_explode_1, R.id.iv_explode_2, R.id.iv_explode_3,
            R.id.iv_explode_4, R.id.iv_explode_5, R.id.iv_explode_6})
    private void process(View view) {
        if (view.getId() == R.id.btn_ripple_explode) {
            explose.clear();
        } else {
            if (view.isEnabled()) {
                if (view.getId() == R.id.iv_explode_6) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Api21ExploseActivity.this, iv_explode_6, "transition_youtube");
                    startActivity(new Intent(getApplicationContext(), Api21Activity.class), options.toBundle());
                } else {
                    explose.explode(view);
                    view.setEnabled(false);
                }
            }
        }
    }
}
