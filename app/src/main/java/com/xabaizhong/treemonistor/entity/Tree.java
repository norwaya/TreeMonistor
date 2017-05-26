package com.xabaizhong.treemonistor.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Transient;

import java.util.Date;
import java.util.List;

import static com.xabaizhong.treemonistor.utils.InputVerification.isNull;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(indexes = {
        @Index(value = "id DESC", unique = true)
})
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
    @Id
    private Long id;
    @Expose
    @SerializedName("UserID")
    private String UserID;
    @Expose
    @SerializedName("abscissa")
    private String abscissa;
    @Expose
    @SerializedName("aspect")
    private String aspect;
    @Expose
    @SerializedName("crownAvg")
    private double crownAvg = 0.0;
    @Expose
    @SerializedName("crownEW")
    private double crownEW = 0.0;
    @Expose
    @SerializedName("crownNS")
    private double crownNS = 0.0;
    @Expose
    @SerializedName("date")
    private Date date = new Date();
    @Expose
    @SerializedName("enviorFactor")
    private String enviorFactor = "";
    @Expose
    @SerializedName("enviorment")
    private String enviorment = "";
    @Expose
    @SerializedName("evevation")
    private double evevation = 0.0;
    @Expose
    @SerializedName("grownSpace")
    private String grownSpace = "";
    @Expose
    @SerializedName("growth")
    private String growth = "";
    @Expose
    @SerializedName("guessAge")
    private double guessAge = 0.0;
    @Expose
    @SerializedName("managementPersion")
    private String managementPersion = "";
    @Expose
    @SerializedName("managementUnit")
    private String managementUnit = "";
    @Expose
    @SerializedName("ordinate")
    private String ordinate = "";
    @Expose
    @SerializedName("owner")
    private String owner = "";
    @Expose
    @SerializedName("protecte")
    private String protecte = "";
    @Expose
    @SerializedName("realAge")
    private double realAge = 0.0;
    @Expose
    @SerializedName("recovery")
    private String recovery = "";
    @Expose
    @SerializedName("slope")
    private String slope = "";
    @Expose
    @SerializedName("slopePos")
    private String slopePos = "";
    @Expose
    @SerializedName("smallName")
    private String smallName = "";
    @Expose
    @SerializedName("soil")
    private String soil = "";
    @Expose
    @SerializedName("specDesc")
    private String specDesc = "";
    @Expose
    @SerializedName("specStatDesc")
    private String specStatDesc = "";
    @Expose
    @SerializedName("special")
    private String special = "";
    @Expose
    @SerializedName("treeDBH")
    private double treeDBH = 0.0;
    @Expose
    @SerializedName("treeHeight")
    private double treeHeight = 0.0;
    @Expose
    @SerializedName("treeHistory")
    private String treeHistory = "";
    @Expose
    @SerializedName("treeId")
    private String treeId = "";
    @Expose
    @SerializedName("treeLevel")
    private String treeLevel = "";
    @Expose
    @SerializedName("treespeid")
    private String treespeid = "";
    @Expose
    @SerializedName("treeStatus")
    private String treeStatus = "";
    @Expose
    @SerializedName("treearea")
    private int treearea = 0;
    @Expose
    @SerializedName("treetype")
    private int treetype = 0;
    @Expose
    private String town = "";
    @Expose
    private String village = "";
    @Expose
    private String explain = "";
    @Expose
    private double specialCode = 0;
    @Expose
    private String region;

    @Expose
    @ToMany(referencedJoinProperty = "tree_id")
    @OrderBy("id ASC")
    private List<TreePic> pics;

    @Expose
    @Transient
    @SerializedName("piclist")
    private List<String> piclist;
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

    @Generated(hash = 1213388059)
    public Tree(Long id, String UserID, String abscissa, String aspect,
                double crownAvg, double crownEW, double crownNS, Date date,
                String enviorFactor, String enviorment, double evevation,
                String grownSpace, String growth, double guessAge,
                String managementPersion, String managementUnit, String ordinate,
                String owner, String protecte, double realAge, String recovery,
                String slope, String slopePos, String smallName, String soil,
                String specDesc, String specStatDesc, String special, double treeDBH,
                double treeHeight, String treeHistory, String treeId, String treeLevel,
                String treespeid, String treeStatus, int treearea, int treetype,
                String town, String village, String explain, double specialCode,
                String region) {
        this.id = id;
        this.UserID = UserID;
        this.abscissa = abscissa;
        this.aspect = aspect;
        this.crownAvg = crownAvg;
        this.crownEW = crownEW;
        this.crownNS = crownNS;
        this.date = date;
        this.enviorFactor = enviorFactor;
        this.enviorment = enviorment;
        this.evevation = evevation;
        this.grownSpace = grownSpace;
        this.growth = growth;
        this.guessAge = guessAge;
        this.managementPersion = managementPersion;
        this.managementUnit = managementUnit;
        this.ordinate = ordinate;
        this.owner = owner;
        this.protecte = protecte;
        this.realAge = realAge;
        this.recovery = recovery;
        this.slope = slope;
        this.slopePos = slopePos;
        this.smallName = smallName;
        this.soil = soil;
        this.specDesc = specDesc;
        this.specStatDesc = specStatDesc;
        this.special = special;
        this.treeDBH = treeDBH;
        this.treeHeight = treeHeight;
        this.treeHistory = treeHistory;
        this.treeId = treeId;
        this.treeLevel = treeLevel;
        this.treespeid = treespeid;
        this.treeStatus = treeStatus;
        this.treearea = treearea;
        this.treetype = treetype;
        this.town = town;
        this.village = village;
        this.explain = explain;
        this.specialCode = specialCode;
        this.region = region;
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


    public String getUserID() {
        return this.UserID;
    }


    public void setUserID(String UserID) {
        this.UserID = UserID;
    }


    public String getAbscissa() {
        return this.abscissa;
    }


    public void setAbscissa(String abscissa) {
        this.abscissa = abscissa;
    }


    public String getAspect() {
        return this.aspect;
    }


    public void setAspect(String aspect) {
        this.aspect = aspect;
    }


    public double getCrownAvg() {
        return this.crownAvg;
    }


    public void setCrownAvg(double crownAvg) {
        this.crownAvg = crownAvg;
    }


    public double getCrownEW() {
        return this.crownEW;
    }


    public void setCrownEW(double crownEW) {
        this.crownEW = crownEW;
    }


    public double getCrownNS() {
        return this.crownNS;
    }


    public void setCrownNS(double crownNS) {
        this.crownNS = crownNS;
    }


    public Date getDate() {
        return this.date;
    }


    public void setDate(Date date) {
        this.date = date;
    }


    public String getEnviorFactor() {
        return this.enviorFactor;
    }


    public void setEnviorFactor(String enviorFactor) {
        this.enviorFactor = enviorFactor;
    }


    public String getEnviorment() {
        return this.enviorment;
    }


    public void setEnviorment(String enviorment) {
        this.enviorment = enviorment;
    }


    public double getEvevation() {
        return this.evevation;
    }


    public void setEvevation(double evevation) {
        this.evevation = evevation;
    }


    public String getGrownSpace() {
        return this.grownSpace;
    }


    public void setGrownSpace(String grownSpace) {
        this.grownSpace = grownSpace;
    }


    public String getGrowth() {
        return this.growth;
    }


    public void setGrowth(String growth) {
        this.growth = growth;
    }


    public double getGuessAge() {
        return this.guessAge;
    }


    public void setGuessAge(double guessAge) {
        this.guessAge = guessAge;
    }


    public String getManagementPersion() {
        return this.managementPersion;
    }


    public void setManagementPersion(String managementPersion) {
        this.managementPersion = managementPersion;
    }


    public String getManagementUnit() {
        return this.managementUnit;
    }


    public void setManagementUnit(String managementUnit) {
        this.managementUnit = managementUnit;
    }


    public String getOrdinate() {
        return this.ordinate;
    }


    public void setOrdinate(String ordinate) {
        this.ordinate = ordinate;
    }


    public String getOwner() {
        return this.owner;
    }


    public void setOwner(String owner) {
        this.owner = owner;
    }


    public String getProtecte() {
        return this.protecte;
    }


    public void setProtecte(String protecte) {
        this.protecte = protecte;
    }


    public double getRealAge() {
        return this.realAge;
    }


    public void setRealAge(double realAge) {
        this.realAge = realAge;
    }


    public String getRecovery() {
        return this.recovery;
    }


    public void setRecovery(String recovery) {
        this.recovery = recovery;
    }


    public String getSlope() {
        return this.slope;
    }


    public void setSlope(String slope) {
        this.slope = slope;
    }


    public String getSlopePos() {
        return this.slopePos;
    }


    public void setSlopePos(String slopePos) {
        this.slopePos = slopePos;
    }


    public String getSmallName() {
        return this.smallName;
    }


    public void setSmallName(String smallName) {
        this.smallName = smallName;
    }


    public String getSoil() {
        return this.soil;
    }


    public void setSoil(String soil) {
        this.soil = soil;
    }


    public String getSpecDesc() {
        return this.specDesc;
    }


    public void setSpecDesc(String specDesc) {
        this.specDesc = specDesc;
    }


    public String getSpecStatDesc() {
        return this.specStatDesc;
    }


    public void setSpecStatDesc(String specStatDesc) {
        this.specStatDesc = specStatDesc;
    }


    public String getSpecial() {
        return this.special;
    }


    public void setSpecial(String special) {
        this.special = special;
    }


    public double getTreeDBH() {
        return this.treeDBH;
    }


    public void setTreeDBH(double treeDBH) {
        this.treeDBH = treeDBH;
    }


    public double getTreeHeight() {
        return this.treeHeight;
    }


    public void setTreeHeight(double treeHeight) {
        this.treeHeight = treeHeight;
    }


    public String getTreeHistory() {
        return this.treeHistory;
    }


    public void setTreeHistory(String treeHistory) {
        this.treeHistory = treeHistory;
    }


    public String getTreeId() {
        return this.treeId;
    }


    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }


    public String getTreeLevel() {
        return this.treeLevel;
    }


    public void setTreeLevel(String treeLevel) {
        this.treeLevel = treeLevel;
    }


    public String getTreespeid() {
        return this.treespeid;
    }


    public void setTreespeid(String treespeid) {
        this.treespeid = treespeid;
    }


    public String getTreeStatus() {
        return this.treeStatus;
    }


    public void setTreeStatus(String treeStatus) {
        this.treeStatus = treeStatus;
    }


    public int getTreearea() {
        return this.treearea;
    }


    public void setTreearea(int treearea) {
        this.treearea = treearea;
    }


    public int getTreetype() {
        return this.treetype;
    }


    public void setTreetype(int treetype) {
        this.treetype = treetype;
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


    public String getExplain() {
        return this.explain;
    }


    public void setExplain(String explain) {
        this.explain = explain;
    }


    public double getSpecialCode() {
        return this.specialCode;
    }


    public void setSpecialCode(double specialCode) {
        this.specialCode = specialCode;
    }


    public String getRegion() {
        return this.region;
    }


    public void setRegion(String region) {
        this.region = region;
    }


    public List<String> getPiclist() {
        return piclist;
    }

    public void setPiclist(List<String> piclist) {
        this.piclist = piclist;
    }


    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 123928770)
    public List<TreePic> getPics() {
        if (pics == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TreePicDao targetDao = daoSession.getTreePicDao();
            List<TreePic> picsNew = targetDao._queryTree_Pics(id);
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
