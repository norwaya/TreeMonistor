package com.xabaizhong.treemonistor.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Transient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(indexes = {
        @Index(value = "id DESC", unique = true)
})
public class TreeGroup {
    @Id
    private Long id;
    @Expose
    private String treeId = "";
    @Expose
    private String placeName = "";           //	地点
    @Expose
    private String mainTreeName = "";   //	主要树种
    @Expose
    private String SZJX = ""; //	四至界限
    @Expose
    private double area = 0.0; //	面积
    @Expose
    private double gSTreeNum = 0.0;//	古树株数
    @Expose
    private double averageHeight = 0.0; //	平均高度
    @Expose
    private double averageDiameter = 0.0;//	平均胸径
    @Expose
    private double averageAge = 0.0;//	平均年龄
    @Expose
    private double yBDInfo = 0.0; //	郁闭度
    @Expose
    private String evevation = ""; //	海拔
    @Expose
    private String aspect = ""; //	坡向
    @Expose
    private double slope = 0.0;//	坡度
    @Expose
    private String soilName = "";//	土壤名称
    @Expose
    private double soilHeight = 0.0;//	土壤厚度
    @Expose
    private String xiaMuType = ""; //	下木种类
    @Expose
    private double xiaMuDensity = 0.0;//	下木密度
    @Expose
    private String dBWType = "";//	地被物种类
    @Expose
    private double dBWDensity = 0.0;//	地被物密度
    @Expose
    private String managementUnit = "";//	管护单位
    @Expose
    private String managementState = "";//	管护现状
    @Expose
    private String aimsTree = "";//	目的保护树种
    @Expose
    private String aimsFamily = "";//	科
    @Expose
    private String aimsBelong = "";//	属
    @Expose
    private String rWJYInfo = ""; //	人为经营活动情况
    @Expose
    private String suggest = "";//保护建议
    @Expose
    private String explain = "";//	照片及说明
    @Expose
    private Date date;

    @ToMany(referencedJoinProperty = "treeGroup_id")
    private List<TreeMap> treeMaps;

    @Expose
    @Transient
    private List<Map<String, Object>> treeMap;
    public List<String> getPicList() {
        return picList;
    }
    @Expose
    @Transient
    @SerializedName("piclist")
    private List<String> picList = new ArrayList<>();

    @Expose
    private String UserID;

    @Expose(serialize = false, deserialize = false)
    @ToMany(referencedJoinProperty = "tree_id")
    private List<TreeGroupPic> Pics;
    @Expose
    private String region="";
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 153211498)
    private transient TreeGroupDao myDao;

    @Generated(hash = 976340294)
    public TreeGroup(Long id, String treeId, String placeName, String mainTreeName,
                     String SZJX, double area, double gSTreeNum, double averageHeight,
                     double averageDiameter, double averageAge, double yBDInfo,
                     String evevation, String aspect, double slope, String soilName,
                     double soilHeight, String xiaMuType, double xiaMuDensity,
                     String dBWType, double dBWDensity, String managementUnit,
                     String managementState, String aimsTree, String aimsFamily,
                     String aimsBelong, String rWJYInfo, String suggest, String explain,
                     Date date, String UserID, String region) {
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
        this.date = date;
        this.UserID = UserID;
        this.region = region;
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

    public double getArea() {
        return this.area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getGSTreeNum() {
        return this.gSTreeNum;
    }

    public void setGSTreeNum(double gSTreeNum) {
        this.gSTreeNum = gSTreeNum;
    }

    public double getAverageHeight() {
        return this.averageHeight;
    }

    public void setAverageHeight(double averageHeight) {
        this.averageHeight = averageHeight;
    }

    public double getAverageDiameter() {
        return this.averageDiameter;
    }

    public void setAverageDiameter(double averageDiameter) {
        this.averageDiameter = averageDiameter;
    }

    public double getAverageAge() {
        return this.averageAge;
    }

    public void setAverageAge(double averageAge) {
        this.averageAge = averageAge;
    }

    public double getYBDInfo() {
        return this.yBDInfo;
    }

    public void setYBDInfo(double yBDInfo) {
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

    public double getSlope() {
        return this.slope;
    }

    public void setSlope(double slope) {
        this.slope = slope;
    }

    public String getSoilName() {
        return this.soilName;
    }

    public void setSoilName(String soilName) {
        this.soilName = soilName;
    }

    public double getSoilHeight() {
        return this.soilHeight;
    }

    public void setSoilHeight(double soilHeight) {
        this.soilHeight = soilHeight;
    }

    public String getXiaMuType() {
        return this.xiaMuType;
    }

    public void setXiaMuType(String xiaMuType) {
        this.xiaMuType = xiaMuType;
    }

    public double getXiaMuDensity() {
        return this.xiaMuDensity;
    }

    public void setXiaMuDensity(double xiaMuDensity) {
        this.xiaMuDensity = xiaMuDensity;
    }

    public String getDBWType() {
        return this.dBWType;
    }

    public void setDBWType(String dBWType) {
        this.dBWType = dBWType;
    }

    public double getDBWDensity() {
        return this.dBWDensity;
    }

    public void setDBWDensity(double dBWDensity) {
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

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserID() {
        return this.UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 601427714)
    public List<TreeMap> getTreeMaps() {
        if (treeMaps == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TreeMapDao targetDao = daoSession.getTreeMapDao();
            List<TreeMap> treeMapsNew = targetDao._queryTreeGroup_TreeMaps(id);
            synchronized (this) {
                if (treeMaps == null) {
                    treeMaps = treeMapsNew;
                }
            }
        }
        return treeMaps;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 946287398)
    public synchronized void resetTreeMaps() {
        treeMaps = null;
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

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1347196558)
    public List<TreeGroupPic> getPics() {
        if (Pics == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TreeGroupPicDao targetDao = daoSession.getTreeGroupPicDao();
            List<TreeGroupPic> PicsNew = targetDao._queryTreeGroup_Pics(id);
            synchronized (this) {
                if (Pics == null) {
                    Pics = PicsNew;
                }
            }
        }
        return Pics;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 1912518224)
    public synchronized void resetPics() {
        Pics = null;
    }

    public List<Map<String, Object>> getTreeMap() {
        return treeMap;
    }

    public void setTreeMap(List<Map<String, Object>> treeMap) {
        this.treeMap = treeMap;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 961300266)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTreeGroupDao() : null;
    }
}