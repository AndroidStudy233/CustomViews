package qwy.anenda.com.baselib.recycleradaper;

import android.content.Context;
import android.view.View;

import java.util.List;


public abstract class RecyclerQuickAdapter<T> extends BaseRecylerAdapter<T, RecylerViewHelper> {

    public RecyclerQuickAdapter() {

    }

    public RecyclerQuickAdapter(Context context, /*BaseRecylerViewHolder recylerViewHolderHelper,*/int layoutId, List<T> datas) {
        super(context, /*recylerViewHolderHelper*/layoutId, datas);
    }

    public RecyclerQuickAdapter(Context context, /*BaseRecylerViewHolder recylerViewHolderHelper,*/int layoutId, List<T> datas, boolean hasEmptyLayout) {
        super(context, /*recylerViewHolderHelper*/layoutId, datas, hasEmptyLayout);
    }


    public RecyclerQuickAdapter(Context context, List<T> datas, BaseRecyclerMultitemTypeSupport baseRecyclerMultitemTypeSupport) {
        super(context, datas, baseRecyclerMultitemTypeSupport);
    }


    @Override
    protected RecylerViewHelper getAdapterHelper(int position, View itemView) {
        return RecylerViewHelper.get(context, itemView, position);
    }
}
