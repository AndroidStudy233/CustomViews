package qwy.anenda.com.baselib.base;
 /* 
 -----------------------------------------------------------------
 * Author: yzw
 * Create: 2019/2/25
 * Descride:
 * 
 *-----------------------------------------------------------------
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import qwy.anenda.com.baselib.R;
import qwy.anenda.com.baselib.utils.AppManagerUtil;
import qwy.anenda.com.baselib.widget.DialogProgress;


public abstract class BaseActivity extends AppCompatActivity {
    protected boolean isTransparent = false;
    private InputMethodManager inputManager;
    protected Toolbar toolbar;
    protected Bundle savedInstanceState;
    protected boolean isAddCenterView;
    protected DialogProgress loadProcess;
    protected RxPermissions rxPermissions;
    protected DialogProgress dialogProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        // 透明导航栏/状态栏
//        transparentStatusBar();
//        transparentNavigationBar();
        try {
            rxPermissions = new RxPermissions(this);
            AppManagerUtil.getAppManager().addActivity(this);
            setContentView(layoutId());
//            ButterKnife.bind(this);
            initToolbar();
            baseBundle = getIntent().getBundleExtra("bundle");
            initView();
            BindEvent();
            dialogProgress = new DialogProgress(this, R.style.DialogTheme);
            inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadProcess = new DialogProgress(this, R.style.DialogTheme);

    }

    protected Bundle baseBundle;

    public Toolbar getToolbar() {
        return toolbar;
    }


    protected abstract int layoutId();

    public abstract void initView();

    public abstract void BindEvent();

    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        if (toolbar != null) {
//                toolbar.setNavigationIcon(R.drawable.arrow_left);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    /**
     * 设置activity的标题
     */
    protected void setATitle(String title) {
        if (toolbar != null) {
            toolbar.setTitle(title);
        }
    }

    protected void setATitle(int resId) {
        String title = getResources().getString(resId);
        if (toolbar != null) {
            toolbar.setTitle(title);
        }
    }

    /**
     * fragment切换
     *
     * @param from
     * @param to
     * @param fragmentManager
     * @param layoutId
     */
    protected static void switchFragment(Fragment from, Fragment to, FragmentManager fragmentManager, int layoutId) {
        if (fragmentManager == null || layoutId <= 0 || from == to) {
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (to != null) {
            if (from != null) {
                // fragmentManager.popBackStack();
                transaction.hide(from);
            }
            if (!to.isAdded()) {
                // transaction.addToBackStack(null);
                transaction.add(layoutId, to, to.getClass().getSimpleName());
            } else {
                transaction.show(to);
            }
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 创建fragment 解决fragment重爹问题
     *
     * @param savedInstanceState
     * @param fragmentManager
     * @param clazz
     * @param args
     * @return
     */
    public static Fragment newFragment(Context context, Bundle savedInstanceState, FragmentManager fragmentManager, Class clazz, Bundle args) {
        if (savedInstanceState != null) {
            Fragment fragment = fragmentManager.findFragmentByTag(clazz.getSimpleName());
            if (fragment == null) {
                return reflectNewFragment(clazz, context, args);
            }
        }
        return reflectNewFragment(clazz, context, args);

    }

    public static Fragment reflectNewFragment(Class clazz, Context context, Bundle args) {
        try {
            Method m = clazz.getDeclaredMethod("newInstance", new Class[]{Context.class, Bundle.class});
            if (!m.isAccessible()) {
                m.setAccessible(true);
            }
            Object object = m.invoke(clazz, context, args);
            if (object == null) {
                throw new RuntimeException("fragment has newInstance(bunld) method");
            }
            return (Fragment) object;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * hide
     */
    protected void hideKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public void goToActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    public void goToActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }


    public void goToActivityWithResult(Class clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra("bundle", bundle);
        startActivityForResult(intent, requestCode);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        OkGo.getInstance().cancelTag(this);
    }


    protected void transparentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //兼容5.0及以上支持全透明
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void transparentNavigationBar() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }
}
