package com.shiqkuangsan.mycustomviews.utils.dialogList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.shiqkuangsan.mycustomviews.R;

import java.util.ArrayList;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/13
 * 内容：单项选择器的adapter
 * 最后修改：2018.4.29  修改参数类型，可以只传递ArrayList<String>进来,单独的String集合不用再封装Bean了.
 */

public class DialogListAdapter<T> extends RecyclerView.Adapter<DialogListAdapter.DialogListViewHolder> {

    private final Context mContext;
    private ArrayList<T> mList = new ArrayList<>();
    private int type = MiDialogList.MILIST_RETURN_SINGLE;

    public DialogListAdapter(Context context) {
        mContext = context;
    }

    /**
     * 设置数据源
     *
     * @param mDataList 允许参数类型  String类型/实现了MiListInterface接口的bean对象
     */
    public void setList(ArrayList<T> mDataList, int returnType) {
        mList.clear();
        mList.addAll(mDataList);
        type = returnType;
        notifyDataSetChanged();
    }

    private OnItemClickListener mClick;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        mClick = mOnItemClickListener;
    }

    @Override
    public DialogListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DialogListAdapter.DialogListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_dialog_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final DialogListAdapter.DialogListViewHolder holder, final int position) {
        if (mList.get(position) instanceof MiListInterface) {
            holder.contentTv.setText(((MiListInterface) mList.get(position)).getShowData() + "");
        } else if (mList.get(position) instanceof String) {
            holder.contentTv.setText((String) mList.get(position) + "");
        } else {
            new RuntimeException("listDate must String or implements MiListInterface !");
        }
        //多选；文字在左边，上下居中   单选；文字居中
        holder.contentTv.setGravity(type == MiDialogList.MILIST_RETURN_MULTIPLE ? Gravity.CENTER_VERTICAL : Gravity.CENTER);
        holder.flagImg.setVisibility(type == MiDialogList.MILIST_RETURN_MULTIPLE ? View.INVISIBLE : View.GONE);

        if (mClick != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClick.onItemClick(v, position);
                    if (type == MiDialogList.MILIST_RETURN_MULTIPLE) {
                        holder.flagImg.setVisibility(holder.flagImg.getVisibility() == View.INVISIBLE ? View.VISIBLE : View.INVISIBLE);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class DialogListViewHolder extends RecyclerView.ViewHolder {
        TextView contentTv;
        ImageView flagImg;

        public DialogListViewHolder(View itemView) {
            super(itemView);
            contentTv = (TextView) itemView.findViewById(R.id.item_dialoglist_text);
            flagImg = (ImageView) itemView.findViewById(R.id.item_item_dialoglist_img);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
