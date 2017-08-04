package com.shiqkuangsan.mycustomviews.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.adapter.NormalRecyclerAdapter;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by shiqkuangsan on 2017/8/4. <p>
 * ClassName: NormalSimpleRecyclerFragment <p>
 * Author: shiqkuangsan <p>
 * Description: 包含一个简单RecyclerView的fragment
 */
public class NormalSimpleRecyclerFragment extends Fragment {

    RecyclerView recyclerView;

    public static NormalSimpleRecyclerFragment newInstance() {
        Bundle args = new Bundle();
        NormalSimpleRecyclerFragment fragment = new NormalSimpleRecyclerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_normal_recycler, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_simple_normal);
        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        NormalRecyclerAdapter adapter = new NormalRecyclerAdapter(getActivity());
        recyclerView.setAdapter(adapter);
    }
}
