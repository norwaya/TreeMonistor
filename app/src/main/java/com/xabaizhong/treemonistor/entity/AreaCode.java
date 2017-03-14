package com.xabaizhong.treemonistor.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by admin on 2017/3/14.
 */
@Entity(indexes = {@Index(value = "id DESC", unique = true)})
public class AreaCode {
    @Id
    private Long id;
    private String cname;
    private int areaId;

    @Generated(hash = 1188577325)
    public AreaCode(Long id, String cname, int areaId) {
        this.id = id;
        this.cname = cname;
        this.areaId = areaId;
    }

    @Generated(hash = 1386038786)
    public AreaCode() {
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

    public int getAreaId() {
        return this.areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

}
