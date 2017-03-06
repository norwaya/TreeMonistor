package com.xabaizhong.treemonistor.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 2017/2/24.
 */


public class News implements Parcelable{
    private long id;
    private String content;

    public News(String content, long id) {
        this.content = content;
        this.id = id;
    }

    protected News(Parcel in) {
        id = in.readLong();
        content = in.readString();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(content);
    }
}
