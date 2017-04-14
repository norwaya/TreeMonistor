package com.xabaizhong.treemonistor.service.model;

import java.util.List;

/**
 * Created by norwaya on 17-4-14.
 */

public class MonitorTree {


    /**
     * TreeID : 61032201
     * UserID : 6100000001
     * date : 2017-03-21 13:46:24
     * picinfo : [{"PicPlace":0,"Explain":"213","piclist":["/9j/4K4nTLyg73Ku/huFUf5jt7OYOsZn/9k="]}]
     */

    private String TreeID;
    private String UserID;
    private String date;
    private List<PicinfoBean> picinfo;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<PicinfoBean> getPicinfo() {
        return picinfo;
    }

    public void setPicinfo(List<PicinfoBean> picinfo) {
        this.picinfo = picinfo;
    }

    public static class PicinfoBean {
        /**
         * PicPlace : 0
         * Explain : 213
         * piclist : ["/9j/4K4nTLyg73Ku/huFUf5jt7OYOsZn/9k="]
         */

        private int PicPlace;
        private String Explain;
        private List<String> piclist;

        public int getPicPlace() {
            return PicPlace;
        }

        public void setPicPlace(int PicPlace) {
            this.PicPlace = PicPlace;
        }

        public String getExplain() {
            return Explain;
        }

        public void setExplain(String Explain) {
            this.Explain = Explain;
        }

        public List<String> getPiclist() {
            return piclist;
        }

        public void setPiclist(List<String> piclist) {
            this.piclist = piclist;
        }
    }
}
