package com.xabaizhong.treemonistor.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/4/24 0024.
 */

public class Expert_obtain_list {

    /**
     * message : sus
     * error_code : 0
     * areaid : 6100323
     * userid : 610323001
     * Checkinfolist : [{"TID":1,"treeid":" 61032900001","CheckType":0},{"TID":2,"treeid":" 61032900001","CheckType":1},{"TID":3,"treeid":" 61032900001","CheckType":2}]
     */

    @SerializedName("message")
    private String message;
    @SerializedName("error_code")
    private int errorCode;
    @SerializedName("areaid")
    private String areaid;
    @SerializedName("userid")
    private String userid;
    @SerializedName("Checkinfolist")
    private List<CheckinfolistBean> Checkinfolist;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
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

    public List<CheckinfolistBean> getCheckinfolist() {
        return Checkinfolist;
    }

    public void setCheckinfolist(List<CheckinfolistBean> Checkinfolist) {
        this.Checkinfolist = Checkinfolist;
    }

    public static class CheckinfolistBean {
        /**
         * TID : 1
         * treeid :  61032900001
         * CheckType : 0
         */

        @SerializedName("TID")
        private int TID;
        @SerializedName("treeid")
        private String treeid;
        @SerializedName("CheckType")
        private int CheckType;

        public int getTID() {
            return TID;
        }

        public void setTID(int TID) {
            this.TID = TID;
        }

        public String getTreeid() {
            return treeid;
        }

        public void setTreeid(String treeid) {
            this.treeid = treeid;
        }

        public int getCheckType() {
            return CheckType;
        }

        public void setCheckType(int CheckType) {
            this.CheckType = CheckType;
        }
    }
}
