package com.xabaizhong.treemonitor.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by admin on 2017/3/2.
 */
@Entity(indexes = {
        @Index(value = "id, TreeId DESC", unique = true)
})
public class Tree {
    @Id
    private long id;
    @Unique
    private String TreeId;     //古树编号  -----unique
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
    private String Owner;     //权属
    private String Level;     //古树等级
    private String Growth;     //生长势
    private String Environment;     //生长环境
    private String Status;     //现存状态
    private double RealAge;     //真实树龄
    private double GuessAge;     //估测树龄
    private double Evevation;     //海拔
    private String Aspect;     //坡向
    private String Slope;     //坡度
    private String SlopePos;     //坡位
    private String Soil;     //土壤名称
    private String EnviorFactor;     //影响生长环境因素
    private String SpecStatDesc;     //树木特殊状况描述
    private String SpecDesc;     //树种鉴定记载
    private String Explain;     //照片及说明
    private String Protected;     //地上保护现状
    private String Recovery;     //养护复壮现状
    private String Remark;  //备注

    @Generated(hash = 1199086578)
    public Tree(long id, String TreeId, String Ivst, String CHName, String Alias,
                String LatinName, String Family, String Belong, String Town,
                String Village, String SmallName, double Ordinate, double Abscissa,
                String SpecialCode, double TreeHeight, double TreeDBH, double CrownAvg,
                double CrownEW, double CrownNS, String ManagementUnit,
                String ManagementPersion, String TreeHistory, String GrownSpace,
                String Special, String Owner, String Level, String Growth,
                String Environment, String Status, double RealAge, double GuessAge,
                double Evevation, String Aspect, String Slope, String SlopePos,
                String Soil, String EnviorFactor, String SpecStatDesc, String SpecDesc,
                String Explain, String Protected, String Recovery, String Remark) {
        this.id = id;
        this.TreeId = TreeId;
        this.Ivst = Ivst;
        this.CHName = CHName;
        this.Alias = Alias;
        this.LatinName = LatinName;
        this.Family = Family;
        this.Belong = Belong;
        this.Town = Town;
        this.Village = Village;
        this.SmallName = SmallName;
        this.Ordinate = Ordinate;
        this.Abscissa = Abscissa;
        this.SpecialCode = SpecialCode;
        this.TreeHeight = TreeHeight;
        this.TreeDBH = TreeDBH;
        this.CrownAvg = CrownAvg;
        this.CrownEW = CrownEW;
        this.CrownNS = CrownNS;
        this.ManagementUnit = ManagementUnit;
        this.ManagementPersion = ManagementPersion;
        this.TreeHistory = TreeHistory;
        this.GrownSpace = GrownSpace;
        this.Special = Special;
        this.Owner = Owner;
        this.Level = Level;
        this.Growth = Growth;
        this.Environment = Environment;
        this.Status = Status;
        this.RealAge = RealAge;
        this.GuessAge = GuessAge;
        this.Evevation = Evevation;
        this.Aspect = Aspect;
        this.Slope = Slope;
        this.SlopePos = SlopePos;
        this.Soil = Soil;
        this.EnviorFactor = EnviorFactor;
        this.SpecStatDesc = SpecStatDesc;
        this.SpecDesc = SpecDesc;
        this.Explain = Explain;
        this.Protected = Protected;
        this.Recovery = Recovery;
        this.Remark = Remark;
    }

    @Generated(hash = 439989092)
    public Tree() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTreeId() {
        return this.TreeId;
    }

    public void setTreeId(String TreeId) {
        this.TreeId = TreeId;
    }

    public String getIvst() {
        return this.Ivst;
    }

    public void setIvst(String Ivst) {
        this.Ivst = Ivst;
    }

    public String getCHName() {
        return this.CHName;
    }

    public void setCHName(String CHName) {
        this.CHName = CHName;
    }

    public String getAlias() {
        return this.Alias;
    }

    public void setAlias(String Alias) {
        this.Alias = Alias;
    }

    public String getLatinName() {
        return this.LatinName;
    }

    public void setLatinName(String LatinName) {
        this.LatinName = LatinName;
    }

    public String getFamily() {
        return this.Family;
    }

    public void setFamily(String Family) {
        this.Family = Family;
    }

    public String getBelong() {
        return this.Belong;
    }

    public void setBelong(String Belong) {
        this.Belong = Belong;
    }

    public String getTown() {
        return this.Town;
    }

    public void setTown(String Town) {
        this.Town = Town;
    }

    public String getVillage() {
        return this.Village;
    }

    public void setVillage(String Village) {
        this.Village = Village;
    }

    public String getSmallName() {
        return this.SmallName;
    }

    public void setSmallName(String SmallName) {
        this.SmallName = SmallName;
    }

