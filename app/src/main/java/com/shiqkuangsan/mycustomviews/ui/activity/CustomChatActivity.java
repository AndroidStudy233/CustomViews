package com.shiqkuangsan.mycustomviews.ui.activity;

import android.os.Bundle;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.base.BaseActivity;
import com.shiqkuangsan.mycustomviews.ui.custom.MpChat;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/*************************************************
 * <p>创建人：余志伟</p>
 * <p>创建时间 : 2017/5/25</p>
 * <p>修改人：       </p>
 * <p>修改时间：   </p>
 * <p>修改备注：   </p>
 *
 * @version V3.1
 *********************************/
public class CustomChatActivity extends BaseActivity {
    @ViewInject(R.id.customac_mpchat)
    MpChat mpChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customchat);
        x.view().inject(this);
        new Thread(mpChat).start();

    }
}
