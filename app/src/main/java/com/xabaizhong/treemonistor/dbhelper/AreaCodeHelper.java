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
         * AreaID : 100000
         * ParentId : 0
         * Name : 中国
         * MergerName : 中国
         * ShortName : 中国
         * MergerShortName : 中国
         * LevelType : 0
         * CityCode :
         * ZipCode :
         * Pinyin : China
         * Jianpin : CN
         * FirstChar : C
         * Lng : 116.3683244
         * Lat : 39.915085
         * Remarks :
         */

        private String AreaID;
        private String ParentId;
        private String Name;
        private String MergerName;
        private String ShortName;
        private String MergerShortName;
        private String LevelType;
        private String CityCode;
        private String ZipCode;
        private String Pinyin;
        private String Jianpin;
        private String FirstChar;
        private String Lng;
        private String Lat;
        private String Remarks;

        public String getAreaID() {
            return AreaID;
        }

        public void setAreaID(String AreaID) {
            this.AreaID = AreaID;
        }

        public String getParentId() {
            return ParentId;
        }

        public void setParentId(String ParentId) {
            this.ParentId = ParentId;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getMergerName() {
            return MergerName;
        }

        public void setMergerName(String MergerName) {
            this.MergerName = MergerName;
        }

        public String getShortName() {
            return ShortName;
        }

        public void setShortName(String ShortName) {
            this.ShortName = ShortName;
        }

        public String getMergerShortName() {
            return MergerShortName;
        }

        public void setMergerShortName(String MergerShortName) {
            this.MergerShortName = MergerShortName;
        }

        public String getLevelType() {
            return LevelType;
        }

        public void setLevelType(String LevelType) {
            this.LevelType = LevelType;
        }

        public String getCityCode() {
            return CityCode;
        }

        public void setCityCode(String CityCode) {
            this.CityCode = CityCode;
        }

        public String getZipCode() {
            return ZipCode;
        }

        public void setZipCode(String ZipCode) {
            this.ZipCode = ZipCode;
        }

        public String getPinyin() {
            return Pinyin;
        }

        public void setPinyin(String Pinyin) {
            this.Pinyin = Pinyin;
        }

        public String getJianpin() {
            return Jianpin;
        }

        public void setJianpin(String Jianpin) {
            this.Jianpin = Jianpin;
        }

        public String getFirstChar() {
            return FirstChar;
        }

        public void setFirstChar(String FirstChar) {
            this.FirstChar = FirstChar;
        }

        public String getLng() {
            return Lng;
        }

        public void setLng(String Lng) {
            this.Lng = Lng;
        }

        public String getLat() {
            return Lat;
        }

        public void setLat(String Lat) {
            this.Lat = Lat;
        }

        public String getRemarks() {
            return Remarks;
        }

        public void setRemarks(String Remarks) {
            this.Remarks = Remarks;
        }

        public AreaCode convertToEntity() {
            return new AreaCode(null, getAreaID(), getParentId(), getName(), getMergerName(),
                    getShortName(), getMergerShortName(), getLevelType(), getCityCode(),
                    getZipCode(), getPinyin(), getJianpin(), getFirstChar(), getLng(),
                    getLat(), getRemarks());

        }
    }

}
