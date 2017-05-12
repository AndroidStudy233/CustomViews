package com.shiqkuangsan.mycustomviews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.bean.ImgAndText;
import com.shiqkuangsan.mycustomviews.constant.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by shiqkuangsan on 2017/5/12.
 * <p>
 * ClassName: SlideDeleteAdapter
 * Author: shiqkuangsan
 * Description: 侧滑删除界面的适配器
 */
public class SlideDeleteAdapter extends RecyclerView.Adapter {

    private Map<Integer, List<ImgAndText>> datas;
    private Context context;

    public SlideDeleteAdapter(Context context) {
        this.context = context;
        initData();
    }

    private void initData() {
        int total = 0;
        for (int x = 0; x < 4; x++) {
            List<ImgAndText> group = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                ImgAndText bean = new ImgAndText();
                bean.imgName = "测试图片 " + total;
                switch (x) {
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
                }

                group.add(bean);
                total++;
            }

            datas.put(x, group);
        }
    }

    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        switch (viewType) {
            case ViewType.TYPE_HEADER:
                View group = inflater.inflate(R.layout.item_sideslip_group, parent);
                return new GroupViewHolder(group);
            case ViewType.TYPE_CONTENT:
                View child = inflater.inflate(R.layout.item_sideslip_child, parent);
                return new ChildViewHolder(child);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GroupViewHolder) {

        } else if (holder instanceof ChildViewHolder) {

        } else return;
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position % 6 == 1 ? ViewType.TYPE_HEADER : ViewType.TYPE_CONTENT;
    }

    class GroupViewHolder extends RecyclerView.ViewHolder {

        public GroupViewHolder(View itemView) {
            super(itemView);
        }
    }

    class ChildViewHolder extends RecyclerView.ViewHolder {

        public ChildViewHolder(View itemView) {
            super(itemView);
        }
    }

    interface ViewType {
        int TYPE_HEADER = 0;
        int TYPE_CONTENT = 1;
    }
}
