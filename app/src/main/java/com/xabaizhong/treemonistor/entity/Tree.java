package com.xabaizhong.treemonistor.entity;


import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import static com.xabaizhong.treemonistor.utils.InputVerification.isNull;

/**
 * Created by admin on 2017/3/2.
 */
public class Tree {
    /**
     * UserID : test1
     * abscissa : 34.201390077101095
     * aspect : 4
     * crownAvg : 21.1
     * crownEW : 14.55
     * crownNS : 14.44
     * date : 2017-04-27 16:45:10
     * enviorFactor : Wkk2
     * enviorment : 2
     * evevation : 2.2
     * grownSpace : 2
     * growth : 3
     * guessAge : 14.2
     * managementPersion : J2i2
     * managementUnit : 2828
     * ordinate : 108.89134199999988
     * owner : 2
     * piclist : []
     * protecte : BH12
     * realAge : 14.1
     * recovery : YH12
     * slope : 5
     * slopePos : 4
     * smallName : 丈八一路SOHO东南107米
     * soil : 7
     * specDesc : K2k2
     * specStatDesc : Kwk2
     * special : 2
     * treeDBH : 14.1
     * treeHeight : 14.1
     * treeHistory : 2k2k
     * treeId : 1
     * treeLevel : 2
     * treespeid : T00128
     * treeStatus : 2
     * treearea : 0
     * treetype : 2
     */

    @SerializedName("UserID")
    private String UserID;
    @SerializedName("abscissa")
    private String abscissa;
    @SerializedName("aspect")
    private String aspect;
    @SerializedName("crownAvg")
    private double crownAvg = 0.0;
    @SerializedName("crownEW")
    private double crownEW = 0.0;
    @SerializedName("crownNS")
    private double crownNS = 0.0;
    @SerializedName("date")
    private Date date = new Date();
    @SerializedName("enviorFactor")
    private String enviorFactor = "";
    @SerializedName("enviorment")
    private String enviorment = "";
    @SerializedName("evevation")
    private double evevation = 0.0;
    @SerializedName("grownSpace")
    private String grownSpace = "";
    @SerializedName("growth")
    private String growth = "";
    @SerializedName("guessAge")
    private double guessAge = 0.0;
    @SerializedName("managementPersion")
    private String managementPersion = "";
    @SerializedName("managementUnit")
    private String managementUnit = "";
    @SerializedName("ordinate")
    private String ordinate = "";
    @SerializedName("owner")
    private String owner = "";
    @SerializedName("protecte")
    private String protecte = "";
    @SerializedName("realAge")
    private double realAge = 0.0;
    @SerializedName("recovery")
    private String recovery = "";
    @SerializedName("slope")
    private String slope = "";
    @SerializedName("slopePos")
    private String slopePos = "";
    @SerializedName("smallName")
    private String smallName = "";
    @SerializedName("soil")
    private String soil = "";
    @SerializedName("specDesc")
    private String specDesc = "";
    @SerializedName("specStatDesc")
    private String specStatDesc = "";
    @SerializedName("special")
    private String special = "";
    @SerializedName("treeDBH")
    private double treeDBH = 0.0;
    @SerializedName("treeHeight")
    private double treeHeight = 0.0;
    @SerializedName("treeHistory")
    private String treeHistory = "";
    @SerializedName("treeId")
    private String treeId = "";
    @SerializedName("treeLevel")
    private String treeLevel = "";
    @SerializedName("treespeid")
    private String treespeid = "";
    @SerializedName("treeStatus")
    private String treeStatus = "";
    @SerializedName("treearea")
    private int treearea = 0;
    @SerializedName("treetype")
    private int treetype = 0;
    @SerializedName("piclist")
    private List<?> piclist;
    private String town = "";
    private String village = "";
    private String explain = "";
    private double specialCode = 0;

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

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
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


    public String check() {
        if (isNull(treeId)) {
            return "古树/群编号为空";
        }
        if (isNull(treespeid)) {
            return "树种为空";
        }
        if (isNull(treeHeight + "")) {
            return "树高为空";
        }
        if (isNull(treeDBH + "")) {
            return "胸径为空";
        }
        if (isNull(crownEW + "")) {
            return "error";
        }
        if (isNull(crownNS + "")) {
            return "error";
        }
        if (isNull(managementUnit) && isNull(managementPersion)) {
            return "管护单位/人为空";
        }
        if (isNull(treeHistory)) {
            return "古树历史为空";
        }
        if (isNull(grownSpace)) {
            return "生长场所为空";
        }
        if (isNull(growth)) {
            return "生长势为空";
        }
        if (isNull(realAge + "")) {
            return "年龄为空";
        }
        if (isNull(aspect)) {
            return "坡向为空";
        }
        if (isNull(slope)) {
            return "坡度为空";
        }
        if (isNull(slopePos)) {
            return "坡位为空";
        }
        if (isNull(soil)) {
            return "土壤为空";
        }
        if (isNull(protecte)) {
            return "error";
        }
        if (isNull(recovery)) {
            return "error";
        }
        if (isNull(owner)) {
            return "owner";
        }
        if (isNull(treeStatus)) {
            return "treestatus";
        }
        return null;
    }


    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getAbscissa() {
        return abscissa;
    }

