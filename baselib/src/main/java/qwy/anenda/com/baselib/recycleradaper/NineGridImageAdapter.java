package qwy.anenda.com.baselib.recycleradaper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import qwy.anenda.com.baselib.R;
import qwy.anenda.com.baselib.base.ImagePreviewActivity;
import qwy.anenda.com.baselib.utils.DensityUtil;
import qwy.anenda.com.baselib.utils.GlideUtils;


public class NineGridImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public enum ShowMode {
        //简单模式，仅仅显示图片
        simple,
        //标准模式模式，显示图片和删除按钮
        standard,
        //复杂模式模式，显示图片和删除按钮，添加图片按钮
        complex
    }

    public static final int NORMAL_ITEM = 0x0001;
//    public static final int LOADMORE_ITEM = 0x0002;
    public static final int ADD_ITEM = 0x0002;
    public static final int DEFAULT_MAX = 9;

    private Context context;
    private List<String> datas;
    private int itemHeight;
    private ShowMode showMode = ShowMode.standard;
    private int maxCount = DEFAULT_MAX;
    private boolean delBtnCanClick = true;//默认可以删除


    public NineGridImageAdapter(Context context, List<String> datas, ShowMode showMode, int ImageHeight) {
        this.datas = datas == null ? new ArrayList<String>() : datas;
        this.context = context;
        itemHeight = DensityUtil.dip2px(context, ImageHeight);
        this.showMode = showMode;
    }

    public NineGridImageAdapter(Context context, List<String> datas, ShowMode showMode) {
        this.datas = datas == null ? new ArrayList<String>() : datas;
        this.context = context;
        itemHeight = DensityUtil.dip2px(context, 110);
        this.showMode = showMode;
    }

    public NineGridImageAdapter(Context context, List<String> datas) {
        this.datas = datas == null ? new ArrayList<String>() : datas;
        this.context = context;
        itemHeight = DensityUtil.dip2px(context, 110);
    }

    public NineGridImageAdapter(Context context, List<String> datas, boolean canClick) {
        this.datas = datas == null ? new ArrayList<String>() : datas;
        this.context = context;
        itemHeight = DensityUtil.dip2px(context, 110);
        this.delBtnCanClick = canClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ADD_ITEM) {
            return new AddHolder(LayoutInflater.from(context).inflate(R.layout.view_nine_loadmore_pic_item, parent, false));
        } else {
            return new ImageHolder(LayoutInflater.from(context).inflate(R.layout.view_nine_pic_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof AddHolder) {
            AddHolder loadMoreHolder = (AddHolder) holder;
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) loadMoreHolder.flItem.getLayoutParams();
            lp.height = itemHeight;
            lp.width = itemHeight;
            loadMoreHolder.flItem.setLayoutParams(lp);
            loadMoreHolder.flItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onAddListener != null) {
                        onAddListener.onAdd();
                    }
                }
            });
        } else if (holder instanceof ImageHolder) {
            final String s = datas.get(position);
            ImageHolder imageHolder = (ImageHolder) holder;
            if (showMode == ShowMode.simple) {
                imageHolder.deleteView.setVisibility(View.GONE);
            } else {
                imageHolder.deleteView.setVisibility(View.VISIBLE);
            }
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) imageHolder.flItem.getLayoutParams();
            lp.height = itemHeight;
            lp.width = itemHeight;
            imageHolder.flItem.setLayoutParams(lp);
