package com.xabaizhong.treemonistor.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import java.util.Date;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by admin on 2017/3/13.
 */
@Entity(indexes = {
        @Index(value = "id, treeId DESC", unique = true)
})
public class TreeTypeInfo {
    @Id
    private long id;
    private String treeId;
    private String ivst;
    private String typeId;
    private String recoredPerson;
    private Date date;
    private String areaId;
    @Expose
    private long treeID;
    @Expose
    @ToOne(joinProperty = "treeID")
    private Tree tree;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 747255772)
    private transient TreeTypeInfoDao myDao;
    @Generated(hash = 504113244)
    private transient Long tree__resolvedKey;


    @Generated(hash = 826155680)
    public TreeTypeInfo(long id, String treeId, String ivst, String typeId,
            String recoredPerson, Date date, String areaId, long treeID) {
        this.id = id;
        this.treeId = treeId;
        this.ivst = ivst;
        this.typeId = typeId;
        this.recoredPerson = recoredPerson;
        this.date = date;
        this.areaId = areaId;
        this.treeID = treeID;
    }

    @Generated(hash = 1444053980)
    public TreeTypeInfo() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTreeId() {
        return this.treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    public String getIvst() {
        return this.ivst;
    }

    public void setIvst(String ivst) {
        this.ivst = ivst;
    }

    public String getTypeId() {
        return this.typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getRecoredPerson() {
        return this.recoredPerson;
    }

    public void setRecoredPerson(String recoredPerson) {
        this.recoredPerson = recoredPerson;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAreaId() {
        return this.areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public long getTreeID() {
        return this.treeID;
    }

    public void setTreeID(long treeID) {
        this.treeID = treeID;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 2005549894)
    public Tree getTree() {
        long __key = this.treeID;
        if (tree__resolvedKey == null || !tree__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TreeDao targetDao = daoSession.getTreeDao();
            Tree treeNew = targetDao.load(__key);
            synchronized (this) {
                tree = treeNew;
                tree__resolvedKey = __key;
            }
        }
        return tree;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 384253357)
    public void setTree(@NotNull Tree tree) {
        if (tree == null) {
            throw new DaoException(
                    "To-one property 'treeID' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.tree = tree;
            treeID = tree.getId();
            tree__resolvedKey = treeID;
        }
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
    @Generated(hash = 1186362298)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTreeTypeInfoDao() : null;
    }

}
