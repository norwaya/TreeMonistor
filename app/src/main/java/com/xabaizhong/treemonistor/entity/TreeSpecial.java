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

    /*  "TreeSpeID": "T00047",
        "CHName": "其余冷杉亚科",
        "jianpin": "qylsyk",
        "SpecialCode": "238",
        "ENName": "",
        "Family": "",
        "ToFamily": "",
        "Belong": "",
        "Alias": "其余冷杉亚科",
        "LatinName": "",
        "Explian": "",
        "TreeSpeType": 1*/
    @Id
    private Long id;
    private String treeSpeId;
    private String cname;
    private String jianPin;
    private String treeSpecCode;
    private String enname;
    private String family;
    private String tofamily;
    private String belong;
    private String alias;
    private String latin;
    private String explian;
    private int treeSpeType;


    protected TreeSpecial(Parcel in) {
        treeSpeId = in.readString();
        cname = in.readString();
        jianPin = in.readString();
        treeSpecCode = in.readString();
        enname = in.readString();
        family = in.readString();
        tofamily = in.readString();
        belong = in.readString();
        alias = in.readString();
        latin = in.readString();
        explian = in.readString();
        treeSpeType = in.readInt();
    }

    @Generated(hash = 759337683)
    public TreeSpecial(Long id, String treeSpeId, String cname, String jianPin,
            String treeSpecCode, String enname, String family, String tofamily,
            String belong, String alias, String latin, String explian,
            int treeSpeType) {
        this.id = id;
        this.treeSpeId = treeSpeId;
        this.cname = cname;
        this.jianPin = jianPin;
        this.treeSpecCode = treeSpecCode;
        this.enname = enname;
        this.family = family;
        this.tofamily = tofamily;
        this.belong = belong;
        this.alias = alias;
        this.latin = latin;
        this.explian = explian;
        this.treeSpeType = treeSpeType;
    }

    @Generated(hash = 31977416)
    public TreeSpecial() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(treeSpeId);
        dest.writeString(cname);
        dest.writeString(jianPin);
        dest.writeString(treeSpecCode);
        dest.writeString(enname);
        dest.writeString(family);
        dest.writeString(tofamily);
        dest.writeString(belong);
        dest.writeString(alias);
        dest.writeString(latin);
        dest.writeString(explian);
        dest.writeInt(treeSpeType);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTreeSpeId() {
        return this.treeSpeId;
    }

    public void setTreeSpeId(String treeSpeId) {
        this.treeSpeId = treeSpeId;
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

    public String getTreeSpecCode() {
        return this.treeSpecCode;
    }

    public void setTreeSpecCode(String treeSpecCode) {
        this.treeSpecCode = treeSpecCode;
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

    public int getTreeSpeType() {
        return this.treeSpeType;
    }

    public void setTreeSpeType(int treeSpeType) {
        this.treeSpeType = treeSpeType;
    }
}
