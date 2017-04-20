package com.xabaizhong.treemonistor.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/4/18 0018.
 */
@Entity(indexes = {
        @Index(value = "id DESC", unique = true)
})
public class Tree_weak_part {
    @Id
    private Long id;
    private int partId;
    private String name;
@Generated(hash = 1570256356)
public Tree_weak_part(Long id, int partId, String name) {
    this.id = id;
    this.partId = partId;
    this.name = name;
}
@Generated(hash = 972616257)
public Tree_weak_part() {
}
public Long getId() {
    return this.id;
}
public void setId(Long id) {
    this.id = id;
}
public int getPartId() {
    return this.partId;
}
public void setPartId(int partId) {
    this.partId = partId;
}
public String getName() {
    return this.name;
}
public void setName(String name) {
    this.name = name;
}

}
