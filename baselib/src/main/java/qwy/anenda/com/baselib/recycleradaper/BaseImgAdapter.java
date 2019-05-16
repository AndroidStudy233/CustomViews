package qwy.anenda.com.baselib.recycleradaper;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @param <T>
 */
public abstract class BaseImgAdapter<T> extends BaseAdapter {

    private static final int DEFAULT_MAX = 9;
    public static final int NORMAL_TYPE = 1001;
    public static final int WRAP_TYPE = 1003;
    public static final int ADD_TYPE = 1002;


    protected List<T> mDatas = new ArrayList<>();
    protected int maxNum;
    private LayoutInflater mLayoutInflater;
    private int normalViewLayout;
    private int addImageLayout;
    private boolean isWrap;
    private boolean isCanAdd;
    private boolean isCanDel;
    protected Context mContext;
    protected FragmentManager fragmentManager;


    //TODO 添加image大小自适应

    public BaseImgAdapter(FragmentManager fragmentManager, Context context, List<T> datas, int normalViewLayout, int addImageLayout) {
        this(fragmentManager, true, true, context, datas, DEFAULT_MAX, normalViewLayout, addImageLayout, false);
    }

    public BaseImgAdapter(FragmentManager fragmentManager, Context context, List<T> datas, int maxNum, int normalViewLayout, int addImageLayout) {
        this(fragmentManager, true, true, context, datas, maxNum, normalViewLayout, addImageLayout, false);
    }

    public BaseImgAdapter(FragmentManager fragmentManager, boolean isCanAdd, Context context, List<T> datas, int maxNum, int normalViewLayout, int addImageLayout) {
        this(fragmentManager, isCanAdd, true, context, datas, maxNum, normalViewLayout, addImageLayout, false);
    }


    public BaseImgAdapter(FragmentManager fragmentManager, Context context, List<T> datas, int normalViewLayout, int addImageLayout, boolean isWrap) {
        this(fragmentManager, true, true, context, datas, DEFAULT_MAX, normalViewLayout, addImageLayout, isWrap);
    }

    public BaseImgAdapter(FragmentManager fragmentManager, boolean isCanAdd, Context context, List<T> datas, int normalViewLayout, int addImageLayout, boolean isWrap) {
        this(fragmentManager, isCanAdd, true, context, datas, DEFAULT_MAX, normalViewLayout, addImageLayout, isWrap);
    }

    public BaseImgAdapter(FragmentManager fragmentManager, boolean isCanAdd, Context context, List<T> datas, int normalViewLayout, int addImageLayout) {
        this(fragmentManager, isCanAdd, true, context, datas, DEFAULT_MAX, normalViewLayout, addImageLayout, false);
    }


    public BaseImgAdapter(FragmentManager fragmentManager, Context context, boolean isCanDel, List<T> datas, int normalViewLayout, int addImageLayout) {
        this(fragmentManager, true, isCanDel, context, datas, DEFAULT_MAX, normalViewLayout, addImageLayout, false);
    }

    public BaseImgAdapter(FragmentManager fragmentManager, Context context, boolean isCanDel, List<T> datas, int maxNum, int normalViewLayout, int addImageLayout) {
        this(fragmentManager, true, isCanDel, context, datas, maxNum, normalViewLayout, addImageLayout, false);
    }

    public BaseImgAdapter(FragmentManager fragmentManager, Context context, boolean isCanDel, List<T> datas, int normalViewLayout, int addImageLayout, boolean isWrap) {
        this(fragmentManager, true, isCanDel, context, datas, DEFAULT_MAX, normalViewLayout, addImageLayout, isWrap);
    }

    public BaseImgAdapter(FragmentManager fragmentManager, boolean isCanAdd, boolean isCanDel, Context context, List<T> datas, int normalViewLayout, int addImageLayout, boolean isWrap) {
        this(fragmentManager, isCanAdd, isCanDel, context, datas, DEFAULT_MAX, normalViewLayout, addImageLayout, isWrap);
    }

    public BaseImgAdapter(FragmentManager fragmentManager, boolean isCanAdd, boolean isCanDel, Context context, List<T> datas, int normalViewLayout, int addImageLayout) {
        this(fragmentManager, isCanAdd, isCanDel, context, datas, DEFAULT_MAX, normalViewLayout, addImageLayout, false);
    }

    public void setCanDel(boolean canDel) {
        isCanDel = canDel;
    }

