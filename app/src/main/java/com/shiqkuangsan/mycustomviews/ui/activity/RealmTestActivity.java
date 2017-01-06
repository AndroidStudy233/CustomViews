package com.shiqkuangsan.mycustomviews.ui.activity;

import android.os.Bundle;
import android.service.carrier.CarrierService;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.constant.Constant;
import com.shiqkuangsan.mycustomviews.db.RealmManager;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by shiqkuangsan on 2016/12/30.
 * <p>
 * author: shiqkuangsan
 * description: realm数据基本功能测试
 */
public class RealmTestActivity extends AppCompatActivity {

    @ViewInject(R.id.btn_realm_insert)
    Button btn_inset;
    @ViewInject(R.id.btn_realm_query)
    Button btn_queryOne;
    @ViewInject(R.id.btn_realm_queryAll)
    Button btn_queryAll;
    @ViewInject(R.id.btn_realm_delete)
    Button btn_delete;
    @ViewInject(R.id.btn_realm_update)
    Button btn_update;
    @ViewInject(R.id.et_realm_name)
    EditText et_name;
    @ViewInject(R.id.et_realm_age)
    EditText et_age;
    @ViewInject(R.id.et_realm_hospital)
    EditText et_hospital;
    @ViewInject(R.id.et_realm_skill)
    EditText et_skill;


    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realm_test);
        x.view().inject(this);

        realm = RealmManager.getInstance(Constant.name_test_realm, 1);
        realm.deleteAll();// 清空数据库

    }

    @Event(value = {R.id.btn_realm_insert})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_realm_insert:
                break;

        }
    }
}
