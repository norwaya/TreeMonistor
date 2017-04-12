package com.xabaizhong.treemonistor.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by admin on 2017/3/2.
 */
public class Tree {
    private Long id;

    /* private String TreeId;     //古树编号  -----unique
     private String Ivst;     //调查号
     private String CHName;     //中文名
     private String Alias;     //别名
     private String LatinName;     //拉丁名
     private String Family;     //科
     private String Belong;     //属
     private String Town;     //乡镇
     private String Village;     //村
     private String SmallName;     //小地名
     private double Ordinate;     //纵坐标
     private double Abscissa;     //横坐标
     private String SpecialCode;     //特征代码
     private double TreeHeight;     //树高
     private double TreeDBH;     //胸径
     private double CrownAvg;     //冠幅平均
     private double CrownEW;     //冠幅东西
     private double CrownNS;     //冠幅南北
     private String ManagementUnit;     //管护单位
     private String ManagementPersion;     //管护人
     private String TreeHistory;     //古树历史
     private String GrownSpace;     //生长场所
     private String Special;     //特点
     private String bzdm; //标志代码
     private String Owner;     //权属
     private String Level;     //古树等级
     private String Growth;     //生长势
     private String Environment;     //生长环境
     private String Status;     //现存状态
     private double RealAge;     //真实树龄
     private double GuessAge;     //估测树龄
     private double Evevation;     //海拔
     private String aspect;     //坡向
     private String slope;     //坡度
     private String slopePos;     //坡位
     private String soil;     //土壤名称
     private String EnviorFactor;     //影响生长环境因素
     private String SpecStatDesc;     //树木特殊状况描述
     private String SpecDesc;     //树种鉴定记载
     private String Explain;     //照片及说明
     private String Protected;     //地上保护现状
     private String Recovery;     //养护复壮现状
     private String Remark;  //备注*/


    private String treeId;  //id  √
    @SerializedName("treetype")
    private int treeType;//古树标志
    @SerializedName("treearea")
    private int treeArea;//农村  城市
    @SerializedName("treespeid")
    private String treeSpeID; //特征代码
    private String town;//
    private String village;//
    private String smallName;//√
    private String ordinate;// longitude
    private String abscissa;//横坐标  纬度
    private String specialCode; //树种代码  specialCodeId
    private String treeHeight; //
    private String treeDBH;//
    private String crownAvg;//
    private String crownEW;//
    private String crownNS;//
    private String managementUnit;//
    private String managementPersion;//
    private String treeHistory;//
    private String grownSpace;//
    private String special;//
    private String growth;//
    private String enviorment;//
    private String realAge;//
    private String guessAge;//
    private String evevation;//海拔
    private String aspect;//
    private String slope;//
    private String slopePos;//
    private String soil;//
    private String enviorFactor;//
    private String specStatDesc;//树木特殊状况描述
    private String specDesc;//树种鉴定记载
    private String explain;//old  pic
    private String protecte;//保护现状
    private String recovery;//养护
    private String owner;//归属
    private String treeLevel;//等级
    private String treeStatus;//状态
    private Date date;
    private String UserID;
    @SerializedName("piclist")
    private List<String> picList = new ArrayList<>();
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public int getTreeType() {
        return treeType;
    }

    public void setTreeType(int treeType) {
        this.treeType = treeType;
    }

    public int getTreeArea() {
        return treeArea;
    }

    public void setTreeArea(int treeArea) {
        this.treeArea = treeArea;
    }

    public String getTreeSpeID() {
        return treeSpeID;
    }

    public void setTreeSpeID(String treeSpeID) {
        this.treeSpeID = treeSpeID;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getSmallName() {
        return smallName;
    }

    public void setSmallName(String smallName) {
        this.smallName = smallName;
    }

    public String getOrdinate() {
        return ordinate;
    }

    public void setOrdinate(String ordinate) {
        this.ordinate = ordinate;
    }

    public String getAbscissa() {
        return abscissa;
    }

    public void setAbscissa(String abscissa) {
        this.abscissa = abscissa;
    }

    public String getSpecialCode() {
        return specialCode;
    }

    public void setSpecialCode(String specialCode) {
        this.specialCode = specialCode;
    }

    public String getTreeHeight() {
        return treeHeight;
    }

    public void setTreeHeight(String treeHeight) {
        this.treeHeight = treeHeight;
    }

    public String getTreeDBH() {
        return treeDBH;
    }

    public void setTreeDBH(String treeDBH) {
        this.treeDBH = treeDBH;
    }

    public String getCrownAvg() {
        return crownAvg;
    }

    public void setCrownAvg(String crownAvg) {
        this.crownAvg = crownAvg;
    }

    public String getCrownEW() {
        return crownEW;
    }

    public void setCrownEW(String crownEW) {
        this.crownEW = crownEW;
    }

    public String getCrownNS() {
        return crownNS;
    }

    public void setCrownNS(String crownNS) {
        this.crownNS = crownNS;
    }

    public String getManagementUnit() {
        return managementUnit;
    }

    public void setManagementUnit(String managementUnit) {
        this.managementUnit = managementUnit;
    }

    public String getManagementPersion() {
        return managementPersion;
    }

    public void setManagementPersion(String managementPersion) {
        this.managementPersion = managementPersion;
    }

    public String getTreeHistory() {
        return treeHistory;
    }

    public void setTreeHistory(String treeHistory) {
        this.treeHistory = treeHistory;
    }

    public String getGrownSpace() {
        return grownSpace;
    }

    public void setGrownSpace(String grownSpace) {
        this.grownSpace = grownSpace;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getGrowth() {
        return growth;
    }

    public void setGrowth(String growth) {
        this.growth = growth;
    }

    public String getEnviorment() {
        return enviorment;
    }

    public void setEnviorment(String enviorment) {
        this.enviorment = enviorment;
    }

    public String getRealAge() {
        return realAge;
    }

    public void setRealAge(String realAge) {
        this.realAge = realAge;
    }

    public String getGuessAge() {
        return guessAge;
    }

    public void setGuessAge(String guessAge) {
        this.guessAge = guessAge;
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

    public String getSlopePos() {
        return slopePos;
    }

    public void setSlopePos(String slopePos) {
        this.slopePos = slopePos;
    }

    public String getSoil() {
        return soil;
    }

    public void setSoil(String soil) {
        this.soil = soil;
    }

    public String getEnviorFactor() {
        return enviorFactor;
    }

    public void setEnviorFactor(String enviorFactor) {
        this.enviorFactor = enviorFactor;
    }

    public String getSpecStatDesc() {
        return specStatDesc;
    }

    public void setSpecStatDesc(String specStatDesc) {
        this.specStatDesc = specStatDesc;
    }

    public String getSpecDesc() {
        return specDesc;
    }

    public void setSpecDesc(String specDesc) {
        this.specDesc = specDesc;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getProtecte() {
        return protecte;
    }

    public void setProtecte(String protecte) {
        this.protecte = protecte;
    }

    public String getRecovery() {
        return recovery;
    }

    public void setRecovery(String recovery) {
        this.recovery = recovery;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTreeLevel() {
        return treeLevel;
    }

    public void setTreeLevel(String treeLevel) {
        this.treeLevel = treeLevel;
    }

    public String getTreeStatus() {
        return treeStatus;
    }

    public void setTreeStatus(String treeStatus) {
        this.treeStatus = treeStatus;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public List<String> getPicList() {
        return picList;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }
}
