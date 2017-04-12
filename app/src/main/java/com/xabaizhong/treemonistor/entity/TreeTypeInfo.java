package com.xabaizhong.treemonistor.entity;

import com.google.gson.annotations.Expose;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by admin on 2017/3/13.
 */
public class TreeTypeInfo {
    private Long id;
    private String ivst = "0";
    private int typeId = 0;  // 0 古树 1 古树群
    private String treeId;
    private Date date;
    private String areaId = "610329";
    private long gsTree;// tree  id  属相  not  treeId

    private Tree tree;

    private TreeGroup treeGroup;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIvst() {
        return ivst;
    }

    public void setIvst(String ivst) {
        this.ivst = ivst;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }


    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public long getGsTree() {
        return gsTree;
    }

    public void setGsTree(long gsTree) {
        this.gsTree = gsTree;
    }

    public Tree getTree() {
        return tree;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }

    public TreeGroup getTreeGroup() {
        return treeGroup;
    }

    public void setTreeGroup(TreeGroup treeGroup) {
        this.treeGroup = treeGroup;
    }
}
