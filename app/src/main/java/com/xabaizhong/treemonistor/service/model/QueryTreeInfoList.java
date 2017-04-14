package com.xabaizhong.treemonistor.service.model;

import java.util.List;

/**
 * Created by norwaya on 17-4-14.
 */

public class QueryTreeInfoList {

    /**
     * message : sus
     * error_code : 0
     * areaid : 610323001
     * userid : 6100323
     * TreeType : 0
     * DetType : 0
     * queryinfolist : [{"treeid":"61032900001","treename":"huaishu","areaname":"xian"},{"treeid":"61032900002","treename":"liushu","areaname":"weinan"},{"treeid":"61032900003","treename":"songshu","areaname":"xianyang"}]
     */

    private String message;
    private String error_code;
    private String areaid;
    private String userid;
    private int TreeType;
    private int DetType;
    private List<QueryinfolistBean> queryinfolist;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getTreeType() {
        return TreeType;
    }

    public void setTreeType(int TreeType) {
        this.TreeType = TreeType;
    }

    public int getDetType() {
        return DetType;
    }

    public void setDetType(int DetType) {
        this.DetType = DetType;
    }

    public List<QueryinfolistBean> getQueryinfolist() {
        return queryinfolist;
    }

    public void setQueryinfolist(List<QueryinfolistBean> queryinfolist) {
        this.queryinfolist = queryinfolist;
    }

    public static class QueryinfolistBean {
        /**
         * treeid : 61032900001
         * treename : huaishu
         * areaname : xian
         */

        private String treeid;
        private String treename;
        private String areaname;

        public String getTreeid() {
            return treeid;
        }

        public void setTreeid(String treeid) {
            this.treeid = treeid;
        }

        public String getTreename() {
            return treename;
        }

        public void setTreename(String treename) {
            this.treename = treename;
        }

        public String getAreaname() {
            return areaname;
        }

        public void setAreaname(String areaname) {
            this.areaname = areaname;
        }
    }
}
