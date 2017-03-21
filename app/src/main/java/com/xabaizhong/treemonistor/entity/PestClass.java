package com.xabaizhong.treemonistor.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by admin on 2017/3/17.
 */
@Entity(indexes = {
        @Index(value = "id DESC", unique = true)
})
public class PestClass {
    @Id
    private Long id;
    private int pestId;
    private String cname;
@Generated(hash = 1510818190)
public PestClass(Long id, int pestId, String cname) {
    this.id = id;
    this.pestId = pestId;
    this.cname = cname;
}
@Generated(hash = 301199938)
public PestClass() {
}
public Long getId() {
    return this.id;
}
public void setId(Long id) {
    this.id = id;
}
public int getPestId() {
    return this.pestId;
}
public void setPestId(int pestId) {
    this.pestId = pestId;
}
public String getCname() {
    return this.cname;
}
public void setCname(String cname) {
    this.cname = cname;
}



}
