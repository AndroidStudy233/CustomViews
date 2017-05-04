package com.shiqkuangsan.mycustomviews.ui.custom;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by shiqkuangsan on 2017/5/4.
 * <p>
 * ClassName: OnRecyclerItemTouchCallBack
 * Author: shiqkuangsan
 * Description: RecyclerView的条目拖拽回调
 */
public class OnRecyclerItemTouchCallBack extends ItemTouchHelper.Callback {

    private OnItemDragListener listener;

    public OnRecyclerItemTouchCallBack(@NonNull OnItemDragListener listener) {
        this.listener = listener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        // 如果是ListView样式的RecyclerView
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            // 设置拖拽方向为上下
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            // 设置侧滑方向为从左到右和从右到左都可以
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            // 将方向参数设置进去
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {//如果是GridView样式的RecyclerView
            // 设置拖拽方向为上下左右
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            // 不支持侧滑
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        // 如果两个item不是一个类型的，我们让他不可以拖拽
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        // 回调adapter中的onMove方法
        if (listener != null)
            listener.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        // 回调adapter中的onSwipe方法
        if (listener != null)
            listener.onSwipe(viewHolder.getAdapterPosition());
    }

    /**
     * 回调, 用来传递给adapter实现你需要的逻辑
     */
    public interface OnItemDragListener {
        /**
         * 位置改变时
         *
         * @param fromPosition 从哪
         * @param toPosition   到哪
         */
        boolean onMove(int fromPosition, int toPosition);

        /**
         * 移除时
         *
         * @param position 之前位置
         */
        void onSwipe(int position);
    }
}
