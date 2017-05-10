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
    private int error_code;
    @SerializedName("result")
    private List<CheckinfolistBean> Checkinfolist;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<CheckinfolistBean> getCheckinfolist() {
        return Checkinfolist;
    }

    public void setCheckinfolist(List<CheckinfolistBean> Checkinfolist) {
        this.Checkinfolist = Checkinfolist;
    }

    public static class CheckinfolistBean {
        /**
         * DateTime : 2017-04-14 04:39:22
         * AuthType : 0
         * UserID : test1
         * Tid : 14
         */

        @SerializedName("DateTime")
        private String DateTime;
        @SerializedName("AuthType")
        private int AuthType;
        @SerializedName("UserID")
        private String UserID;
        @SerializedName("Tid")
        private String Tid;

        public String getDateTime() {
            return DateTime;
        }

        public void setDateTime(String DateTime) {
            this.DateTime = DateTime;
        }

        public int getAuthType() {
            return AuthType;
        }

        public void setAuthType(int AuthType) {
            this.AuthType = AuthType;
        }

        public String getUserID() {
            return UserID;
        }

        public void setUserID(String UserID) {
            this.UserID = UserID;
        }

        public String getTid() {
            return Tid;
        }

        public void setTid(String Tid) {
            this.Tid = Tid;
        }

//        /**
//         * TID : 1
//         * treeid :  61032900001
//         * CheckType : 0
//         */
//
//        @SerializedName("Tid")
//        private String TID;
//        @SerializedName("Type")
//        private int CheckType;
//
//        public String getTID() {
//            return TID;
//        }
//
//        public void setTID(String TID) {
//            this.TID = TID;
//        }
//
//        public int getCheckType() {
//            return CheckType;
//        }
//
//        public void setCheckType(int CheckType) {
//            this.CheckType = CheckType;
//        }

    }
}
