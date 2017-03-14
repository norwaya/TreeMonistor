package com.xabaizhong.treemonistor.dbhelper;

import com.xabaizhong.treemonistor.entity.AreaCode;

import java.util.List;

/**
 * Created by admin on 2017/3/14.
 */

public class AreaCodeHelper {

    private List<RECORDSBean> RECORDS;

    public List<RECORDSBean> getRECORDS() {
        return RECORDS;
    }

    public void setRECORDS(List<RECORDSBean> RECORDS) {
        this.RECORDS = RECORDS;
    }

    public static class RECORDSBean {
        /**
         * areaid : 610100
         * cname : 西安市
         * id : 1
         */

        private int areaid;
        private String cname;
        private int id;

        public int getAreaid() {
            return areaid;
        }

        public void setAreaid(int areaid) {
            this.areaid = areaid;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public AreaCode convertToEntity() {
            return new AreaCode(null,this.cname,this.areaid);
        }

    }
}