    public void setAbscissa(String abscissa) {
        this.abscissa = abscissa;
    }

    public String getAspect() {
        return aspect;
    }

    public void setAspect(String aspect) {
        this.aspect = aspect;
    }

    public double getCrownAvg() {
        return crownAvg;
    }

    public void setCrownAvg(double crownAvg) {
        this.crownAvg = crownAvg;
    }

    public double getCrownEW() {
        return crownEW;
    }

    public void setCrownEW(double crownEW) {
        this.crownEW = crownEW;
    }

    public double getCrownNS() {
        return crownNS;
    }

    public void setCrownNS(double crownNS) {
        this.crownNS = crownNS;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEnviorFactor() {
        return enviorFactor;
    }

    public void setEnviorFactor(String enviorFactor) {
        this.enviorFactor = enviorFactor;
    }

    public String getEnviorment() {
        return enviorment;
    }

    public void setEnviorment(String enviorment) {
        this.enviorment = enviorment;
    }

    public double getEvevation() {
        return evevation;
    }

    public void setEvevation(double evevation) {
        this.evevation = evevation;
    }

    public String getGrownSpace() {
        return grownSpace;
    }

    public void setGrownSpace(String grownSpace) {
        this.grownSpace = grownSpace;
    }

    public String getGrowth() {
        return growth;
    }

    public void setGrowth(String growth) {
        this.growth = growth;
    }

    public double getGuessAge() {
        return guessAge;
    }

    public void setGuessAge(double guessAge) {
        this.guessAge = guessAge;
    }

    public String getManagementPersion() {
        return managementPersion;
    }

    public void setManagementPersion(String managementPersion) {
        this.managementPersion = managementPersion;
    }

    public String getManagementUnit() {
        return managementUnit;
    }

    public void setManagementUnit(String managementUnit) {
        this.managementUnit = managementUnit;
    }

    public String getOrdinate() {
        return ordinate;
    }

    public void setOrdinate(String ordinate) {
        this.ordinate = ordinate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getProtecte() {
        return protecte;
    }

    public void setProtecte(String protecte) {
        this.protecte = protecte;
    }

    public double getRealAge() {
        return realAge;
    }

    public void setRealAge(double realAge) {
        this.realAge = realAge;
    }

    public String getRecovery() {
        return recovery;
    }

    public void setRecovery(String recovery) {
        this.recovery = recovery;
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

    public String getSmallName() {
        return smallName;
    }

    public void setSmallName(String smallName) {
        this.smallName = smallName;
    }

    public String getSoil() {
        return soil;
    }

    public void setSoil(String soil) {
        this.soil = soil;
    }

    public String getSpecDesc() {
        return specDesc;
    }

    public void setSpecDesc(String specDesc) {
        this.specDesc = specDesc;
    }

    public String getSpecStatDesc() {
        return specStatDesc;
    }

    public void setSpecStatDesc(String specStatDesc) {
        this.specStatDesc = specStatDesc;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public double getTreeDBH() {
        return treeDBH;
    }

    public void setTreeDBH(double treeDBH) {
        this.treeDBH = treeDBH;
    }

    public double getTreeHeight() {
        return treeHeight;
    }

    public void setTreeHeight(double treeHeight) {
        this.treeHeight = treeHeight;
    }

    public String getTreeHistory() {
        return treeHistory;
    }

    public void setTreeHistory(String treeHistory) {
        this.treeHistory = treeHistory;
    }

    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    public String getTreeLevel() {
        return treeLevel;
    }

    public void setTreeLevel(String treeLevel) {
        this.treeLevel = treeLevel;
    }

    public String getTreespeid() {
        return treespeid;
    }

    public void setTreespeid(String treespeid) {
        this.treespeid = treespeid;
    }

    public String getTreeStatus() {
        return treeStatus;
    }

    public void setTreeStatus(String treeStatus) {
        this.treeStatus = treeStatus;
    }

    public int getTreearea() {
        return treearea;
    }

    public void setTreearea(int treearea) {
        this.treearea = treearea;
    }

    public int getTreetype() {
        return treetype;
    }

    public void setTreetype(int treetype) {
        this.treetype = treetype;
    }

    public List<?> getPiclist() {
        return piclist;
    }

    public void setPiclist(List<?> piclist) {
        this.piclist = piclist;
    }
}
