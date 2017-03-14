package com.xabaizhong.treemonistor.dbhelper;

import com.xabaizhong.treemonistor.entity.TreeSpecial;

import java.util.List;

/**
 * Created by admin on 2017/3/13.
 */

public class TreeSpecialHelper {

    private List<RECORDSBean> RECORDS;

    public List<RECORDSBean> getRECORDS() {
        return RECORDS;
    }

    public void setRECORDS(List<RECORDSBean> RECORDS) {
        this.RECORDS = RECORDS;
    }

    public static class RECORDSBean {
        /**
         * cname : 乔木树种
         * code : 900
         * enname :
         * family :
         * tofamily :
         * belong :
         * alias : 乔木树种
         * latin :
         * remark :
         */

        private String cname;
        private int code;
        private String enname;
        private String family;
        private String tofamily;
        private String belong;
        private String alias;
        private String latin;
        private String remark;

        public TreeSpecial convertToEntity(){
            TreeSpecial treeSpecial = new TreeSpecial();
            /*(long id, String cname, String code, String enname,
                    String family, String tofamily, String belong, String alias,
                    String latin, String remark)*/
            return new TreeSpecial(null,this.cname,this.code,this.enname,this.family,this.tofamily,this.tofamily,this.alias,this.latin,this.remark);
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getEnname() {
            return enname;
        }

        public void setEnname(String enname) {
            this.enname = enname;
        }

        public String getFamily() {
            return family;
        }

        public void setFamily(String family) {
            this.family = family;
        }

        public String getTofamily() {
            return tofamily;
        }

        public void setTofamily(String tofamily) {
            this.tofamily = tofamily;
        }

        public String getBelong() {
            return belong;
        }

        public void setBelong(String belong) {
            this.belong = belong;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getLatin() {
            return latin;
        }

        public void setLatin(String latin) {
            this.latin = latin;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
