package com.xabaizhong.treemonistor.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by admin on 2017/3/13.
 */
@Entity(indexes = {
        @Index(value = "id DESC", unique = true)
})
public class TreeSpecial implements Parcelable {

    /* private int SpeID;
     private String CHName;
     private String Jianpin;
     private String SpecialCode;
     private String ENName;
     private String Family;
     private String ToFamily;
     private String Belong;
     private String Alias;
     private String LatinName;
     private String Explian;
     private String TreeSpeID;
     private int ReviewSta;
     private String Memo;
     private String Memo1;
     private String Memo2;*/
    @Id
    private Long id;
    private String cname;
    private String jianPin;
    private String code;
    private String enname;
    private String family;
    private String tofamily;
    private String belong;
    private String alias;
    private String latin;
    private String explian;
    private String treeSpecCode;
    private int ReviewSta;


    protected TreeSpecial(Parcel in) {
        cname = in.readString();
        jianPin = in.readString();
        code = in.readString();
        enname = in.readString();
        family = in.readString();
        tofamily = in.readString();
        belong = in.readString();
        alias = in.readString();
        latin = in.readString();
        explian = in.readString();
        treeSpecCode = in.readString();
        ReviewSta = in.readInt();
    }

    @Generated(hash = 1556976984)
    public TreeSpecial(Long id, String cname, String jianPin, String code,
            String enname, String family, String tofamily, String belong,
            String alias, String latin, String explian, String treeSpecCode,
            int ReviewSta) {
        this.id = id;
        this.cname = cname;
        this.jianPin = jianPin;
        this.code = code;
        this.enname = enname;
        this.family = family;
        this.tofamily = tofamily;
        this.belong = belong;
        this.alias = alias;
        this.latin = latin;
        this.explian = explian;
        this.treeSpecCode = treeSpecCode;
        this.ReviewSta = ReviewSta;
    }

    @Generated(hash = 31977416)
    public TreeSpecial() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cname);
        dest.writeString(jianPin);
        dest.writeString(code);
        dest.writeString(enname);
        dest.writeString(family);
        dest.writeString(tofamily);
        dest.writeString(belong);
        dest.writeString(alias);
        dest.writeString(latin);
        dest.writeString(explian);
        dest.writeString(treeSpecCode);
        dest.writeInt(ReviewSta);
    }

    @Override
    public int describeContents() {
        return 0;
    }

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

    public String getJianPin() {
        return this.jianPin;
    }

    public void setJianPin(String jianPin) {
        this.jianPin = jianPin;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
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

    public String getExplian() {
        return this.explian;
    }

    public void setExplian(String explian) {
        this.explian = explian;
    }

    public String getTreeSpecCode() {
        return this.treeSpecCode;
    }

    public void setTreeSpecCode(String treeSpecCode) {
        this.treeSpecCode = treeSpecCode;
    }

    public int getReviewSta() {
        return this.ReviewSta;
    }

    public void setReviewSta(int ReviewSta) {
        this.ReviewSta = ReviewSta;
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
}