    public double getOrdinate() {
        return this.Ordinate;
    }

    public void setOrdinate(double Ordinate) {
        this.Ordinate = Ordinate;
    }

    public double getAbscissa() {
        return this.Abscissa;
    }

    public void setAbscissa(double Abscissa) {
        this.Abscissa = Abscissa;
    }

    public String getSpecialCode() {
        return this.SpecialCode;
    }

    public void setSpecialCode(String SpecialCode) {
        this.SpecialCode = SpecialCode;
    }

    public double getTreeHeight() {
        return this.TreeHeight;
    }

    public void setTreeHeight(double TreeHeight) {
        this.TreeHeight = TreeHeight;
    }

    public double getTreeDBH() {
        return this.TreeDBH;
    }

    public void setTreeDBH(double TreeDBH) {
        this.TreeDBH = TreeDBH;
    }

    public double getCrownAvg() {
        return this.CrownAvg;
    }

    public void setCrownAvg(double CrownAvg) {
        this.CrownAvg = CrownAvg;
    }

    public double getCrownEW() {
        return this.CrownEW;
    }

    public void setCrownEW(double CrownEW) {
        this.CrownEW = CrownEW;
    }

    public double getCrownNS() {
        return this.CrownNS;
    }

    public void setCrownNS(double CrownNS) {
        this.CrownNS = CrownNS;
    }

    public String getManagementUnit() {
        return this.ManagementUnit;
    }

    public void setManagementUnit(String ManagementUnit) {
        this.ManagementUnit = ManagementUnit;
    }

    public String getManagementPersion() {
        return this.ManagementPersion;
    }

    public void setManagementPersion(String ManagementPersion) {
        this.ManagementPersion = ManagementPersion;
    }

    public String getTreeHistory() {
        return this.TreeHistory;
    }

    public void setTreeHistory(String TreeHistory) {
        this.TreeHistory = TreeHistory;
    }

    public String getGrownSpace() {
        return this.GrownSpace;
    }

    public void setGrownSpace(String GrownSpace) {
        this.GrownSpace = GrownSpace;
    }

    public String getSpecial() {
        return this.Special;
    }

    public void setSpecial(String Special) {
        this.Special = Special;
    }

    public String getOwner() {
        return this.Owner;
    }

    public void setOwner(String Owner) {
        this.Owner = Owner;
    }

    public String getLevel() {
        return this.Level;
    }

    public void setLevel(String Level) {
        this.Level = Level;
    }

    public String getGrowth() {
        return this.Growth;
    }

    public void setGrowth(String Growth) {
        this.Growth = Growth;
    }

    public String getEnvironment() {
        return this.Environment;
    }

    public void setEnvironment(String Environment) {
        this.Environment = Environment;
    }

    public String getStatus() {
        return this.Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public double getRealAge() {
        return this.RealAge;
    }

    public void setRealAge(double RealAge) {
        this.RealAge = RealAge;
    }

    public double getGuessAge() {
        return this.GuessAge;
    }

    public void setGuessAge(double GuessAge) {
        this.GuessAge = GuessAge;
    }

    public double getEvevation() {
        return this.Evevation;
    }

    public void setEvevation(double Evevation) {
        this.Evevation = Evevation;
    }

    public String getAspect() {
        return this.Aspect;
    }

    public void setAspect(String Aspect) {
        this.Aspect = Aspect;
    }

    public String getSlope() {
        return this.Slope;
    }

    public void setSlope(String Slope) {
        this.Slope = Slope;
    }

    public String getSlopePos() {
        return this.SlopePos;
    }

    public void setSlopePos(String SlopePos) {
        this.SlopePos = SlopePos;
    }

    public String getSoil() {
        return this.Soil;
    }

    public void setSoil(String Soil) {
        this.Soil = Soil;
    }

    public String getEnviorFactor() {
        return this.EnviorFactor;
    }

    public void setEnviorFactor(String EnviorFactor) {
        this.EnviorFactor = EnviorFactor;
    }

    public String getSpecStatDesc() {
        return this.SpecStatDesc;
    }

    public void setSpecStatDesc(String SpecStatDesc) {
        this.SpecStatDesc = SpecStatDesc;
    }

    public String getSpecDesc() {
        return this.SpecDesc;
    }

    public void setSpecDesc(String SpecDesc) {
        this.SpecDesc = SpecDesc;
    }

    public String getExplain() {
        return this.Explain;
    }

    public void setExplain(String Explain) {
        this.Explain = Explain;
    }

    public String getProtected() {
        return this.Protected;
    }

    public void setProtected(String Protected) {
        this.Protected = Protected;
    }

    public String getRecovery() {
        return this.Recovery;
    }

    public void setRecovery(String Recovery) {
        this.Recovery = Recovery;
    }

    public String getRemark() {
        return this.Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }
}
