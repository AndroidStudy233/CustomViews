package qwy.anenda.com.baselib.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import qwy.anenda.com.baselib.R;
import qwy.anenda.com.baselib.base.BaseFragment;


@SuppressLint("ValidFragment")
public class IndexFragment extends BaseFragment {

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
        return mLayoutInflater.inflate(R.layout.fragment_index, container, false);
    }



    @Override
    public void onResume() {
        super.onResume();
    }
}
