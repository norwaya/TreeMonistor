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
    private int fId;
    private String name;

    @Generated(hash = 222814106)
    public Weakness_f2(Long id, int fId, String name) {
        this.id = id;
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
