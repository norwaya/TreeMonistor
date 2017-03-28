package com.xabaizhong.treemonistor.service.entity;

/**
 * Created by admin on 2017/3/15.
 */

public class User {

    /**
     * username : 610000
     * password : admin
     * roleid : 1
     * deptid : 2
     * areaid : 610000
     */

    private String username;
    private String password;
    private String roleid;
    private String deptid;
    private String areaid;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }
}
