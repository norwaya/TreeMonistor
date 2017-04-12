package com.xabaizhong.treemonistor.service.model;

/**
 * Created by Administrator on 2017/4/11 0011.
 */

public class QueryTreeInfo {

    /**
     * message : sus
     * error_code : 0
     * areaid : 6100323
     * userid : 610323001
     *  TreeType : 0
     * DetType : 0
     * queryinfo : [{"name":"ALL","num":300},{"name":"treeinfo","num":150},{"name":"groupinfo","num":150}]
     */

    @com.google.gson.annotations.SerializedName("message")
    private String message;
    @com.google.gson.annotations.SerializedName("error_code")
    private String errorCode;
    @com.google.gson.annotations.SerializedName("areaid")
    private String areaid;
    @com.google.gson.annotations.SerializedName("userid")
    private String userid;
    @com.google.gson.annotations.SerializedName(" TreeType")
    private int TreeType;
    @com.google.gson.annotations.SerializedName("DetType")
    private int DetType;
    @com.google.gson.annotations.SerializedName("queryinfo")
    private java.util.List<QueryinfoBean> queryinfo;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
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

    public static class QueryinfoBean {
        /**
         * name : ALL
         * num : 300
         */

        @com.google.gson.annotations.SerializedName("name")
        private String name;
        @com.google.gson.annotations.SerializedName("num")
        private int num;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
