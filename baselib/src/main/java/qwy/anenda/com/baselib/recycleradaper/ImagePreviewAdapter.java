package qwy.anenda.com.baselib.recycleradaper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

import qwy.anenda.com.baselib.R;
import qwy.anenda.com.baselib.base.ImagePreviewActivity;
import qwy.anenda.com.baselib.utils.GlideUtils;


public class ImagePreviewAdapter extends PagerAdapter implements OnPhotoTapListener {
    String TAG="ImagePreviewAdapter";
    private List<String> imageInfo;
    private Context context;
    private View currentView;
//
    public ImagePreviewAdapter(Context context, @NonNull List<String> imageInfo) {
        super();
        this.imageInfo = imageInfo;
        this.context = context;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        currentView = (View) object;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    public View getPrimaryItem() {
        return currentView;
    }

    public ImageView getPrimaryImageView() {
        return (ImageView) currentView.findViewById(R.id.pv);
    }

    @Override
    public int getCount() {
        return imageInfo.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_photoview, container, false);
        final ProgressBar pb =  view.findViewById(R.id.pb);
        final PhotoView imageView =  view.findViewById(R.id.pv);
        imageView.setOnPhotoTapListener(this);
        String info = this.imageInfo.get(position);
        if (info == null || "".equals(info) || info.endsWith("null")) {
            imageView.setImageResource(R.drawable.pic_deauft);
        } else {
//            RequestOptions requestOptions = new RequestOptions()
//                    .dontAnimate()
//                    .placeholder(R.drawable.ad_mall_nopicture) //占位符 也就是加载中的图片，可放个gif
//                    .error(R.drawable.icon_me_default_avatar); //失败图片;
            GlideUtils.loadImg(context,info,imageView);
        }
        container.addView(view);
        return view;
    }

    /**
     * 展示过度图片
     */
    private void showExcessPic(String imageInfo, PhotoView imageView) {
        //先获取大图的缓存图片
        Bitmap cacheImage = null;// NineGridView.getImageLoader().getCacheImage(imageInfo);
//        如果大图的缓存不存在,在获取小图的缓存
//        if (cacheImage == null) cacheImage = NineGridView.getImageLoader().getCacheImage(imageInfo);
        //如果没有任何缓存,使用默认图片,否者使用缓存
        if (cacheImage == null) {
            imageView.setImageResource(R.drawable.pic_deauft);
        } else {
            imageView.setImageBitmap(cacheImage);
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        final PhotoView imageView =  view.findViewById(R.id.pv);
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable == null) {
                drawable = imageView.getBackground();
            }
            if (drawable != null) {
                drawable.setCallback(null);
            }
        }
        container.removeView((View) object);
    }

    @Override
    public void onPhotoTap(ImageView view, float x, float y) {
        ((ImagePreviewActivity) context).finishActivityAnim();
    }
}
