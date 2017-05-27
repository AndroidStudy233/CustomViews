package com.shiqkuangsan.mycustomviews.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shiqkuangsan.mycustomviews.R;

/**
 * <p>创建人：余志伟</p>
 * <p>创建时间：2016-03-15 16:35</p>
 * <p>修改人：       </p>
 * <p>修改时间：   </p>
 * <p>修改备注：   </p>
 *
 * @version V3.1
 */
public class ViewPagerFragment extends Fragment {

   private View view;
    private Bundle bundle;
    public ViewPagerFragment() {
        this.bundle = bundle;
    }
    public ViewPagerFragment(Bundle bundle) {
        this.bundle = bundle;
    }
    private int pisotion;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_viewpager_indicator,container,false);
        TextView textView = (TextView) view.findViewById(R.id.tv_fragment);
        pisotion = bundle.getInt("position");
        textView.setText("这是第"+pisotion+"个fragment");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("----onResume----", "第" + pisotion);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("----onPause----", "第" + pisotion);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("----onDestroy----", "第" + pisotion);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }
}
