package qwy.anenda.com.baselib.Activity;
 /* 
 -----------------------------------------------------------------
 * Author: yzw
 * Create: 2019/2/25
 * Descride:
 * 
 *-----------------------------------------------------------------
 */

import android.Manifest;
import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Date;
import io.reactivex.functions.Consumer;
import qwy.anenda.com.baselib.R;
import qwy.anenda.com.baselib.base.BaseActivity;
import qwy.anenda.com.baselib.utils.CommonUtils;
import qwy.anenda.com.baselib.utils.PreferenceUtil;
import qwy.anenda.com.baselib.utils.ToastUtils;


public class LoginActivity extends BaseActivity {

    private boolean isPwdLogin = true;
    private RxPermissions rxPermissions;
    private String Imei;
    private boolean autoLogin = false;
    private View btnLogin;
    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }

    @SuppressLint("CheckResult")
    @Override
    public void initView() {
        rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Imei = CommonUtils.getImeiOrMeid(LoginActivity.this);
                        }
                    }
                });
        btnLogin = findViewById( R.id.btn_enter);

    }

    @Override
    public void BindEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(BaseMainActivity.class);
                finish();
            }
        });
    }


    public boolean checkDataIlgel() {
        return true;
    }
}
