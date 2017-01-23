package com.shiqkuangsan.mycustomviews.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.shiqkuangsan.mycustomviews.MyApplication;
import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.ui.custom.photoview.PhotoView;
import com.shiqkuangsan.mycustomviews.utils.SimplexUtil;

import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by shiqkuangsan on 2016/9/13.
 */

/**
 * 图片查看主界面的GridView适配器
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
//        if (convertView == null) {
//            PhotoView photoView = new PhotoView(parent.getContext());
//            float sum = parent.getContext().getResources().getDisplayMetrics().widthPixels -
//                    parent.getContext().getResources().getDimensionPixelSize(R.dimen.dip_50);
//            int width = (int) (sum / 3);
//            AbsListView.LayoutParams params = new AbsListView.LayoutParams(width, width - 50);
//            photoView.setLayoutParams(params);
//            photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            convertView = photoView;
//        }
//        PhotoView p = (PhotoView) convertView;
//        p.setEnabled(false);
//        ImageLoader.getInstance().displayImage(picsList.get(position), p, MyApplication.getOptionsOfImageLoader(),
//                new SimpleImageLoadingListener() {
//                    @Override
//                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                        //only loadedImage is available we can click item
//                        view.setEnabled(true);
//                    }
//                });
//        return p;

        if (convertView == null) {
            float sum = parent.getContext().getResources().getDisplayMetrics().widthPixels -
                    parent.getContext().getResources().getDimensionPixelSize(R.dimen.dip_50);
            int width = (int) (sum / 3);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(width, width);
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(params);
            imageView.setEnabled(false);
            convertView = imageView;
        }
        final ImageView imageView = (ImageView) convertView;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setTag(picsList.get(position));
        }
        SimplexUtil.loadImage(imageView, picsList.get(position), SimplexUtil.getSimpleImageOptions(2), new SimplexUtil.SimpleRequstCallBack<Drawable>() {
            @Override
            public void onSuccess(Drawable result) {
                imageView.setEnabled(true);
            }
        });

        return imageView;
    }

}
