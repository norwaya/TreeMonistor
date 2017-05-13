package com.xabaizhong.treemonistor.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/5/13 0013.
 */
@Entity(indexes = {
        @Index(value = "id DESC", unique = true)
})
public class TreeMap {
    @Id
    private Long id;
    @NotNull
    private Long treeGroup_id;
    private String name;
    private int num;

    @Generated(hash = 2005136569)
    public TreeMap(Long id, @NotNull Long treeGroup_id, String name, int num) {
        this.id = id;
        this.treeGroup_id = treeGroup_id;
        this.name = name;
        this.num = num;
    }

    @Generated(hash = 605175252)
    public TreeMap() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTreeGroup_id() {
        return this.treeGroup_id;
    }

    public void setTreeGroup_id(Long treeGroup_id) {
        this.treeGroup_id = treeGroup_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return this.num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
