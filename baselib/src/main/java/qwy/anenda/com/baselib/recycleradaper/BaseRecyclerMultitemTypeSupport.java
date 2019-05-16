package qwy.anenda.com.baselib.recycleradaper;


public interface BaseRecyclerMultitemTypeSupport<T> {

    //BaseRecylerViewHolder getViewHolderHelper(int layoutId);

    int getItemViewType(T t, int positon);

    int getLayoutId(int type);

    /**
     * 是否需要处理itemview
     * @param t
     * @param position
     * @return
     */
    boolean isConvert(T t, int position);

}
