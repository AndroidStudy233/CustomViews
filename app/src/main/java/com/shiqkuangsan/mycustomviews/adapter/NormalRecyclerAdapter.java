package com.shiqkuangsan.mycustomviews.adapter;

import android.content.Context;
import android.provider.Settings;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by shiqkuangsan on 2017/5/10.
 * <p>
 * ClassName: NormalRecyclerAdapter
 * Author: shiqkuangsan
 * Description: 简单的适配器
 */
public class NormalRecyclerAdapter extends RecyclerView.Adapter<NormalRecyclerAdapter.NormalViewHolder> {

    private Context context;

    public NormalRecyclerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, null);
        return new NormalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NormalViewHolder holder, int position) {
        TextView tv_text = (TextView) holder.itemView.findViewById(android.R.id.text1);
        tv_text.setText("条目 ".concat(String.valueOf(position)));
    }

    @Override
    public int getItemCount() {
        return 26;
    }

    class NormalViewHolder extends RecyclerView.ViewHolder {

        public NormalViewHolder(View itemView) {
            super(itemView);
        }
    }
}
