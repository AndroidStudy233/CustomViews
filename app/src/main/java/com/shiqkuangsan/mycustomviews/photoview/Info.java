package com.shiqkuangsan.mycustomviews.photoview;

/**
 * Created by shiqkuangsan on 2016/9/21.
 */

import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

/**
 * Created by liuheng on 2015/8/19.
 */
public class Info implements Parcelable {

    // 内部图片在整个手机界面的位置
    RectF mRect = new RectF();

    // 控件在窗口的位置
    RectF mImgRect = new RectF();

    RectF mWidgetRect = new RectF();

    RectF mBaseRect = new RectF();

    PointF mScreenCenter = new PointF();

    float mScale;

    float mDegrees;

    ImageView.ScaleType mScaleType;

    public Info(){

    }

    public Info(RectF rect, RectF img, RectF widget, RectF base, PointF screenCenter, float scale, float degrees, ImageView.ScaleType scaleType) {
        mRect.set(rect);
        mImgRect.set(img);
        mWidgetRect.set(widget);
        mScale = scale;
        mScaleType = scaleType;
        mDegrees = degrees;
        mBaseRect.set(base);
        mScreenCenter.set(screenCenter);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mRect, flags);
        dest.writeParcelable(this.mImgRect, flags);
        dest.writeParcelable(this.mWidgetRect, flags);
        dest.writeParcelable(this.mBaseRect, flags);
        dest.writeParcelable(this.mScreenCenter, flags);
        dest.writeFloat(this.mScale);
        dest.writeFloat(this.mDegrees);
        dest.writeInt(this.mScaleType == null ? -1 : this.mScaleType.ordinal());
    }

    protected Info(Parcel in) {
        this.mRect = in.readParcelable(RectF.class.getClassLoader());
        this.mImgRect = in.readParcelable(RectF.class.getClassLoader());
        this.mWidgetRect = in.readParcelable(RectF.class.getClassLoader());
        this.mBaseRect = in.readParcelable(RectF.class.getClassLoader());
        this.mScreenCenter = in.readParcelable(PointF.class.getClassLoader());
        this.mScale = in.readFloat();
        this.mDegrees = in.readFloat();
        int tmpMScaleType = in.readInt();
        this.mScaleType = tmpMScaleType == -1 ? null : ImageView.ScaleType.values()[tmpMScaleType];
    }

    public static final Parcelable.Creator<Info> CREATOR = new Parcelable.Creator<Info>() {
        @Override
        public Info createFromParcel(Parcel source) {
            return new Info(source);
        }

        @Override
        public Info[] newArray(int size) {
            return new Info[size];
        }
    };
}
