package com.shiqkuangsan.mycustomviews.ui.activity.vlayout.subadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.utils.MyLogUtil;

/**
 * Created by shiqkuangsan on 2017/5/9.
 * <p>
 * ClassName: SimpleVLayoutAdapter
 * Author: shiqkuangsan
 * Description: 简单的VLayout子adapter
 */
public class SimpleVLayoutAdapter extends DelegateAdapter.Adapter<SimpleVLayoutAdapter.VLayoutViewHolder> {

    private Context context;
    private LayoutHelper layoutHelper;
    private int count = 0;
    private VirtualLayoutManager.LayoutParams params;

    public SimpleVLayoutAdapter(Context context, LayoutHelper layoutHelper, int count) {
        // 不给高度默认150px
        this(context, layoutHelper, count,
                new VirtualLayoutManager.LayoutParams(VirtualLayoutManager.LayoutParams.MATCH_PARENT, 150));
    }

    public SimpleVLayoutAdapter(Context context, LayoutHelper layoutHelper,
                                int count, @NonNull VirtualLayoutManager.LayoutParams params) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.count = count;
        this.params = params;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public VLayoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_simple_vlayout, null);
        return new VLayoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VLayoutViewHolder holder, int position) {
        holder.itemView.setLayoutParams(new VirtualLayoutManager.LayoutParams(params));
    }

    @Override
    protected void onBindViewHolderWithOffset(VLayoutViewHolder holder, int position, int offsetTotal) {
        TextView tv_text = (TextView) holder.itemView.findViewById(R.id.tv_simple_vlayout_title);
        tv_text.setText(String.valueOf(offsetTotal));
    }

    @Override
    public int getItemCount() {
        return count;
    }

    protected class VLayoutViewHolder extends RecyclerView.ViewHolder {
        VLayoutViewHolder(View itemView) {
            super(itemView);
        }
    }
}
