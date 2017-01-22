package com.shiqkuangsan.mycustomviews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.bean.ImgAndText;
import com.shiqkuangsan.mycustomviews.constant.Constant;
import com.shiqkuangsan.mycustomviews.utils.SimplexUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiqkuangsan on 2016/10/13.
 *
 * @author shiqkuangsan
 * @summary 自定义RecyclerView的适配器, 需要继承自RecyclerView下的Adapter,
 * 泛型ViewHolder也要继承自RecyclerView下的ViewHolder
 */

public class SimpleRecyclerAdapter extends RecyclerView.Adapter<SimpleRecyclerAdapter.SimpleRecyclerViewHolder> {

    private OnItemClickListener listener;
    private Context context;
    private List<ImgAndText> list = new ArrayList<>();

    public SimpleRecyclerAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;

        initData();
    }

    private void initData() {
        for (int i = 0; i < 30; i++) {
            ImgAndText bean = new ImgAndText();
            bean.imgName = "测试图片 " + i;
            switch (i % 6) {
                case 0:
                    bean.imgUrl = Constant.imgurl_800x470_1;
                    break;
                case 1:
                    bean.imgUrl = Constant.imgurl_800x470_2;
                    break;
                case 2:
                    bean.imgUrl = Constant.imgurl_1366x768_1;
                    break;
                case 3:
                    bean.imgUrl = Constant.imgurl_1366x768_2;
                    break;
                case 4:
                    bean.imgUrl = Constant.imgurl_1920x1080_1;
                    break;
                case 5:
                    bean.imgUrl = Constant.imgurl_1920x1080_2;
                    break;
            }

            list.add(bean);
        }
    }

    public interface OnItemClickListener {
        void onClick(int position, ImgAndText bean);
    }

    /**
     * 通过notifyItemInserted()添加一条数据
     *
     * @param imgName  图片描述
     * @param imgUrl   图片资源地址
     * @param position 位置
     */
    public void addItem(String imgName, String imgUrl, int position) {
        ImgAndText bean = new ImgAndText();
        bean.imgUrl = imgUrl;
        bean.imgName = imgName;
        list.add(position, bean);
        notifyItemInserted(position);
    }

    /**
     * 通过notifyItemRemoved()移除一条数据
     *
     * @param position 位置
     */
    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
//        notifyDataSetChanged();
    }

    /**
     * 根据当前位置获取到bean
     *
     * @param position 位置
     * @return 以数组形式返回, 0是name, 1是链接
     */
    public ImgAndText getCurrentItem(int position) {
        return list.get(position);
    }


    // 绘制View
    @Override
    public SimpleRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View custom = View.inflate(context, R.layout.item_recycler_main_test, null);
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_main_test, parent, false);
        // 注意这里返回的ViewHolder
        return new SimpleRecyclerViewHolder(view);
    }


    // 设置数据
    @Override
    public void onBindViewHolder(final SimpleRecyclerViewHolder holder, final int position) {

        final ImgAndText bean = list.get(position);

        SimplexUtil.loadImage(holder.iv_card_img, bean.imgUrl, SimplexUtil.getSimpleImageOptions(8), null);
        holder.tv_card_text.setText(bean.imgName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onClick(holder.getAdapterPosition(), bean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SimpleRecyclerViewHolder extends RecyclerView.ViewHolder {
        final ImageView iv_card_img;
        final TextView tv_card_text;
        View itemView;

        SimpleRecyclerViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            iv_card_img = (ImageView) itemView.findViewById(R.id.iv_recycler_cardimg);
            tv_card_text = (TextView) itemView.findViewById(R.id.tv_recycler_cardtext);
        }
    }
}
