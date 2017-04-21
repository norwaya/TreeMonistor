package com.xabaizhong.treemonistor.entity.json_entity;

import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.xabaizhong.treemonistor.entity.Weakness_f2;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18 0018.
 */

public class Json_tree_f2 {


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
         * f1 : 1
         * Feature2ID : 1
         * Name : 初期在叶背产生白色粉状斑点，后扩展为近圆形或不规则形白色粉状斑，叶片正面呈现不规则形褪绿斑。后期在粉状斑上散生黑色颗粒状小粒点。
         */

        @SerializedName("f1")
        private int f1;
        @SerializedName("Feature2ID")
        private int Feature2ID;
        @SerializedName("Name")
        private String Name;

        public int getF1() {
            return f1;
        }

        public void setF1(int f1) {
            this.f1 = f1;
        }

        public int getFeature2ID() {
            return Feature2ID;
        }

        public void setFeature2ID(int Feature2ID) {
            this.Feature2ID = Feature2ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public Weakness_f2 toWeaknessF2() {
            Log.i("f2", "toWeaknessF2: "+this.f1 +"\t"+this.Feature2ID);
            return new Weakness_f2(null, this.f1, this.Feature2ID, this.Name);
        }
    }
}
