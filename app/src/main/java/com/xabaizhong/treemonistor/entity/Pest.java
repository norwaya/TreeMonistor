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
public class Pest {
    @Id
    private Long id;
    private String hexapodId;
    private int hexapodType;
    private String hexapodname;
    private String trait;
    private String method;

    @Generated(hash = 105299168)
    public Pest(Long id, String hexapodId, int hexapodType, String hexapodname,
                String trait, String method) {
        this.id = id;
        this.hexapodId = hexapodId;
        this.hexapodType = hexapodType;
        this.hexapodname = hexapodname;
        this.trait = trait;
        this.method = method;
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

    public String getHexapodId() {
        return this.hexapodId;
    }

    public void setHexapodId(String hexapodId) {
        this.hexapodId = hexapodId;
    }

    public int getHexapodType() {
        return this.hexapodType;
    }

    public void setHexapodType(int hexapodType) {
        this.hexapodType = hexapodType;
    }

    public String getHexapodname() {
        return this.hexapodname;
    }

    public void setHexapodname(String hexapodname) {
        this.hexapodname = hexapodname;
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

    /**
     * HexapodID : 1
     * HexapodType : 0
     * HexapodName : 线茸毒蛾
     * Trait : 幼虫主要为害叶片,该虫食量大,食性杂,严重时可将全树叶片吃光。
     * Method
     */
}
