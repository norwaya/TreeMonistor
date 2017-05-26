package com.xabaizhong.treemonistor.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import java.util.List;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by admin on 2017/3/15.
 */
@Entity(indexes = {
        @Index(value = "id DESC", unique = true)
})
public class TreePic {
    @Id
    private Long id;
    @NotNull
    private long tree_id;// 此treeId 不是彼 treeId  其实 是 tree 的 id
    private String path;
@Generated(hash = 1283520150)
public TreePic(Long id, long tree_id, String path) {
    this.id = id;
    this.tree_id = tree_id;
    this.path = path;
}
@Generated(hash = 2053578404)
public TreePic() {
}
public Long getId() {
    return this.id;
}
public void setId(Long id) {
    this.id = id;
}
public long getTree_id() {
    return this.tree_id;
}
public void setTree_id(long tree_id) {
    this.tree_id = tree_id;
}
public String getPath() {
    return this.path;
}
public void setPath(String path) {
    this.path = path;
}


}
