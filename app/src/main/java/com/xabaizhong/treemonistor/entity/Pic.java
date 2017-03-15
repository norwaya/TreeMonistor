package com.xabaizhong.treemonistor.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import java.util.List;

import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by admin on 2017/3/15.
 */
@Entity(indexes = {
        @Index(value = "id DESC", unique = true)
})
public class Pic {
    @Id
    private Long id;
    private long treeId;// 此treeId 不是彼 treeId  其实 是 tree 的 id
    private String path;

    @Generated(hash = 123517054)
    public Pic(Long id, long treeId, String path) {
        this.id = id;
        this.treeId = treeId;
        this.path = path;
    }

    @Generated(hash = 1022185358)
    public Pic() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTreeId() {
        return this.treeId;
    }

    public void setTreeId(long treeId) {
        this.treeId = treeId;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
