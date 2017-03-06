package com.xabaizhong.treemonitor.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by admin on 2017/2/27.
 */

public class ResultOfExpert implements Parcelable{
    private long id;
    private String result;
    private Date date;

    public ResultOfExpert(long id, String result, Date date) {
        this.id = id;
        this.result = result;
        this.date = date;
    }

    protected ResultOfExpert(Parcel in) {
        id = in.readLong();
        result = in.readString();
    }

    public static final Creator<ResultOfExpert> CREATOR = new Creator<ResultOfExpert>() {
        @Override
        public ResultOfExpert createFromParcel(Parcel in) {
            return new ResultOfExpert(in);
        }

        @Override
        public ResultOfExpert[] newArray(int size) {
            return new ResultOfExpert[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(result);
    }
}
