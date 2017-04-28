package com.xabaizhong.treemonistor.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.ToOne;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by admin on 2017/3/17.
 */

@Entity(indexes = {
        @Index(value = "id DESC", unique = true)
})
public class Weakness {
    /* @SerializedName("TID")
     private int TID;
     @SerializedName("illName")
     private String illName;
     @SerializedName("Trait")
     private String Trait;
     @SerializedName("Method")
     private String Method;
     @SerializedName("Part")
     private int Part;
     @SerializedName("Feature")
     private String Feature;
     @SerializedName("Name")
     private String Name;*/
    @Id
    private Long id;
    private int tid;
    private String name;
    private String trait;
    private String method;
    private int partId;
    private String freature;
    private String partName;

    @Generated(hash = 152693552)
    public Weakness(Long id, int tid, String name, String trait, String method,
                    int partId, String freature, String partName) {
        this.id = id;
        this.tid = tid;
        this.name = name;
        this.trait = trait;
        this.method = method;
        this.partId = partId;
        this.freature = freature;
        this.partName = partName;
    }

    @Generated(hash = 52880024)
    public Weakness() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTid() {
        return this.tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrait() {
        return this.trait;
    }

    public void setTrait(String trait) {
        this.trait = trait;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getPartId() {
        return this.partId;
    }

    public void setPartId(int partId) {
        this.partId = partId;
    }

    public String getFreature() {
        return this.freature;
    }

    public void setFreature(String freature) {
        this.freature = freature;
    }

    public String getPartName() {
        return this.partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }
}