    public BaseImgAdapter(FragmentManager fragmentManager, boolean isCanAdd, boolean isCanDel, Context context, List<T> datas, int maxNum, int normalViewLayout, int addImageLayout, boolean isWrap) {
        if (datas != null && !datas.isEmpty()) {
            mDatas.clear();
            mDatas.addAll(datas);
        }
        this.maxNum = maxNum;
        this.isCanDel = isCanDel;
        this.normalViewLayout = normalViewLayout;
        this.addImageLayout = addImageLayout;
        this.isWrap = isWrap;
        this.isCanAdd = isCanAdd;
        mLayoutInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.fragmentManager = fragmentManager;


    }

    @Override
    public int getCount() {
        if (isCanAdd) {
            if (mDatas.size() < maxNum) {
                return mDatas.size() + 1;
            }
        }
        return mDatas.size();
    }

    public int getDataSize() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public boolean isWrap() {
        return isWrap;
    }


    /**
     * 正常图片获取的布局
     *
     * @param item
     * @param normalView
     * @return
     */
    public abstract void convertNormalView(int position, T item, View normalView);

    public abstract void convertWrapView(int position, T item, View wrapView);

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int itemViewType = getViewType(position);
        View view = null;
        switch (itemViewType) {
            case NORMAL_TYPE:
                Object item = mDatas.get(position);
                if (convertView == null) {
                    view = mLayoutInflater.inflate(normalViewLayout, parent, false);
                } else {
                    int ctype = (int) convertView.getTag();
                    if (ctype == itemViewType)
                        view = convertView;
                    else
                        view = mLayoutInflater.inflate(normalViewLayout, parent, false);
                }
                convertNormalView(position, (T) item, view);
                //长按删除图片


                break;
            case ADD_TYPE:
                if (convertView == null) {
                    view = mLayoutInflater.inflate(addImageLayout, parent, false);
                } else {
                    int ctype = (int) convertView.getTag();
                    if (ctype == itemViewType)
                        view = convertView;
                    else
                        view = mLayoutInflater.inflate(addImageLayout, parent, false);
                }
                convertWrapView(position,(T)null,view);
                break;
            case WRAP_TYPE:
                break;

        }

        view.setTag(itemViewType);

        return view;

    }

    @Override
    public int getViewTypeCount() {
        if (isWrap)
            return 1;
        else
            return 2;
    }

    public int getViewType(int position) {
        if (position == 0 && isWrap && mDatas.size() == 1) {
            return WRAP_TYPE;
        }
        if (position < mDatas.size()) {
            return NORMAL_TYPE;
        }
        return ADD_TYPE;
    }

    public void add(T elem) {
        if (elem != null && mDatas.size() < maxNum) {
            this.mDatas.add(elem);
            this.notifyDataSetChanged();
        }

    }

    public void addAll(List<T> elem) {
        if (elem != null && !elem.isEmpty()) {
            if (mDatas.size() + elem.size() < maxNum) {
                mDatas.addAll(elem);
            } else {
                int index = maxNum - mDatas.size();
                for (int i = 0; i < index; i++) {
                    mDatas.add(elem.get(i));
                }
            }
            notifyDataSetChanged();
        }

    }


    public void set(T oldElem, T newElem) {
        this.set(this.mDatas.indexOf(oldElem), newElem);
    }

    public void set(int index, T elem) {
        this.mDatas.set(index, elem);
        this.notifyDataSetChanged();
    }

    public void remove(T elem) {
        if (this.mDatas != null && elem != null) {
            this.mDatas.remove(elem);
            this.notifyDataSetChanged();
        }

    }

    public void remove(int index) {
        if (this.mDatas != null) {
            this.mDatas.remove(index);
            this.notifyDataSetChanged();
        }

    }

    public void replaceAll(List<T> elem) {
        if (elem != null && !elem.isEmpty()) {
            if (elem.size() <= maxNum) {
                mDatas.clear();
                mDatas.addAll(elem);
            } else {
                int index = maxNum - elem.size();
                for (int i = 0; i < index; i++) {
                    mDatas.add(elem.get(i));
                }
            }
        }
        this.notifyDataSetChanged();
    }

    public boolean contains(T elem) {
        return this.mDatas.contains(elem);
    }

    public void clear() {
        if (this.mDatas != null) {
            this.mDatas.clear();
        }

        this.notifyDataSetChanged();
    }


    public List<T> getDatas() {
        return mDatas;
    }

    public boolean isCanAdd() {
        return isCanAdd;
    }


    public int getMaxNum() {
        return maxNum - (mDatas != null ? mDatas.size() : 0);
    }
}

