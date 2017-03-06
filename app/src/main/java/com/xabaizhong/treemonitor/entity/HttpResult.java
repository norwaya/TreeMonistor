package com.xabaizhong.treemonitor.entity;

import java.util.List;

/**
 * Created by admin on 2017/2/28.
 */

public class HttpResult {

    /**
     * code : 0
     * message : ok
     * pagedList : {"list":[{"name":"tom","realName":"tom","password":"123456","ssbm":"111114824a5c4e888800619001ff3871","yhm":"tom","xm":"tom","csny":"","czzt":"","llry":"","lrsj":"","mm":"","sj":"","xb":"","xl":"","zc":"","zfzb":"","zjjz":""}]}
     */

    private int code;
    private String message;
    private PagedListBean pagedList;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PagedListBean getPagedList() {
        return pagedList;
    }

    public void setPagedList(PagedListBean pagedList) {
        this.pagedList = pagedList;
    }

    public static class PagedListBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * name : tom
             * realName : tom
             * password : 123456
             * ssbm : 111114824a5c4e888800619001ff3871
             * yhm : tom
             * xm : tom
             * csny :
             * czzt :
             * llry :
             * lrsj :
             * mm :
             * sj :
             * xb :
             * xl :
             * zc :
             * zfzb :
             * zjjz :
             */

            private String name;
            private String realName;
            private String password;
            private String ssbm;
            private String yhm;
            private String xm;
            private String csny;
            private String czzt;
            private String llry;
            private String lrsj;
            private String mm;
            private String sj;
            private String xb;
            private String xl;
            private String zc;
            private String zfzb;
            private String zjjz;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getSsbm() {
                return ssbm;
            }

            public void setSsbm(String ssbm) {
                this.ssbm = ssbm;
            }

            public String getYhm() {
                return yhm;
            }

            public void setYhm(String yhm) {
                this.yhm = yhm;
            }

            public String getXm() {
                return xm;
            }

            public void setXm(String xm) {
                this.xm = xm;
            }

            public String getCsny() {
                return csny;
            }

            public void setCsny(String csny) {
                this.csny = csny;
            }

            public String getCzzt() {
                return czzt;
            }

            public void setCzzt(String czzt) {
                this.czzt = czzt;
            }

            public String getLlry() {
                return llry;
            }

            public void setLlry(String llry) {
                this.llry = llry;
            }

            public String getLrsj() {
                return lrsj;
            }

            public void setLrsj(String lrsj) {
                this.lrsj = lrsj;
            }

            public String getMm() {
                return mm;
            }

            public void setMm(String mm) {
                this.mm = mm;
            }

            public String getSj() {
                return sj;
            }

            public void setSj(String sj) {
                this.sj = sj;
            }

            public String getXb() {
                return xb;
            }

            public void setXb(String xb) {
                this.xb = xb;
            }

            public String getXl() {
                return xl;
            }

            public void setXl(String xl) {
                this.xl = xl;
            }

            public String getZc() {
                return zc;
            }

            public void setZc(String zc) {
                this.zc = zc;
            }

            public String getZfzb() {
                return zfzb;
            }

            public void setZfzb(String zfzb) {
                this.zfzb = zfzb;
            }

            public String getZjjz() {
                return zjjz;
            }

            public void setZjjz(String zjjz) {
                this.zjjz = zjjz;
            }
        }
    }
}
