package com.xabaizhong.treemonistor.entity.json_entity;

import com.google.gson.annotations.SerializedName;
import com.xabaizhong.treemonistor.entity.Tree_weak_part;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18 0018.
 */

public class Json_tree_weak_part {

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
         * HexapodPartID : 1
         * Name : 叶
         */

        @SerializedName("HexapodPartID")
        private int HexapodPartID;
        @SerializedName("Name")
        private String Name;

        public int getHexapodPartID() {
            return HexapodPartID;
        }

        public void setHexapodPartID(int HexapodPartID) {
            this.HexapodPartID = HexapodPartID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public Tree_weak_part toTreePart() {
            return new Tree_weak_part(null, this.HexapodPartID, this.Name);
        }

    }
}
