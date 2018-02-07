package com.shiqkuangsan.mycustomviews.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.utils.ChoosePicUtil;
import com.shiqkuangsan.mycustomviews.utils.MyLogUtil;

/**
 * <p>创建人：余志伟</p>
 * <p>创建时间：2016-03-15 16:35</p>
 * <p>修改人：       </p>
 * <p>修改时间：   </p>
 * <p>修改备注：   </p>
 *
 * @version V3.1
 */
public class EasyPagerFragment extends Fragment {

    private View view;

    public static EasyPagerFragment getInstance(int position) {
        EasyPagerFragment fragment = new EasyPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int pisotion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_viewpager_indicator, container, false);
        TextView textView = (TextView) view.findViewById(R.id.tv_fragment);
        pisotion = getArguments().getInt("position");
        textView.setText("这是第" + pisotion + "个fragment");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChoosePicUtil.startActivityFor(ChoosePicUtil.MATCHING_CODE_CAMERA, getActivity());
            }
        });
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MyLogUtil.debug(requestCode + "---" + resultCode + "---" + data);
    }
}
