package com.shiqkuangsan.mycustomviews.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by shiqkuangsan on 2016/5/4.
 */

/**
 * 发送内容封装成的bean类,说说 + 图片(String)
 */
public class Moment implements Parcelable {
    public String content;
    public ArrayList<String> photos;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
        dest.writeStringList(this.photos);
    }

    public Moment() {
    }

    public Moment(String content, ArrayList<String> photos) {
        this.content = content;
        this.photos = photos;
    }

    protected Moment(Parcel in) {
        this.content = in.readString();
        this.photos = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Moment> CREATOR = new Parcelable.Creator<Moment>() {
        @Override
        public Moment createFromParcel(Parcel source) {
            return new Moment(source);
        }

        @Override
        public Moment[] newArray(int size) {
            return new Moment[size];
        }
    };
}