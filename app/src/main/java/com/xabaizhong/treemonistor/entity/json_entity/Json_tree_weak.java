package com.xabaizhong.treemonistor.entity.json_entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18 0018.
 */

public class Json_tree_weak {

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
         * TreeIllID : 1
         * illName : 白粉病
         * Part : 1
         * expression1 : 1
         * expression2 : 1
         * Trait : 叶片上出现不规则白色粉状霉斑，病斑上散生黄色闭囊壳，后期闭囊壳变为黑色。发病严重时，叶色变黄，叶片卷曲。
         * Method : 1.采收后彻底清扫落叶，集中烧毁。
         2.树发芽前喷一次40%福美胂可湿性粉剂100倍液，或5波美度石硫合剂、70%多硫化钡（索利巴尔）50～80倍液。
         3.发现病斑后立即喷施25％粉锈灵800～100倍液，或50％三唑酮.硫悬浮剂600～750倍液、25%晴菌唑乳油5000～9000倍液、70％甲基托布津可湿性粉剂800～1000倍液。
         */

        @SerializedName("TreeIllID")
        private int TreeIllID;
        @SerializedName("illName")
        private String illName;
        @SerializedName("Part")
        private int Part;
        @SerializedName("expression1")
        private int expression1;
        @SerializedName("expression2")
        private int expression2;
        @SerializedName("Trait")
        private String Trait;
        @SerializedName("Method")
        private String Method;

        public int getTreeIllID() {
            return TreeIllID;
        }

        public void setTreeIllID(int TreeIllID) {
            this.TreeIllID = TreeIllID;
        }

        public String getIllName() {
            return illName;
        }

        public void setIllName(String illName) {
            this.illName = illName;
        }

        public int getPart() {
            return Part;
        }

        public void setPart(int Part) {
            this.Part = Part;
        }

        public int getExpression1() {
            return expression1;
        }

        public void setExpression1(int expression1) {
            this.expression1 = expression1;
        }

        public int getExpression2() {
            return expression2;
        }

        public void setExpression2(int expression2) {
            this.expression2 = expression2;
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
