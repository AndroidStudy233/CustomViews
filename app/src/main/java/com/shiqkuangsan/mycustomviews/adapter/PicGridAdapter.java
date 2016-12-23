package com.shiqkuangsan.mycustomviews.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.shiqkuangsan.mycustomviews.MyApplication;
import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.ui.custom.photoview.PhotoView;

import java.util.List;

/**
 * Created by shiqkuangsan on 2016/9/13.
 */

/**
 * 图片查看界面的GridView适配器
 */
public class PicGridAdapter extends BaseAdapter {

    private List<String> picsList;
    private Context context;

    public PicGridAdapter(List<String> picsList, Context context) {
        this.picsList = picsList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return picsList == null ? 0 : picsList.size();
    }

    @Override
    public Object getItem(int position) {
        return picsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            PhotoView p = new PhotoView(parent.getContext());
            float sum = parent.getContext().getResources().getDisplayMetrics().widthPixels -
                    parent.getContext().getResources().getDimensionPixelSize(R.dimen.dip_50);
            int width = (int) (sum / 3);
            p.setScaleType(ImageView.ScaleType.FIT_CENTER);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(width, width - 50);
            p.setLayoutParams(params);
            convertView = p;
        }
        PhotoView p = (PhotoView) convertView;
        p.setEnabled(false);
        ImageLoader.getInstance().displayImage(picsList.get(position), p, MyApplication.getPicOptionsWithLoading(),
                new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        view.setEnabled(true);//only loadedImage is available we can click item
                    }
                });
        return p;
    }

}
