package com.xabaizhong.treemonistor.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

@Entity(indexes = {
        @Index(value = "id, treeId DESC", unique = true)
})
public class TreeGroup {
    @Id
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
@Generated(hash = 748440422)
public TreeGroup(Long id, String treeId, String placeName, String mainTreeName,
        String SZJX, String area, String gSTreeNum, String averageHeight,
        String averageDiameter, String averageAge, String yBDInfo,
        String evevation, String aspect, String slope, String soilName,
        String soilHeight, String xiaMuType, String xiaMuDensity,
        String dBWType, String dBWDensity, String managementUnit,
        String managementState, String aimsTree, String aimsFamily,
        String aimsBelong, String rWJYInfo, String suggest, String explain) {
    this.id = id;
    this.treeId = treeId;
    this.placeName = placeName;
    this.mainTreeName = mainTreeName;
    this.SZJX = SZJX;
    this.area = area;
    this.gSTreeNum = gSTreeNum;
    this.averageHeight = averageHeight;
    this.averageDiameter = averageDiameter;
    this.averageAge = averageAge;
    this.yBDInfo = yBDInfo;
    this.evevation = evevation;
    this.aspect = aspect;
    this.slope = slope;
    this.soilName = soilName;
    this.soilHeight = soilHeight;
    this.xiaMuType = xiaMuType;
    this.xiaMuDensity = xiaMuDensity;
    this.dBWType = dBWType;
    this.dBWDensity = dBWDensity;
    this.managementUnit = managementUnit;
    this.managementState = managementState;
    this.aimsTree = aimsTree;
    this.aimsFamily = aimsFamily;
    this.aimsBelong = aimsBelong;
    this.rWJYInfo = rWJYInfo;
    this.suggest = suggest;
    this.explain = explain;
}
@Generated(hash = 1161991029)
public TreeGroup() {
}
public Long getId() {
    return this.id;
}
public void setId(Long id) {
    this.id = id;
}
public String getTreeId() {
    return this.treeId;
}
public void setTreeId(String treeId) {
    this.treeId = treeId;
}
public String getPlaceName() {
    return this.placeName;
}
public void setPlaceName(String placeName) {
    this.placeName = placeName;
}
public String getMainTreeName() {
    return this.mainTreeName;
}
public void setMainTreeName(String mainTreeName) {
    this.mainTreeName = mainTreeName;
}
public String getSZJX() {
    return this.SZJX;
}
public void setSZJX(String SZJX) {
    this.SZJX = SZJX;
}
public String getArea() {
    return this.area;
}
public void setArea(String area) {
    this.area = area;
}
public String getGSTreeNum() {
    return this.gSTreeNum;
}
public void setGSTreeNum(String gSTreeNum) {
    this.gSTreeNum = gSTreeNum;
}
public String getAverageHeight() {
    return this.averageHeight;
}
public void setAverageHeight(String averageHeight) {
    this.averageHeight = averageHeight;
}
public String getAverageDiameter() {
    return this.averageDiameter;
}
public void setAverageDiameter(String averageDiameter) {
    this.averageDiameter = averageDiameter;
}
public String getAverageAge() {
    return this.averageAge;
}
public void setAverageAge(String averageAge) {
    this.averageAge = averageAge;
}
public String getYBDInfo() {
    return this.yBDInfo;
}
public void setYBDInfo(String yBDInfo) {
    this.yBDInfo = yBDInfo;
}
public String getEvevation() {
    return this.evevation;
}
public void setEvevation(String evevation) {
    this.evevation = evevation;
}
public String getAspect() {
    return this.aspect;
}
public void setAspect(String aspect) {
    this.aspect = aspect;
}
public String getSlope() {
    return this.slope;
}
public void setSlope(String slope) {
    this.slope = slope;
}
public String getSoilName() {
    return this.soilName;
}
public void setSoilName(String soilName) {
    this.soilName = soilName;
}
public String getSoilHeight() {
    return this.soilHeight;
}
public void setSoilHeight(String soilHeight) {
    this.soilHeight = soilHeight;
}
public String getXiaMuType() {
    return this.xiaMuType;
}
public void setXiaMuType(String xiaMuType) {
    this.xiaMuType = xiaMuType;
}
public String getXiaMuDensity() {
    return this.xiaMuDensity;
}
public void setXiaMuDensity(String xiaMuDensity) {
    this.xiaMuDensity = xiaMuDensity;
}
public String getDBWType() {
    return this.dBWType;
}
public void setDBWType(String dBWType) {
    this.dBWType = dBWType;
}
public String getDBWDensity() {
    return this.dBWDensity;
}
public void setDBWDensity(String dBWDensity) {
    this.dBWDensity = dBWDensity;
}
public String getManagementUnit() {
    return this.managementUnit;
}
public void setManagementUnit(String managementUnit) {
    this.managementUnit = managementUnit;
}
public String getManagementState() {
    return this.managementState;
}
public void setManagementState(String managementState) {
    this.managementState = managementState;
}
public String getAimsTree() {
    return this.aimsTree;
}
public void setAimsTree(String aimsTree) {
    this.aimsTree = aimsTree;
}
public String getAimsFamily() {
    return this.aimsFamily;
}
public void setAimsFamily(String aimsFamily) {
    this.aimsFamily = aimsFamily;
}
public String getAimsBelong() {
    return this.aimsBelong;
}
public void setAimsBelong(String aimsBelong) {
    this.aimsBelong = aimsBelong;
}
public String getRWJYInfo() {
    return this.rWJYInfo;
}
public void setRWJYInfo(String rWJYInfo) {
    this.rWJYInfo = rWJYInfo;
}
public String getSuggest() {
    return this.suggest;
}
public void setSuggest(String suggest) {
    this.suggest = suggest;
}
public String getExplain() {
    return this.explain;
}
public void setExplain(String explain) {
    this.explain = explain;
}
}