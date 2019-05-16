package qwy.anenda.com.baselib.base;
 /* 
 -----------------------------------------------------------------
 * Author: yzw
 * Create: 2019/2/25
 * Descride:
 * 
 *-----------------------------------------------------------------
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import qwy.anenda.com.baselib.R;
import qwy.anenda.com.baselib.widget.DialogProgress;


public abstract class BaseFragment extends Fragment {


    protected Bundle bundle;
    protected Context mContext;
    protected LayoutInflater mLayoutInflater;
    protected Toolbar toolbar;
    protected DialogProgress dialogProgress;
    protected View emptyView;
    protected View mRootView;

    public BaseFragment() {

    }

    protected BaseFragment(Context context, Bundle bundle) {
        this.mContext = context;
        this.bundle = bundle;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mLayoutInflater = inflater;
        mRootView = onLayoutView(savedInstanceState, inflater, container);
//        ButterKnife.bind(this, mRootView);
        emptyView =  mRootView.findViewById(R.id.rl_empty);
        initToolbar(mRootView);
//        userRolyType =  PreferenceUtil.readInt( Constant.USER_ROLE);
        init();
        initView();
        bindEvent();
        fillData();
        dialogProgress = new DialogProgress(mContext, R.style.DialogTheme);
        return mRootView;
    }

    public void initToolbar(View view) {
        if (view != null) {
            toolbar = (Toolbar) view.findViewById(R.id.tool_bar);
            if (toolbar != null) {
//                toolbar.setNavigationIcon(R.drawable.arrow_left);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().finish();
                    }
                });
            }
        }
    }

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
     */
    protected abstract void init();

    /**
     * 控件初始化化
     */
    protected abstract void initView();

    /**
     * 绑定事件
     */
    protected abstract void bindEvent();

    /**
     * 加载数据
     */
    protected abstract void fillData();


    /**
     * 添加布局
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    protected abstract View onLayoutView(Bundle savedInstanceState, LayoutInflater inflater, ViewGroup container);

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
                transaction.hide(from);
            }
            if (!to.isAdded()) {
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
    protected static Fragment newFragment(Context context, Bundle savedInstanceState, FragmentManager fragmentManager, Class clazz, Bundle args) {
        if (savedInstanceState != null) {
            Fragment fragment = fragmentManager.findFragmentByTag(clazz.getSimpleName());
            if (fragment == null) {
                return reflectNewFragment(clazz, context, args);
            }
        }
        return reflectNewFragment(clazz, context, args);

    }

    protected static Fragment reflectNewFragment(Class clazz, Context context, Bundle args) {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        //占位菜单
//        inflater.inflate(R.menu.menu_placeholder, menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_empty) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    public void goToActivity(Class clazz) {
        Intent intent = new Intent(mContext, clazz);
        startActivity(intent);
    }

    public void goToActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(mContext, clazz);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        OkGo.getInstance().cancelTag(this);
    }
}
