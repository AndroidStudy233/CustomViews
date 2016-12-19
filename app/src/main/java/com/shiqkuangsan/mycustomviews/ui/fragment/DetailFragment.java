package com.shiqkuangsan.mycustomviews.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shiqkuangsan.mycustomviews.R;


/**
 * Created by shiqkuangsan on 2016/9/28.
 *
 * @author shiqkaungsan
 * @summary 就是展示一个TextView而已
 */
public class DetailFragment extends Fragment {

    public static DetailFragment newInstance(String info) {
        Bundle args = new Bundle();
        DetailFragment fragment = new DetailFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emma_detail, null);
        TextView tvInfo = (TextView) view.findViewById(R.id.tv_Info);
        tvInfo.setText(getArguments().getString("info"));
//        tvInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Snackbar.make(v,"hello",Snackbar.LENGTH_SHORT).show();
//            }
//        });
        return view;
    }
}
