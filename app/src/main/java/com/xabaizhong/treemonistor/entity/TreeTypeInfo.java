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

@Entity(indexes = {
        @Index(value = "id DESC", unique = true)
})
public class TreeTypeInfo {
    @Id
    private Long id;
    private String ivst = "0";
    private int typeId = 0;  // 0 古树 1 古树群
    private String treeId;
    private Date date;
    private String areaId;
    private long gsTree;// tree  id  属相  not  treeId




    public String check() {
        if (treeId == null)
            return "古树(群)编号为空";
        if (date == null)
            return "调查日期为空";
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
    public int getTypeId() {
        return this.typeId;
    }
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
    public String getTreeId() {
        return this.treeId;
    }
    public void setTreeId(String treeId) {
        this.treeId = treeId;
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
    public Long getTreeTableID() {
        return this.treeTableID;
    }
    public void setTreeTableID(Long treeTableID) {
        this.treeTableID = treeTableID;
    }
    public Long getTreeGroup_id() {
        return this.treeGroup_id;
    }
    public void setTreeGroup_id(Long treeGroup_id) {
        this.treeGroup_id = treeGroup_id;
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 221856822)
    public Tree getTree() {
        Long __key = this.treeTableID;
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
    @Generated(hash = 1305667952)
    public void setTree(Tree tree) {
        synchronized (this) {
            this.tree = tree;
            treeTableID = tree == null ? null : tree.getId();
            tree__resolvedKey = treeTableID;
        }
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1486164824)
    public TreeGroup getTreeGroup() {
        Long __key = this.treeGroup_id;
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
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1547466380)
    public void setTreeGroup(TreeGroup treeGroup) {
        synchronized (this) {
            this.treeGroup = treeGroup;
            treeGroup_id = treeGroup == null ? null : treeGroup.getId();
            treeGroup__resolvedKey = treeGroup_id;
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

    private Long treeTableID;
    @ToOne(joinProperty = "treeTableID")
    private Tree tree;
    private Long treeGroup_id;
    @ToOne(joinProperty = "treeGroup_id")
    private TreeGroup treeGroup;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 747255772)
    private transient TreeTypeInfoDao myDao;




    @Generated(hash = 1534781139)
    public TreeTypeInfo(Long id, String ivst, int typeId, String treeId, Date date,
            String areaId, long gsTree, Long treeTableID, Long treeGroup_id) {
        this.id = id;
        this.ivst = ivst;
        this.typeId = typeId;
        this.treeId = treeId;
        this.date = date;
        this.areaId = areaId;
        this.gsTree = gsTree;
        this.treeTableID = treeTableID;
        this.treeGroup_id = treeGroup_id;
    }
    @Generated(hash = 1444053980)
    public TreeTypeInfo() {
    }

    @Generated(hash = 504113244)
    private transient Long tree__resolvedKey;
    @Generated(hash = 371016317)
    private transient Long treeGroup__resolvedKey;



}
