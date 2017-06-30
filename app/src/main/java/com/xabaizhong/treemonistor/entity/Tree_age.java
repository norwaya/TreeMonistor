package com.xabaizhong.treemonistor.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/4/26 0026.
 */
@Entity(indexes = {
        @Index(value = "id DESC", unique = true)
})
public class Tree_age {

    @Id
    private Long id;
    private String cname;
    private int treeBj;
    private int treeAge;
    private int treeAgeNum;

    @Generated(hash = 2065622516)
    public Tree_age(Long id, String cname, int treeBj, int treeAge,
                    int treeAgeNum) {
        this.id = id;
        this.cname = cname;
        this.treeBj = treeBj;
        this.treeAge = treeAge;
        this.treeAgeNum = treeAgeNum;
    }

    @Generated(hash = 1576550704)
    public Tree_age() {
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

    public int getTreeBj() {
        return this.treeBj;
    }

    public void setTreeBj(int treeBj) {
        this.treeBj = treeBj;
    }

    public int getTreeAge() {
        return this.treeAge;
    }

    public void setTreeAge(int treeAge) {
        this.treeAge = treeAge;
    }

    public int getTreeAgeNum() {
        return this.treeAgeNum;
    }

    public void setTreeAgeNum(int treeAgeNum) {
        this.treeAgeNum = treeAgeNum;
    }

}
