package com.xabaizhong.treemonistor.service.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 2017/3/15.
 */

public class User {
    /**
     * name : sa
     * areaId : admin
     * permission : 3
     */

    private String name;
    private String areaId;
    private String permission;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return this.name + "\t" + this.areaId + "\t" + this.getPermission();
    }
}
