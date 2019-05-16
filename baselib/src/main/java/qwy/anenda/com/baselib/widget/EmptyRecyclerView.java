package qwy.anenda.com.baselib.widget;
 /* 
 -----------------------------------------------------------------
 * Author: yzw
 * Create: 2019/5/9
 * Descride:
 * 
 *-----------------------------------------------------------------
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Adapter;

public class EmptyRecyclerView extends RecyclerView {
    private View emptyView;
    private final AdapterDataObserver observer = new AdapterDataObserver() {
        public void onChanged() {
            EmptyRecyclerView.this.checkIfEmpty();
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            EmptyRecyclerView.this.checkIfEmpty();
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
            EmptyRecyclerView.this.checkIfEmpty();
        }
    };

    public EmptyRecyclerView(Context context) {
        super(context);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    void checkIfEmpty() {
        if (this.getAdapter() != null) {
            try {
                boolean emptyViewVisible = this.getAdapter().getItemCount() == 0;
                if (this.emptyView != null) {
                    this.emptyView.setVisibility(emptyViewVisible ?  View.VISIBLE :  View.GONE);
                    this.setVisibility(emptyViewVisible ? View.GONE :  View.VISIBLE);
                }
            } catch (Exception var2) {
                var2.printStackTrace();
            }
        }

    }

    public void setAdapter(Adapter adapter) {
        Adapter oldAdapter = this.getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(this.observer);
        }

        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(this.observer);
        }

        this.checkIfEmpty();
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        this.checkIfEmpty();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
