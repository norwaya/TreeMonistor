package com.xabaizhong.treemonistor.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by norwaya on 17-4-14.
 */

public class QueryTreeInfoList {


    /**
     * message : sus
     * error_code : 0
     * list : [{"CHName":"","date":"","AreaID":"","TreeID":"","UserID":""}]
     */

    @SerializedName("message")
    private String message;
    @SerializedName("error_code")
    private int errorCode;
    @SerializedName("list")
    private List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * CHName :
         * date :
         * AreaID :
         * TreeID :
         * UserID :
         */

        @SerializedName("CHName")
        private String CHName;
        @SerializedName("date")
        private String date;
        @SerializedName("AreaID")
        private String AreaID;
        @SerializedName("TreeID")
        private String TreeID;
        @SerializedName("UserID")
        private String UserID;

        public String getCHName() {
            return CHName;
        }

        public void setCHName(String CHName) {
            this.CHName = CHName;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getAreaID() {
            return AreaID;
        }

        public void setAreaID(String AreaID) {
            this.AreaID = AreaID;
        }

        public String getTreeID() {
            return TreeID;
        }

        public void setTreeID(String TreeID) {
            this.TreeID = TreeID;
        }

        public String getUserID() {
            return UserID;
        }

        public void setUserID(String UserID) {
            this.UserID = UserID;
        }
    }
}
