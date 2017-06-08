package com.xabaizhong.treemonistor.dbhelper;

import com.google.gson.annotations.SerializedName;
import com.xabaizhong.treemonistor.entity.Pest;

import java.util.List;

/**
 * Created by admin on 2017/3/17.
 */

public class PestHelper {


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
         * HexapodID : 1
         * HexapodType : 0
         * HexapodName : 线茸毒蛾
         * Trait : 幼虫主要为害叶片,该虫食量大,食性杂,严重时可将全树叶片吃光。
         * Method : 在毒蛾类害虫发生的年份，毒蛾的卵一般大量集中的石崖下、树干、草丛等处，卵期长达9个月，所以容易人工采集并集中销毁。
         */

        @SerializedName("HexapodID")
        private String HexapodID;
        @SerializedName("HexapodType")
        private int HexapodType;
        @SerializedName("HexapodName")
        private String HexapodName;
        @SerializedName("Trait")
        private String Trait;
        @SerializedName("Method")
        private String Method;

        public Pest toPest() {
            return new Pest(null, HexapodID, HexapodType, HexapodName,
                    Trait, Method);
        }

        public String getHexapodID() {
            return HexapodID;
        }

        public void setHexapodID(String HexapodID) {
            this.HexapodID = HexapodID;
        }

        public int getHexapodType() {
            return HexapodType;
        }

        public void setHexapodType(int HexapodType) {
            this.HexapodType = HexapodType;
        }

        public String getHexapodName() {
            return HexapodName;
        }

        public void setHexapodName(String HexapodName) {
            this.HexapodName = HexapodName;
        }

        public String getTrait() {
            return Trait;
        }

        public void setTrait(String Trait) {
            this.Trait = Trait;
        }

        public String getMethod() {
            return Method;
        }

        public void setMethod(String Method) {
            this.Method = Method;
        }
    }
}
