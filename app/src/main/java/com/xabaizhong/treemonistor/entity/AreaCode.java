package com.xabaizhong.treemonistor.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by admin on 2017/3/14.
 */
@Entity(indexes = {@Index(value = "id,areaId DESC", unique = true)})
public class AreaCode {
    @Id
    private Long id;
    private String areaId;//	nvarchar(50)	区域ID
    private String parentId;//	nvarchar(50)	父节点
    private String name;//nvarchar(50)	名称
    private String mergerName;//nvarchar(255)	全称
    private String shortName;//	nvarchar(255)	简称
    private String mergerShortName;//	nvarchar(255)	简全称
    private String levelType;//	nvarchar(10)	级别
    private String cityCode;//	nvarchar(50)	区号
    private String zipCode;//	nvarchar(50)
    private String pinyin;//nvarchar(50)	全拼
    private String jianpin;//	nvarchar(20)	简拼
    private String firstChar;//	nvarchar(255)	首字符
    private String lng;//nvarchar(255)	经度
    private String lat;//nvarchar(255)	纬度
    private String remarks;//	nvarchar(255)

    @Generated(hash = 1788978997)
    public AreaCode(Long id, String areaId, String parentId, String name,
                    String mergerName, String shortName, String mergerShortName,
                    String levelType, String cityCode, String zipCode, String pinyin,
                    String jianpin, String firstChar, String lng, String lat,
                    String remarks) {
        this.id = id;
        this.areaId = areaId;
        this.parentId = parentId;
        this.name = name;
        this.mergerName = mergerName;
        this.shortName = shortName;
        this.mergerShortName = mergerShortName;
        this.levelType = levelType;
        this.cityCode = cityCode;
        this.zipCode = zipCode;
        this.pinyin = pinyin;
        this.jianpin = jianpin;
        this.firstChar = firstChar;
        this.lng = lng;
        this.lat = lat;
        this.remarks = remarks;
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

    public String getAreaId() {
        return this.areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMergerName() {
        return this.mergerName;
    }

    public void setMergerName(String mergerName) {
        this.mergerName = mergerName;
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getMergerShortName() {
        return this.mergerShortName;
    }

    public void setMergerShortName(String mergerShortName) {
        this.mergerShortName = mergerShortName;
    }

    public String getLevelType() {
        return this.levelType;
    }

    public void setLevelType(String levelType) {
        this.levelType = levelType;
    }

    public String getCityCode() {
        return this.cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPinyin() {
        return this.pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getJianpin() {
        return this.jianpin;
    }

    public void setJianpin(String jianpin) {
        this.jianpin = jianpin;
    }

    public String getFirstChar() {
        return this.firstChar;
    }

    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar;
    }

    public String getLng() {
        return this.lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return this.lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


}
