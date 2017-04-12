package com.xabaizhong.treemonistor.entity;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TreeGroup {
    private Long id;

    private String treeId;
    private String placeName;           //	地点
    private String mainTreeName;   //	主要树种
    private String SZJX; //	四至界限
    private String area; //	面积
    private String gSTreeNum;//	古树株数
    private String averageHeight; //	平均高度
    private String averageDiameter;//	平均胸径
    private String averageAge;//	平均年龄
    private String yBDInfo; //	郁闭度
    private String evevation; //	海拔
    private String aspect; //	坡向
    private String slope;//	坡度
    private String soilName;//	土壤名称
    private String soilHeight;//	土壤厚度
    private String xiaMuType; //	下木种类
    private String xiaMuDensity;//	下木密度
    private String dBWType;//	地被物种类
    private String dBWDensity;//	地被物密度
    private String managementUnit;//	管护单位
    private String managementState;//	管护现状
    private String aimsTree;//	目的保护树种
    private String aimsFamily;//	科
    private String aimsBelong;//	属
    private String rWJYInfo; //	人为经营活动情况
    private String suggest;//保护建议
    private String explain;//	照片及说明
    private Date date;

    private List<Map<String, Object>> treeMap;
    @SerializedName("piclist")
    private List<String> picList = new ArrayList<>();
    private String UserID;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getMainTreeName() {
        return mainTreeName;
    }

    public void setMainTreeName(String mainTreeName) {
        this.mainTreeName = mainTreeName;
    }

    public String getSZJX() {
        return SZJX;
    }

    public void setSZJX(String SZJX) {
        this.SZJX = SZJX;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getgSTreeNum() {
        return gSTreeNum;
    }

    public void setgSTreeNum(String gSTreeNum) {
        this.gSTreeNum = gSTreeNum;
    }

    public String getAverageHeight() {
        return averageHeight;
    }

    public void setAverageHeight(String averageHeight) {
        this.averageHeight = averageHeight;
    }

    public String getAverageDiameter() {
        return averageDiameter;
    }

    public void setAverageDiameter(String averageDiameter) {
        this.averageDiameter = averageDiameter;
    }

    public String getAverageAge() {
        return averageAge;
    }

    public void setAverageAge(String averageAge) {
        this.averageAge = averageAge;
    }

    public String getyBDInfo() {
        return yBDInfo;
    }

    public void setyBDInfo(String yBDInfo) {
        this.yBDInfo = yBDInfo;
    }

    public String getEvevation() {
        return evevation;
    }

    public void setEvevation(String evevation) {
        this.evevation = evevation;
    }

    public String getAspect() {
        return aspect;
    }

    public void setAspect(String aspect) {
        this.aspect = aspect;
    }

    public String getSlope() {
        return slope;
    }

    public void setSlope(String slope) {
        this.slope = slope;
    }

    public String getSoilName() {
        return soilName;
    }

    public void setSoilName(String soilName) {
        this.soilName = soilName;
    }

    public String getSoilHeight() {
        return soilHeight;
    }

    public void setSoilHeight(String soilHeight) {
        this.soilHeight = soilHeight;
    }

    public String getXiaMuType() {
        return xiaMuType;
    }

    public void setXiaMuType(String xiaMuType) {
        this.xiaMuType = xiaMuType;
    }

    public String getXiaMuDensity() {
        return xiaMuDensity;
    }

    public void setXiaMuDensity(String xiaMuDensity) {
        this.xiaMuDensity = xiaMuDensity;
    }

    public String getdBWType() {
        return dBWType;
    }

    public void setdBWType(String dBWType) {
        this.dBWType = dBWType;
    }

    public String getdBWDensity() {
        return dBWDensity;
    }

    public void setdBWDensity(String dBWDensity) {
        this.dBWDensity = dBWDensity;
    }

    public String getManagementUnit() {
        return managementUnit;
    }

    public void setManagementUnit(String managementUnit) {
        this.managementUnit = managementUnit;
    }

    public String getManagementState() {
        return managementState;
    }

    public void setManagementState(String managementState) {
        this.managementState = managementState;
    }

    public String getAimsTree() {
        return aimsTree;
    }

    public void setAimsTree(String aimsTree) {
        this.aimsTree = aimsTree;
    }

    public String getAimsFamily() {
        return aimsFamily;
    }

    public void setAimsFamily(String aimsFamily) {
        this.aimsFamily = aimsFamily;
    }

    public String getAimsBelong() {
        return aimsBelong;
    }

    public void setAimsBelong(String aimsBelong) {
        this.aimsBelong = aimsBelong;
    }

    public String getrWJYInfo() {
        return rWJYInfo;
    }

    public void setrWJYInfo(String rWJYInfo) {
        this.rWJYInfo = rWJYInfo;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public List<Map<String, Object>> getTreeMap() {
        return treeMap;
    }

    public void setTreeMap(List<Map<String, Object>> treeMap) {
        this.treeMap = treeMap;
    }

    public List<String> getPicList() {
        return picList;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }
}