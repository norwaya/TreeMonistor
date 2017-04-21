package com.xabaizhong.treemonistor.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/4/17 0017.
 */
@Entity(indexes = {
        @Index(value = "id DESC", unique = true)
})
public class Weakness_f2 {
    @Id
    private Long id;
    private int f1Id;
    private int fId;
    private String name;
@Generated(hash = 822929943)
public Weakness_f2(Long id, int f1Id, int fId, String name) {
    this.id = id;
    this.f1Id = f1Id;
    this.fId = fId;
    this.name = name;
}
@Generated(hash = 154543802)
public Weakness_f2() {
}
public Long getId() {
    return this.id;
}
public void setId(Long id) {
    this.id = id;
}
public int getF1Id() {
    return this.f1Id;
}
public void setF1Id(int f1Id) {
    this.f1Id = f1Id;
}
public int getFId() {
    return this.fId;
}
public void setFId(int fId) {
    this.fId = fId;
}
public String getName() {
    return this.name;
}
public void setName(String name) {
    this.name = name;
}

}
