package qwy.anenda.com.baselib.utils;
 /* 
 -----------------------------------------------------------------
 * Author: yzw
 * Create: 2019/3/27
 * Descride:
 * 
 *-----------------------------------------------------------------
 */

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import qwy.anenda.com.baselib.R;

public class GlideUtils {
    public static void loadCircleImg(Context context, String imageUrl, ImageView targetIv){
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.drawable.pic_head_deauft);
        requestOptions.circleCrop();
        Glide.with(context).load(imageUrl).apply(requestOptions).into(targetIv);
    }


    public static void loadHeadImg(Context context, String imageUrl, ImageView targetIv){
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.drawable.pic_head_deauft);
        requestOptions.placeholder(R.drawable.pic_head_deauft);
        requestOptions.circleCrop();
        Glide.with(context).load(imageUrl).apply(requestOptions).into(targetIv);
    }

    public static void loadImg(Context context, String imageUrl, ImageView targetIv){
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.drawable.pic_deauft);
        requestOptions.placeholder(R.drawable.pic_deauft);
        Glide.with(context).load(imageUrl).apply(requestOptions).into(targetIv);
    }
}
