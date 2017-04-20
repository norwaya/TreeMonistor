package com.xabaizhong.treemonistor.entity.json_entity;

import com.google.gson.annotations.SerializedName;
import com.xabaizhong.treemonistor.entity.Weakness_f1;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18 0018.
 */

public class Json_tree_f1 {

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
         * Feature1ID : 1
         * Name : 叶片上产生斑点或斑块
         */

        @SerializedName("Feature1ID")
        private int Feature1ID;
        @SerializedName("Name")
        private String Name;

        public int getFeature1ID() {
            return Feature1ID;
        }

        public void setFeature1ID(int Feature1ID) {
            this.Feature1ID = Feature1ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public Weakness_f1 toWeaknessF1() {
            return new Weakness_f1(null, this.Feature1ID, this.Name);
        }
    }
}
