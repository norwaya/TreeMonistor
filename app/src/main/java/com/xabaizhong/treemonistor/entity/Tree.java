package com.xabaizhong.treemonistor.entity;

import android.util.Log;

import com.google.gson.annotations.Expose;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

import java.util.List;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by admin on 2017/3/2.
 */
@Entity(indexes = {
        @Index(value = "id, treeId DESC", unique = true)
})
public class Tree {
    @Id
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
     private String Remark;  //备注*/
    private String treeId;  //id  √
    private String treeType;//古树标志
    private String treeArea;//农村  城市
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
    private String Aspect;//
    private String Slope;//
    private String SlopePos;//
    private String Soil;//
    private String enviorFactor;//
    private String specStatDesc;//树木特殊状况描述
    private String specDesc;//树种鉴定记载
    private String explain;//old  pic
    private String protecte;//保护现状
    private String recovery;//养护
    private String owner;//归属
    private String treeLevel;//等级
    private String treeStatus;//状态

    private long treeSpecialId;

    @ToOne(joinProperty = "treeSpecialId")
    private TreeSpecial treeSpecial;

    @Expose
    @ToMany(referencedJoinProperty = "treeId")//treeId 指的是id  主键
    @OrderBy("id ASC")
    private List<Pic> pics;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 948839816)
    private transient TreeDao myDao;

    @Generated(hash = 1102234020)
    public Tree(Long id, String treeId, String treeType, String treeArea,
                String treeSpeID, String town, String village, String smallName,
                String ordinate, String abscissa, String specialCode, String treeHeight,
                String treeDBH, String crownAvg, String crownEW, String crownNS,
                String managementUnit, String managementPersion, String treeHistory,
                String grownSpace, String special, String growth, String enviorment,
                String realAge, String guessAge, String evevation, String Aspect,
                String Slope, String SlopePos, String Soil, String enviorFactor,
                String specStatDesc, String specDesc, String explain, String protecte,
                String recovery, String owner, String treeLevel, String treeStatus,
                long treeSpecialId) {
        this.id = id;
        this.treeId = treeId;
        this.treeType = treeType;
        this.treeArea = treeArea;
        this.treeSpeID = treeSpeID;
        this.town = town;
        this.village = village;
        this.smallName = smallName;
        this.ordinate = ordinate;
        this.abscissa = abscissa;
        this.specialCode = specialCode;
        this.treeHeight = treeHeight;
        this.treeDBH = treeDBH;
        this.crownAvg = crownAvg;
        this.crownEW = crownEW;
        this.crownNS = crownNS;
        this.managementUnit = managementUnit;
        this.managementPersion = managementPersion;
        this.treeHistory = treeHistory;
        this.grownSpace = grownSpace;
        this.special = special;
        this.growth = growth;
        this.enviorment = enviorment;
        this.realAge = realAge;
        this.guessAge = guessAge;
        this.evevation = evevation;
        this.Aspect = Aspect;
        this.Slope = Slope;
        this.SlopePos = SlopePos;
        this.Soil = Soil;
        this.enviorFactor = enviorFactor;
        this.specStatDesc = specStatDesc;
        this.specDesc = specDesc;
        this.explain = explain;
        this.protecte = protecte;
        this.recovery = recovery;
        this.owner = owner;
        this.treeLevel = treeLevel;
        this.treeStatus = treeStatus;
        this.treeSpecialId = treeSpecialId;
    }

    @Generated(hash = 439989092)
    public Tree() {
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

    public String getTreeType() {
        return this.treeType;
    }

    public void setTreeType(String treeType) {
        this.treeType = treeType;
    }

    public String getTreeArea() {
        return this.treeArea;
    }

    public void setTreeArea(String treeArea) {
        this.treeArea = treeArea;
    }

    public String getTreeSpeID() {
        return this.treeSpeID;
    }

    public void setTreeSpeID(String treeSpeID) {
        this.treeSpeID = treeSpeID;
    }

    public String getTown() {
        return this.town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getVillage() {
        return this.village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getSmallName() {
        return this.smallName;
    }

    public void setSmallName(String smallName) {
        this.smallName = smallName;
    }

    public String getOrdinate() {
        return this.ordinate;
    }

    public void setOrdinate(String ordinate) {
        this.ordinate = ordinate;
    }

    public String getAbscissa() {
        return this.abscissa;
    }

    public void setAbscissa(String abscissa) {
        this.abscissa = abscissa;
    }

    public String getSpecialCode() {
        return this.specialCode;
    }

    public void setSpecialCode(String specialCode) {
        this.specialCode = specialCode;
    }

    public String getTreeHeight() {
        return this.treeHeight;
    }

    public void setTreeHeight(String treeHeight) {
        this.treeHeight = treeHeight;
    }

    public String getTreeDBH() {
        return this.treeDBH;
    }

    public void setTreeDBH(String treeDBH) {
        this.treeDBH = treeDBH;
    }

    public String getCrownAvg() {
        return this.crownAvg;
    }

    public void setCrownAvg(String crownAvg) {
        this.crownAvg = crownAvg;
    }

    public String getCrownEW() {
        return this.crownEW;
    }

    public void setCrownEW(String crownEW) {
        this.crownEW = crownEW;
    }

    public String getCrownNS() {
        return this.crownNS;
    }

    public void setCrownNS(String crownNS) {
        this.crownNS = crownNS;
    }

    public String getManagementUnit() {
        return this.managementUnit;
    }

    public void setManagementUnit(String managementUnit) {
        this.managementUnit = managementUnit;
    }

    public String getManagementPersion() {
        return this.managementPersion;
    }

    public void setManagementPersion(String managementPersion) {
        this.managementPersion = managementPersion;
    }

    public String getTreeHistory() {
        return this.treeHistory;
    }

    public void setTreeHistory(String treeHistory) {
        this.treeHistory = treeHistory;
    }

    public String getGrownSpace() {
        return this.grownSpace;
    }

    public void setGrownSpace(String grownSpace) {
        this.grownSpace = grownSpace;
    }

    public String getSpecial() {
        return this.special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getGrowth() {
        return this.growth;
    }

    public void setGrowth(String growth) {
        this.growth = growth;
    }

    public String getEnviorment() {
        return this.enviorment;
    }

    public void setEnviorment(String enviorment) {
        this.enviorment = enviorment;
    }

    public String getRealAge() {
        return this.realAge;
    }

    public void setRealAge(String realAge) {
        this.realAge = realAge;
    }

    public String getGuessAge() {
        return this.guessAge;
    }

    public void setGuessAge(String guessAge) {
        this.guessAge = guessAge;
    }

    public String getEvevation() {
        return this.evevation;
    }

    public void setEvevation(String evevation) {
        this.evevation = evevation;
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
        return this.enviorFactor;
    }

    public void setEnviorFactor(String enviorFactor) {
        this.enviorFactor = enviorFactor;
    }

    public String getSpecStatDesc() {
        return this.specStatDesc;
    }

    public void setSpecStatDesc(String specStatDesc) {
        this.specStatDesc = specStatDesc;
    }

    public String getSpecDesc() {
        return this.specDesc;
    }

    public void setSpecDesc(String specDesc) {
        this.specDesc = specDesc;
    }

    public String getExplain() {
        return this.explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getProtecte() {
        return this.protecte;
    }

    public void setProtecte(String protecte) {
        this.protecte = protecte;
    }

    public String getRecovery() {
        return this.recovery;
    }

    public void setRecovery(String recovery) {
        this.recovery = recovery;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTreeLevel() {
        return this.treeLevel;
    }

    public void setTreeLevel(String treeLevel) {
        this.treeLevel = treeLevel;
    }

    public String getTreeStatus() {
        return this.treeStatus;
    }

    public void setTreeStatus(String treeStatus) {
        this.treeStatus = treeStatus;
    }

    public long getTreeSpecialId() {
        return this.treeSpecialId;
    }

    public void setTreeSpecialId(long treeSpecialId) {
        this.treeSpecialId = treeSpecialId;
    }

    @Generated(hash = 2091176287)
    private transient Long treeSpecial__resolvedKey;

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 1272969414)
    public TreeSpecial getTreeSpecial() {
        long __key = this.treeSpecialId;
        if (treeSpecial__resolvedKey == null
                || !treeSpecial__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TreeSpecialDao targetDao = daoSession.getTreeSpecialDao();
            TreeSpecial treeSpecialNew = targetDao.load(__key);
            synchronized (this) {
                treeSpecial = treeSpecialNew;
                treeSpecial__resolvedKey = __key;
            }
        }
        return treeSpecial;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1668721663)
    public void setTreeSpecial(@NotNull TreeSpecial treeSpecial) {
        if (treeSpecial == null) {
            throw new DaoException(
                    "To-one property 'treeSpecialId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.treeSpecial = treeSpecial;
            treeSpecialId = treeSpecial.getId();
            treeSpecial__resolvedKey = treeSpecialId;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 612066960)
    public List<Pic> getPics() {
        if (pics == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PicDao targetDao = daoSession.getPicDao();
            List<Pic> picsNew = targetDao._queryTree_Pics(id);
            synchronized (this) {
                if (pics == null) {
                    pics = picsNew;
                }
            }
        }
        return pics;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 582493596)
    public synchronized void resetPics() {
        pics = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }


    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1935359770)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTreeDao() : null;
    }

}
