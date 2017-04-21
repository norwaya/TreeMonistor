package com.xabaizhong.treemonistor.dbhelper;

import com.google.gson.annotations.SerializedName;
import com.xabaizhong.treemonistor.entity.TreeSpecial;

import java.util.List;

/**
 * Created by admin on 2017/3/13.
 */

public class TreeSpecialHelper {


    @SerializedName("RECORDS")
    private List<RECORDSBean> RECORDS;

    public List<RECORDSBean> getRECORDS() {
        return RECORDS;
    }

    public void setRECORDS(List<RECORDSBean> RECORDS) {
        this.RECORDS = RECORDS;
    }

    public static class RECORDSBean {
        /**
         * TreeSpeID : T00021
         * CHName : 臭冷杉
         * jianpin : cls
         * SpecialCode : 115
         * ENName : Abies nephrolepsi
         * Family : 松科
         * ToFamily : 冷杉亚科
         * Belong : 冷杉属
         * Alias : 华北冷杉、臭松、东陵冷杉
         * LatinName : Abies nephrolepis (Trautv.) Maxim.
         * Explian : 臭冷杉(拉丁学名:Abiesnephrolepis(Trautv.) Maxim.)别名: 华北冷杉、臭松、东陵冷杉 、小五台地区俗称"臭桃葫芦"，乔木，高30m，胸径50cm，树冠尖塔形至圆锥形。树皮青灰色，浅裂或不裂。一年生枝淡黄色或淡灰褐色，密生褐色短柔毛。冬芽有树脂，叶条形，长1~3厘米。宽约1.5mm。上面亮绿色，下面有2条白色气孔带，花期:4~5月，果期:9~10月。产于中国东北小兴安岭南坡、长白山区及张广才岭海拔300-1800米，河北小五台山、雾灵山、围场及山西五台山海拔1700-21
         * TreeSpeType : 0
         */

        @SerializedName("TreeSpeID")
        private String TreeSpeID;
        @SerializedName("CHName")
        private String CHName;
        @SerializedName("jianpin")
        private String jianpin;
        @SerializedName("SpecialCode")
        private String SpecialCode;
        @SerializedName("ENName")
        private String ENName;
        @SerializedName("Family")
        private String Family;
        @SerializedName("ToFamily")
        private String ToFamily;
        @SerializedName("Belong")
        private String Belong;
        @SerializedName("Alias")
        private String Alias;
        @SerializedName("LatinName")
        private String LatinName;
        @SerializedName("Explian")
        private String Explian;
        @SerializedName("TreeSpeType")
        private int TreeSpeType;

        public String getTreeSpeID() {
            return TreeSpeID;
        }

        public void setTreeSpeID(String TreeSpeID) {
            this.TreeSpeID = TreeSpeID;
        }

        public String getCHName() {
            return CHName;
        }

        public void setCHName(String CHName) {
            this.CHName = CHName;
        }

        public String getJianpin() {
            return jianpin;
        }

        public void setJianpin(String jianpin) {
            this.jianpin = jianpin;
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

        public int getTreeSpeType() {
            return TreeSpeType;
        }

        public void setTreeSpeType(int TreeSpeType) {
            this.TreeSpeType = TreeSpeType;
        }

        public TreeSpecial convertToEntity() {
            return new TreeSpecial(null, TreeSpeID, CHName, jianpin,
                    SpecialCode, ENName, Family, ToFamily, Belong, Alias, LatinName, Explian, TreeSpeType);
        }
    }
}
