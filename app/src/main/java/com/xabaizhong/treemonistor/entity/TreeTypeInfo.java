package com.xabaizhong.treemonistor.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

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
    private Date  date;
    private String areaId;
@Generated(hash = 1315525464)
public TreeTypeInfo(long id, String treeId, String ivst, String typeId,
        String recoredPerson, Date date, String areaId) {
    this.id = id;
    this.treeId = treeId;
    this.ivst = ivst;
    this.typeId = typeId;
    this.recoredPerson = recoredPerson;
    this.date = date;
    this.areaId = areaId;
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

}
