package com.shiqkuangsan.mycustomviews.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.bean.Doctor;
import com.shiqkuangsan.mycustomviews.constant.Constant;
import com.shiqkuangsan.mycustomviews.db.RealmManager;
import com.shiqkuangsan.mycustomviews.utils.InputMethodUtil;
import com.shiqkuangsan.mycustomviews.utils.StringUtil;
import com.shiqkuangsan.mycustomviews.utils.ToastUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import io.realm.Realm;
import io.realm.RealmResults;

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
    @ViewInject(R.id.et_realm_name)
    EditText et_name;
    @ViewInject(R.id.et_realm_age)
    EditText et_age;
    @ViewInject(R.id.et_realm_hospital)
    EditText et_hospital;
    @ViewInject(R.id.et_realm_skill)
    EditText et_skill;
    @ViewInject(R.id.tv_realm_result)
    TextView tv_result;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realm_test);
        x.view().inject(this);

        realm = RealmManager.getInstance(Constant.name_test_realm, 1).getRealm();
    }

    @Event(value = {R.id.btn_realm_insert,R.id.btn_realm_query,R.id.btn_realm_queryAll,R.id.btn_realm_delete})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_realm_insert:
                String name = et_name.getText().toString().trim();
                String age = et_age.getText().toString().trim();
                String hospital = et_hospital.getText().toString().trim();
                String skill = et_skill.getText().toString().trim();
                if (!isAllInputValid(name, age, hospital, skill)) {
                    ToastUtil.shortToast(this, "以上信息有格式不对,请核对");
                    return;
                }
                final Doctor doctor = new Doctor(name, age, hospital, skill);
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealmOrUpdate(doctor);
                        refreshUI();
                        ToastUtil.shortToast(RealmTestActivity.this, "插入成功");
                        InputMethodUtil.closeSoftKeyboard(RealmTestActivity.this);
                    }
                });

                break;

            case R.id.btn_realm_query:
                final String name2 = et_name.getText().toString().trim();
                if (isNameValid(name2))
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Doctor doc = realm.where(Doctor.class).equalTo("name", name2).findFirst();
                            if (doc!=null) {
                                et_name.setText(doc.getName());
                                et_age.setText(doc.getAge());
                                et_hospital.setText(doc.getHospital());
                                et_skill.setText(doc.getSkill());
                                tv_result.setText("查询结果: " + "\t" + "Doctor: " + doc.getName());
                                InputMethodUtil.closeSoftKeyboard(RealmTestActivity.this);
                            } else
                                ToastUtil.shortToast(RealmTestActivity.this, "尚未存储");
                        }
                    });
                else
                    ToastUtil.shortToast(RealmTestActivity.this, "无效的名称");
                break;

            case R.id.btn_realm_queryAll:
                RealmResults<Doctor> doctors = realm.where(Doctor.class).findAll();
                if (doctors.size() > 0) {
                    String text = "查询结果: ";
                    for (Doctor doc : doctors) {
                        refreshUI();
                        text += "Doctor: " + doc.getName() + "\n";
                    }
                    tv_result.setText(text);
                    InputMethodUtil.closeSoftKeyboard(RealmTestActivity.this);
                } else
                    ToastUtil.shortToast(RealmTestActivity.this, "尚无数据");
                break;

            case R.id.btn_realm_delete:
                final String name3 = et_name.getText().toString().trim();
                if (isNameValid(name3))
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Doctor doc = realm.where(Doctor.class).equalTo("name", name3).findFirst();
                            if (StringUtil.isEmpty(doc.getName()))
                                ToastUtil.shortToast(RealmTestActivity.this, "尚未存储");
                            else {
                                doc.deleteFromRealm();
                                refreshUI();
                                ToastUtil.shortToast(RealmTestActivity.this, "删除成功");
                                InputMethodUtil.closeSoftKeyboard(RealmTestActivity.this);
                            }

                        }
                    });
                else
                    ToastUtil.shortToast(RealmTestActivity.this, "无效的名称");
                break;

        }
    }

    private void refreshUI() {
        et_name.setText("");
        et_age.setText("");
        et_hospital.setText("");
        et_skill.setText("");
    }

    private boolean isAllInputValid(String name, String age, String hos, String skill) {
        return isNameValid(name) && isAgeValid(age) && isHospitalValid(hos) && isSkillValid(skill);
    }

    private boolean isNameValid(String name) {
        return !StringUtil.isEmpty(name) && name.length() <= 10;
    }

    private boolean isAgeValid(String age) {
        return !StringUtil.isEmpty(age) && Integer.parseInt(age) >= 18 && Integer.parseInt(age) <= 150;
    }

    private boolean isHospitalValid(String hospital) {
        return !StringUtil.isEmpty(hospital) && hospital.length() <= 20;
    }

    private boolean isSkillValid(String skill) {
        return !StringUtil.isEmpty(skill) && skill.length() <= 20;
    }


}
