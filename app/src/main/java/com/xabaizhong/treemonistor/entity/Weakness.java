package com.xabaizhong.treemonistor.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
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
public class Weakness {
    @Id
    private Long id;
    private String name;
    private long partId;


    @ToOne(joinProperty = "partId")
    private Tree_weak_part treePart;
    private long f1Id;
    @ToOne(joinProperty = "f1Id")
    private Weakness_f1 weakness_f1;
    private long f2Id;
    @ToOne(joinProperty = "f2Id")
    private Weakness_f2 weakness_f2;

    private String trait;
    private String method;
/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;
/** Used for active entity operations. */
@Generated(hash = 987437071)
private transient WeaknessDao myDao;
@Generated(hash = 2134326252)
public Weakness(Long id, String name, long partId, long f1Id, long f2Id,
        String trait, String method) {
    this.id = id;
    this.name = name;
    this.partId = partId;
    this.f1Id = f1Id;
    this.f2Id = f2Id;
    this.trait = trait;
    this.method = method;
}
@Generated(hash = 52880024)
public Weakness() {
}
public Long getId() {
    return this.id;
}
public void setId(Long id) {
    this.id = id;
}
public String getName() {
    return this.name;
}
public void setName(String name) {
    this.name = name;
}
public long getPartId() {
    return this.partId;
}
public void setPartId(long partId) {
    this.partId = partId;
}
public long getF1Id() {
    return this.f1Id;
}
public void setF1Id(long f1Id) {
    this.f1Id = f1Id;
}
public long getF2Id() {
    return this.f2Id;
}
public void setF2Id(long f2Id) {
    this.f2Id = f2Id;
}
public String getTrait() {
    return this.trait;
}
public void setTrait(String trait) {
    this.trait = trait;
}
public String getMethod() {
    return this.method;
}
public void setMethod(String method) {
    this.method = method;
}
@Generated(hash = 594589177)
private transient Long treePart__resolvedKey;
/** To-one relationship, resolved on first access. */
@Generated(hash = 192497041)
public Tree_weak_part getTreePart() {
    long __key = this.partId;
    if (treePart__resolvedKey == null || !treePart__resolvedKey.equals(__key)) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        Tree_weak_partDao targetDao = daoSession.getTree_weak_partDao();
        Tree_weak_part treePartNew = targetDao.load(__key);
        synchronized (this) {
            treePart = treePartNew;
            treePart__resolvedKey = __key;
        }
    }
    return treePart;
}
/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 390902518)
public void setTreePart(@NotNull Tree_weak_part treePart) {
    if (treePart == null) {
        throw new DaoException(
                "To-one property 'partId' has not-null constraint; cannot set to-one to null");
    }
    synchronized (this) {
        this.treePart = treePart;
        partId = treePart.getId();
        treePart__resolvedKey = partId;
    }
}
@Generated(hash = 100040213)
private transient Long weakness_f1__resolvedKey;
/** To-one relationship, resolved on first access. */
@Generated(hash = 2037041619)
public Weakness_f1 getWeakness_f1() {
    long __key = this.f1Id;
    if (weakness_f1__resolvedKey == null
            || !weakness_f1__resolvedKey.equals(__key)) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        Weakness_f1Dao targetDao = daoSession.getWeakness_f1Dao();
        Weakness_f1 weakness_f1New = targetDao.load(__key);
        synchronized (this) {
            weakness_f1 = weakness_f1New;
            weakness_f1__resolvedKey = __key;
        }
    }
    return weakness_f1;
}
/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 2094087634)
public void setWeakness_f1(@NotNull Weakness_f1 weakness_f1) {
    if (weakness_f1 == null) {
        throw new DaoException(
                "To-one property 'f1Id' has not-null constraint; cannot set to-one to null");
    }
    synchronized (this) {
        this.weakness_f1 = weakness_f1;
        f1Id = weakness_f1.getId();
        weakness_f1__resolvedKey = f1Id;
    }
}
@Generated(hash = 1118430128)
private transient Long weakness_f2__resolvedKey;
/** To-one relationship, resolved on first access. */
@Generated(hash = 987417158)
public Weakness_f2 getWeakness_f2() {
    long __key = this.f2Id;
    if (weakness_f2__resolvedKey == null
            || !weakness_f2__resolvedKey.equals(__key)) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        Weakness_f2Dao targetDao = daoSession.getWeakness_f2Dao();
        Weakness_f2 weakness_f2New = targetDao.load(__key);
        synchronized (this) {
            weakness_f2 = weakness_f2New;
            weakness_f2__resolvedKey = __key;
        }
    }
    return weakness_f2;
}
/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 1490949743)
public void setWeakness_f2(@NotNull Weakness_f2 weakness_f2) {
    if (weakness_f2 == null) {
        throw new DaoException(
                "To-one property 'f2Id' has not-null constraint; cannot set to-one to null");
    }
    synchronized (this) {
        this.weakness_f2 = weakness_f2;
        f2Id = weakness_f2.getId();
        weakness_f2__resolvedKey = f2Id;
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
@Generated(hash = 290070438)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getWeaknessDao() : null;
}
}
