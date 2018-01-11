package com.shiqkuangsan.mycustomviews.base;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.xutils.x;

/**
 * Created by shiqkuangsan on 2016/5/4.
 */
// 基类Activity
public abstract class FrameBaseActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //xUtils注解框架
        x.view().inject(this);

        initView();
        initDataAndListener();
    }

    /**
     * 初始化布局
     */
    public abstract void initView();

    /**
     * 初始化数据及设置监听
     */

    public abstract void initDataAndListener();

    /**
     * 点击监听
     */
    public abstract void processClick(View view);

    @Override
    public void onClick(View v) {
        processClick(v);
    }

    protected Toast toast;

    /**
     * 重写Toast 避免重复弹
     * @param msg
     */
    protected void showToast(String msg) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(this, msg + "", Toast.LENGTH_SHORT);
        toast.show();
    }
}
