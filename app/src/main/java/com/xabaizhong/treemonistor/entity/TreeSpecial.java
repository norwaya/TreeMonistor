package com.xabaizhong.treemonistor.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by admin on 2017/3/13.
 */
@Entity(indexes = {
        @Index(value = "id DESC", unique = true)
})
public class TreeSpecial implements Parcelable{
    @Id
    private Long id;
    private String cname;
    private int code;
    private String enname;
    private String family;
    private String tofamily;
    private String belong;
    private String alias;
    private String latin;
    private String remark;

    @Generated(hash = 1965726077)
    public TreeSpecial(Long id, String cname, int code, String enname,
                       String family, String tofamily, String belong, String alias,
                       String latin, String remark) {
        this.id = id;
        this.cname = cname;
        this.code = code;
        this.enname = enname;
        this.family = family;
        this.tofamily = tofamily;
        this.belong = belong;
        this.alias = alias;
        this.latin = latin;
        this.remark = remark;
    }

    @Generated(hash = 31977416)
    public TreeSpecial() {
    }

    protected TreeSpecial(Parcel in) {
        cname = in.readString();
        code = in.readInt();
        enname = in.readString();
        family = in.readString();
        tofamily = in.readString();
        belong = in.readString();
        alias = in.readString();
        latin = in.readString();
        remark = in.readString();
    }

    public static final Creator<TreeSpecial> CREATOR = new Creator<TreeSpecial>() {
        @Override
        public TreeSpecial createFromParcel(Parcel in) {
            return new TreeSpecial(in);
        }

        @Override
        public TreeSpecial[] newArray(int size) {
            return new TreeSpecial[size];
        }
    };

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCname() {
        return this.cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getEnname() {
        return this.enname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    public String getFamily() {
        return this.family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getTofamily() {
        return this.tofamily;
    }

    public void setTofamily(String tofamily) {
        this.tofamily = tofamily;
    }

    public String getBelong() {
        return this.belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getLatin() {
        return this.latin;
    }

    public void setLatin(String latin) {
        this.latin = latin;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cname);
        dest.writeInt(code);
        dest.writeString(enname);
        dest.writeString(family);
        dest.writeString(tofamily);
        dest.writeString(belong);
        dest.writeString(alias);
        dest.writeString(latin);
        dest.writeString(remark);
    }
}
