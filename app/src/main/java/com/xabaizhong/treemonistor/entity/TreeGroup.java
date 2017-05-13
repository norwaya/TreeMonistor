package com.xabaizhong.treemonistor.entity;


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
    private String treeId = "";
    private String placeName = "";           //	地点
    private String mainTreeName = "";   //	主要树种
    private String SZJX = ""; //	四至界限
    private double area = 0.0; //	面积
    private double gSTreeNum = 0.0;//	古树株数
    private double averageHeight = 0.0; //	平均高度
    private double averageDiameter = 0.0;//	平均胸径
    private double averageAge = 0.0;//	平均年龄
    private double yBDInfo = 0.0; //	郁闭度
    private String evevation = ""; //	海拔
    private String aspect = ""; //	坡向
    private double slope = 0.0;//	坡度
    private String soilName = "";//	土壤名称
    private double soilHeight = 0.0;//	土壤厚度
    private String xiaMuType = ""; //	下木种类
    private double xiaMuDensity = 0.0;//	下木密度
    private String dBWType = "";//	地被物种类
    private double dBWDensity = 0.0;//	地被物密度
    private String managementUnit = "";//	管护单位
    private String managementState = "";//	管护现状
    private String aimsTree = "";//	目的保护树种
    private String aimsFamily = "";//	科
    private String aimsBelong = "";//	属
    private String rWJYInfo = ""; //	人为经营活动情况
    private String suggest = "";//保护建议
    private String explain = "";//	照片及说明
    private Date date;

    @ToMany(referencedJoinProperty = "treeGroup_id")
    private List<TreeMap> treeMaps;

    @Transient
    private List<Map<String, Object>> treeMap;
    @SerializedName("piclist")
    @Transient
    private List<String> picList = new ArrayList<>();
    private String UserID;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 153211498)
    private transient TreeGroupDao myDao;

    @Generated(hash = 1293141976)
    public TreeGroup(Long id, String treeId, String placeName, String mainTreeName,
            String SZJX, double area, double gSTreeNum, double averageHeight,
            double averageDiameter, double averageAge, double yBDInfo,
            String evevation, String aspect, double slope, String soilName,
            double soilHeight, String xiaMuType, double xiaMuDensity,
            String dBWType, double dBWDensity, String managementUnit,
            String managementState, String aimsTree, String aimsFamily,
            String aimsBelong, String rWJYInfo, String suggest, String explain,
            Date date, String UserID) {
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
    }

    @Generated(hash = 1161991029)
    public TreeGroup() {
    }

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


    public String getdBWType() {
        return dBWType;
    }

    public void setdBWType(String dBWType) {
        this.dBWType = dBWType;
    }

    public double getdBWDensity() {
        return dBWDensity;
    }

    public void setdBWDensity(double dBWDensity) {
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

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getgSTreeNum() {
        return gSTreeNum;
    }

    public void setgSTreeNum(double gSTreeNum) {
        this.gSTreeNum = gSTreeNum;
    }

    public double getAverageHeight() {
        return averageHeight;
    }

    public void setAverageHeight(double averageHeight) {
        this.averageHeight = averageHeight;
    }

    public double getAverageDiameter() {
        return averageDiameter;
    }

    public void setAverageDiameter(double averageDiameter) {
        this.averageDiameter = averageDiameter;
    }

    public double getAverageAge() {
        return averageAge;
    }

    public void setAverageAge(double averageAge) {
        this.averageAge = averageAge;
    }

    public double getyBDInfo() {
        return yBDInfo;
    }

    public void setyBDInfo(double yBDInfo) {
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

    public double getSlope() {
        return slope;
    }

    public void setSlope(double slope) {
        this.slope = slope;
    }

    public String getSoilName() {
        return soilName;
    }

    public void setSoilName(String soilName) {
        this.soilName = soilName;
    }

    public double getSoilHeight() {
        return soilHeight;
    }

    public void setSoilHeight(double soilHeight) {
        this.soilHeight = soilHeight;
    }

    public String getXiaMuType() {
        return xiaMuType;
    }

    public void setXiaMuType(String xiaMuType) {
        this.xiaMuType = xiaMuType;
    }

    public double getXiaMuDensity() {
        return xiaMuDensity;
    }

    public void setXiaMuDensity(double xiaMuDensity) {
        this.xiaMuDensity = xiaMuDensity;
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

    public double getGSTreeNum() {
        return this.gSTreeNum;
    }

    public void setGSTreeNum(double gSTreeNum) {
        this.gSTreeNum = gSTreeNum;
    }

    public double getYBDInfo() {
        return this.yBDInfo;
    }

    public void setYBDInfo(double yBDInfo) {
        this.yBDInfo = yBDInfo;
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

    public String getRWJYInfo() {
        return this.rWJYInfo;
    }

    public void setRWJYInfo(String rWJYInfo) {
        this.rWJYInfo = rWJYInfo;
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

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
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

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 961300266)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTreeGroupDao() : null;
    }
}