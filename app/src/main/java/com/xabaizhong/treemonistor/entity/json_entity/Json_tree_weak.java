package com.xabaizhong.treemonistor.entity.json_entity;

import com.google.gson.annotations.SerializedName;
import com.xabaizhong.treemonistor.entity.Weakness;

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
         * TID : 1
         * illName : 白粉病
         * Trait : 叶片上出现不规则白色粉状霉斑，病斑上散生黄色闭囊壳，后期闭囊壳变为黑色。发病严重时，叶色变黄，叶片卷曲。
         * Method : 1.采收后彻底清扫落叶，集中烧毁。
         2.树发芽前喷一次40%福美胂可湿性粉剂100倍液，或5波美度石硫合剂、70%多硫化钡（索利巴尔）50～80倍液。
         3.发现病斑后立即喷施25％粉锈灵800～100倍液，或50％三唑酮.硫悬浮剂600～750倍液、25%晴菌唑乳油5000～9000倍液、70％甲基托布津可湿性粉剂800～1000倍液。
         * Part : 1
         * Feature : 叶片上产生斑点或斑块：初期在叶背产生白色粉状斑点，后扩展为近圆形或不规则形白色粉状斑，叶片正面呈现不规则形褪绿斑。后期在粉状斑上散生黑色颗粒状小粒点。
         * Name : 叶部
         */

        @SerializedName("TID")
        private int TID;
        @SerializedName("illName")
        private String illName;
        @SerializedName("Trait")
        private String Trait;
        @SerializedName("Method")
        private String Method;
        @SerializedName("Part")
        private int Part;
        @SerializedName("Feature")
        private String Feature;
        @SerializedName("Name")
        private String Name;

        public int getTID() {
            return TID;
        }

        public void setTID(int TID) {
            this.TID = TID;
        }

        public String getIllName() {
            return illName;
        }

        public void setIllName(String illName) {
            this.illName = illName;
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

        public int getPart() {
            return Part;
        }

        public void setPart(int Part) {
            this.Part = Part;
        }

        public String getFeature() {
            return Feature;
        }

        public void setFeature(String Feature) {
            this.Feature = Feature;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public Weakness convertToWeak() {
            return new Weakness(null, TID, illName, Trait, Method, Part, Feature, Name);
        }

    }
}
