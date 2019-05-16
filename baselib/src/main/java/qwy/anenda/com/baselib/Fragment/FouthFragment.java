package qwy.anenda.com.baselib.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import qwy.anenda.com.baselib.R;
import qwy.anenda.com.baselib.base.BaseFragment;


@SuppressLint("ValidFragment")
public class FouthFragment extends BaseFragment {

    @Override
    protected void init() {

    }

    @Override
    protected void initView() {
        setATitle("首页");
//        toolbar.setNavigationIcon(null);

    }

    @Override
    protected void bindEvent() {
    }

    @Override
    protected void fillData() {

    }

    @Override
    protected View onLayoutView(Bundle savedInstanceState, LayoutInflater inflater, ViewGroup
            container) {
        return mLayoutInflater.inflate(R.layout.fragment_fourth, container, false);
    }



    @Override
    public void onResume() {
        super.onResume();
    }
}
