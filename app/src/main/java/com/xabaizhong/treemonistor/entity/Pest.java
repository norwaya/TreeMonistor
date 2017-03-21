package com.xabaizhong.treemonistor.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by admin on 2017/3/17.
 */
@Entity(indexes = {
        @Index(value = "id DESC", unique = true)
})
public class Pest {
    private Long id;
    private String cname;
    private long pestClassId;
    @ToOne(joinProperty = "pestClassId")
    private PestClass pestClass;
    private String explain;
    private String special;
    private String step;
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1387879180)
    private transient PestDao myDao;

    @Generated(hash = 194766728)
    public Pest(Long id, String cname, long pestClassId, String explain,
                String special, String step) {
        this.id = id;
        this.cname = cname;
        this.pestClassId = pestClassId;
        this.explain = explain;
        this.special = special;
        this.step = step;
    }

    @Generated(hash = 495447472)
    public Pest() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCname() {
        return this.cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public long getPestClassId() {
        return this.pestClassId;
    }

    public void setPestClassId(long pestClassId) {
        this.pestClassId = pestClassId;
    }

    public String getExplain() {
        return this.explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getSpecial() {
        return this.special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getStep() {
        return this.step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    @Generated(hash = 1444425654)
    private transient Long pestClass__resolvedKey;

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 273172549)
    public PestClass getPestClass() {
        long __key = this.pestClassId;
        if (pestClass__resolvedKey == null
                || !pestClass__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PestClassDao targetDao = daoSession.getPestClassDao();
            PestClass pestClassNew = targetDao.load(__key);
            synchronized (this) {
                pestClass = pestClassNew;
                pestClass__resolvedKey = __key;
            }
        }
        return pestClass;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 288463086)
    public void setPestClass(@NotNull PestClass pestClass) {
        if (pestClass == null) {
            throw new DaoException(
                    "To-one property 'pestClassId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.pestClass = pestClass;
            pestClassId = pestClass.getId();
            pestClass__resolvedKey = pestClassId;
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
    @Generated(hash = 2145907554)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPestDao() : null;
    }

}