//            Log.e("NineGridImageAdapter", "onBindViewHolder  " + s);
            GlideUtils.loadImg(context,s,imageHolder.image);
            imageHolder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, (String) v.getTag(), position);
                    }
                    previewImage(v, position);

                }
            });
            imageHolder.deleteView.setTag(s);
            imageHolder.deleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (delBtnCanClick) {
                        deleteItem((String) v.getTag());
                        if (onItemDeleteListener != null) {
                            onItemDeleteListener.onDeleted(v, (String) v.getTag(), position);
                        }
                    }
                }
            });
        }
    }

    private void previewImage(View view, int position) {
        int[] screenLocation = new int[2];
        view.getLocationOnScreen(screenLocation);
        Intent intent = new Intent(context, ImagePreviewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(ImagePreviewActivity.SCREEN_LOCATION0, screenLocation[0]);
        bundle.putInt(ImagePreviewActivity.SCREEN_LOCATION1, screenLocation[1]);
        bundle.putInt(ImagePreviewActivity.THUMBNAIL_WIDTH, view.getWidth());
        bundle.putInt(ImagePreviewActivity.THUMBNAIL_HEIGHT, view.getHeight());
        bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) datas);
        bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, position);
        intent.putExtras(bundle);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(0, 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (showMode == ShowMode.complex) {
            if (datas.size() <= 0) {
                return ADD_ITEM;
            } else {
                if (position < datas.size()) {
                    return NORMAL_ITEM;
                }
                return ADD_ITEM;
            }
        } else {
            return NORMAL_ITEM;
        }

    }


    @Override
    public int getItemCount() {
        if (showMode == ShowMode.complex) {
            if (datas.size() <= 0) {
                return 1;
            } else if (datas.size() < maxCount) {
                return datas.size() + 1;
            }
            return datas.size();
        }
        return datas.size();
    }

    public void setShowMode(ShowMode showMode) {
        this.showMode = showMode;

    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
    }

    public void clear() {
        datas.clear();
        notifyDataSetChanged();
    }

    public void replaceAll(List<String> elems) {
        datas.clear();
        if (elems != null) {
            if (showMode == ShowMode.complex) {
                if (elems.size() + datas.size() > maxCount) {
                    datas.addAll(elems.subList(0, maxCount - datas.size() - 1));
                } else {
                    datas.addAll(elems);
                }
            } else {
                datas.addAll(elems);
            }
        }
        notifyDataSetChanged();
    }

    public void addAll(List<String> elems) {
        if (elems != null) {
            if (showMode == ShowMode.complex) {
                if (elems.size() + datas.size() > maxCount) {
                    datas.addAll(elems.subList(0, maxCount - datas.size() - 1));
                } else {
                    datas.addAll(elems);
                }
            } else {
                datas.addAll(elems);
            }
            notifyDataSetChanged();
        }
    }

    public void add(String elem) {
        if (elem != null) {
            if (showMode == ShowMode.complex) {
                if (1 + datas.size() > maxCount) {
                } else {
                    datas.add(elem);
                    notifyDataSetChanged();
                }
            } else {
                datas.add(elem);
                notifyDataSetChanged();
            }
        }
    }

    public List<String> getData() {
        return datas;
    }

    public void deleteItem(String s) {
        int index = datas.indexOf(s);
        if (index >= 0 && index <= datas.size() - 1) {
            datas.remove(index);
            notifyItemRemoved(index);
        }
    }

    class ImageHolder extends RecyclerView.ViewHolder {
        public FrameLayout flItem;
        public ImageView image;
        public ImageView deleteView;

        public ImageHolder(View itemView) {
            super(itemView);
            flItem = (FrameLayout) itemView.findViewById(R.id.fl_item);
            image = (ImageView) itemView.findViewById(R.id.iv);
            deleteView = (ImageView) itemView.findViewById(R.id.iv_delete);

        }
    }

    class AddHolder extends RecyclerView.ViewHolder {
        public FrameLayout flItem;
        public ImageView imageView;

        public AddHolder(View itemView) {
            super(itemView);
            flItem = (FrameLayout) itemView.findViewById(R.id.fl_item);
            imageView = itemView.findViewById(R.id.iv_more);

        }
    }

    private OnAddListener onAddListener;
    private OnItemClickListener onItemClickListener;
    private OnItemDeleteListener onItemDeleteListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemDeleteListener(OnItemDeleteListener onItemDeleteListener) {
        this.onItemDeleteListener = onItemDeleteListener;
    }

    public void setOnAddListener(OnAddListener onAddListener) {
        this.onAddListener = onAddListener;
    }

    public interface OnAddListener {
        void onAdd();
    }

    public interface OnItemDeleteListener {
        void onDeleted(View v, String s, int position);
    }

    public interface OnItemClickListener {
        void onItemClick(View v, String s, int position);
    }
}
