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
         * SpeID : 1
         * CHName : 乔木树种
         * Jianpin : qmsz
         * SpecialCode : 900
         * ENName :
         * Family :
         * ToFamily :
         * Belong :
         * Alias : 乔木树种
         * LatinName :
         * Explian :
         * TreeSpeID : T00001
         * ReviewSta : 1
         * Memo :
         * Memo1 :
         * Memo2 :
         */

        private int SpeID;
        private String CHName;
        private String Jianpin;
        private String SpecialCode;
        private String ENName;
        private String Family;
        private String ToFamily;
        private String Belong;
        private String Alias;
        private String LatinName;
        private String Explian;
        private String TreeSpeID;
        private int ReviewSta;
        private String Memo;
        private String Memo1;
        private String Memo2;

        public int getSpeID() {
            return SpeID;
        }

        public void setSpeID(int SpeID) {
            this.SpeID = SpeID;
        }

        public String getCHName() {
            return CHName;
        }

        public void setCHName(String CHName) {
            this.CHName = CHName;
        }

        public String getJianpin() {
            return Jianpin;
        }

        public void setJianpin(String Jianpin) {
            this.Jianpin = Jianpin;
        }

        public String getSpecialCode() {
            return SpecialCode;
        }

        public void setSpecialCode(String SpecialCode) {
            this.SpecialCode = SpecialCode;
        }

        public String getENName() {
            return ENName;
        }

        public void setENName(String ENName) {
            this.ENName = ENName;
        }

        public String getFamily() {
            return Family;
        }

        public void setFamily(String Family) {
            this.Family = Family;
        }

        public String getToFamily() {
            return ToFamily;
        }

        public void setToFamily(String ToFamily) {
            this.ToFamily = ToFamily;
        }

        public String getBelong() {
            return Belong;
        }

        public void setBelong(String Belong) {
            this.Belong = Belong;
        }

        public String getAlias() {
            return Alias;
        }

        public void setAlias(String Alias) {
            this.Alias = Alias;
        }

        public String getLatinName() {
            return LatinName;
        }

        public void setLatinName(String LatinName) {
            this.LatinName = LatinName;
        }

        public String getExplian() {
            return Explian;
        }

        public void setExplian(String Explian) {
            this.Explian = Explian;
        }

        public String getTreeSpeID() {
            return TreeSpeID;
        }

        public void setTreeSpeID(String TreeSpeID) {
            this.TreeSpeID = TreeSpeID;
        }

        public int getReviewSta() {
            return ReviewSta;
        }

        public void setReviewSta(int ReviewSta) {
            this.ReviewSta = ReviewSta;
        }

        public String getMemo() {
            return Memo;
        }

        public void setMemo(String Memo) {
            this.Memo = Memo;
        }

        public String getMemo1() {
            return Memo1;
        }

        public void setMemo1(String Memo1) {
            this.Memo1 = Memo1;
        }

        public String getMemo2() {
            return Memo2;
        }

        public void setMemo2(String Memo2) {
            this.Memo2 = Memo2;
        }

        public TreeSpecial convertToEntity() {
            return new TreeSpecial(null, CHName, Jianpin, SpecialCode, ENName, Family, ToFamily, Belong, Alias, LatinName,
                    Explian, TreeSpeID, ReviewSta);
        }

    }
}
