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
public class Weakness {
    @Id
    private Long id;
    private String cname;
    private String bz;
    private String wh;
    private String fz;
@Generated(hash = 675110727)
public Weakness(Long id, String cname, String bz, String wh, String fz) {
    this.id = id;
    this.cname = cname;
    this.bz = bz;
    this.wh = wh;
    this.fz = fz;
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
public String getCname() {
    return this.cname;
}
public void setCname(String cname) {
    this.cname = cname;
}
public String getBz() {
    return this.bz;
}
public void setBz(String bz) {
    this.bz = bz;
}
public String getWh() {
    return this.wh;
}
public void setWh(String wh) {
    this.wh = wh;
}
public String getFz() {
    return this.fz;
}
public void setFz(String fz) {
    this.fz = fz;
}
}
