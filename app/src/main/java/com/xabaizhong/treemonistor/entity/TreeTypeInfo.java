package com.xabaizhong.treemonistor.entity;

import com.google.gson.annotations.Expose;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by admin on 2017/3/13.
 */
@Entity(indexes = {
        @Index(value = "id DESC", unique = true)
})
public class TreeTypeInfo {
    @Id
    private Long id;
    private String ivst;
    private String typeId;  // 0 古树 1 古树群
    private String recoredPerson;
    private Date date;
    private String areaId;
    private long gsTree;// tree  id  属相  not  treeId

    @ToOne(joinProperty = "gsTree")
    public Tree tree;

    @ToOne(joinProperty = "gsTree")
    public TreeGroup treeGroup;
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 747255772)
    private transient TreeTypeInfoDao myDao;

    @Generated(hash = 869834564)
    public TreeTypeInfo(Long id, String ivst, String typeId, String recoredPerson,
                        Date date, String areaId, long gsTree) {
        this.id = id;
        this.ivst = ivst;
        this.typeId = typeId;
        this.recoredPerson = recoredPerson;
        this.date = date;
        this.areaId = areaId;
        this.gsTree = gsTree;
    }

    @Generated(hash = 1444053980)
    public TreeTypeInfo() {
    }

    @Generated(hash = 504113244)
    private transient Long tree__resolvedKey;
    @Generated(hash = 371016317)
    private transient Long treeGroup__resolvedKey;

    public String check() {
        return null;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public long getGsTree() {
        return this.gsTree;
    }

    public void setGsTree(long gsTree) {
        this.gsTree = gsTree;
    }

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 628870927)
    public Tree getTree() {
        long __key = this.gsTree;
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

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1396535806)
    public void setTree(@NotNull Tree tree) {
        if (tree == null) {
            throw new DaoException(
                    "To-one property 'gsTree' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.tree = tree;
            gsTree = tree.getId();
            tree__resolvedKey = gsTree;
        }
    }

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 2078446062)
    public TreeGroup getTreeGroup() {
        long __key = this.gsTree;
        if (treeGroup__resolvedKey == null
                || !treeGroup__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TreeGroupDao targetDao = daoSession.getTreeGroupDao();
            TreeGroup treeGroupNew = targetDao.load(__key);
            synchronized (this) {
                treeGroup = treeGroupNew;
                treeGroup__resolvedKey = __key;
            }
        }
        return treeGroup;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1699931499)
    public void setTreeGroup(@NotNull TreeGroup treeGroup) {
        if (treeGroup == null) {
            throw new DaoException(
                    "To-one property 'gsTree' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.treeGroup = treeGroup;
            gsTree = treeGroup.getId();
            treeGroup__resolvedKey = gsTree;
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

    public String stringDate() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd");
        return sdf.format(date);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1186362298)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTreeTypeInfoDao() : null;
    }


}
