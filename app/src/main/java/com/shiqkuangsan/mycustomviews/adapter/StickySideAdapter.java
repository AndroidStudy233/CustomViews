package com.shiqkuangsan.mycustomviews.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.adapter.itemdecoration.StickyHeader;
import com.shiqkuangsan.mycustomviews.ui.custom.swipe1.SwipeMenuView;
import com.shiqkuangsan.mycustomviews.utils.MyLogUtil;

/**
 * Created by shiqkuangsan on 2017/5/12.
 * <p>
 * ClassName: StickySideAdapter
 * Author: shiqkuangsan
 * Description: 侧滑删除界面的适配器
 */
public class StickySideAdapter extends RecyclerView.Adapter<StickySideAdapter.ItemViewHolder>
        implements StickyHeader<StickySideAdapter.HeaderViewHolder> {

    private Context context;
    private OnSwipeMenuClick listener;

    public StickySideAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View child = inflater.inflate(R.layout.item_sideslip_child, parent, false);
        return new ItemViewHolder(child);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        // 关掉IOS阻塞式交互效果 并依次打开左滑右滑
        ((SwipeMenuView) holder.itemView).setIos(false).setLeftSwipe(position % 2 == 0);

        // 这里拿到的position仅仅就是按照item个数, 不含header
        TextView tv_text = (TextView) holder.itemView.findViewById(R.id.tv_sideslip_item);
        String text = "RecyclerView item ".concat(String.valueOf(position));
        tv_text.setText(position % 2 == 0 ? "左滑".concat(text) : "右滑".concat(text));

        Button btn_top = (Button) holder.itemView.findViewById(R.id.btn_sideslip_top);
        Button btn_delete = (Button) holder.itemView.findViewById(R.id.btn_sideslip_delete);
        btn_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onClickTop(holder, position);
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onClickDelete(holder, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 60;
    }

    @Override
    public long getHeaderId(int position) {
        return (long) position / 6;
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View header = LayoutInflater.from(context).inflate(R.layout.item_sideslip_group, parent, false);
        return new HeaderViewHolder(header);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder holder, int position) {
        // 这里拿到的position是包含item之后的.
        TextView tv_text = (TextView) holder.itemView.findViewById(R.id.tv_sideslip_header);
        tv_text.setText("StickHeader ".concat(String.valueOf(position / 6)));
    }

    public void setOnSwipeMenuClickListener(@NonNull OnSwipeMenuClick listener) {
        this.listener = listener;
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnSwipeMenuClick {
        void onClickTop(RecyclerView.ViewHolder holder, int position);

        void onClickDelete(RecyclerView.ViewHolder holder, int position);
    }
}
