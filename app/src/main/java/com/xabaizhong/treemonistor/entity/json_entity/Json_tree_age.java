package com.xabaizhong.treemonistor.entity.json_entity;

import com.google.gson.annotations.SerializedName;
import com.xabaizhong.treemonistor.entity.Tree_age;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18 0018.
 */

public class Json_tree_age {

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
         * TreeSpeID : T00222
         * TreeBJ : 20
         * TreeAge : 2
         * TreeAgeNum : 0
         * CHName : 槐树
         */

        @SerializedName("TreeSpeID")
        private String TreeSpeID;
        @SerializedName("TreeBJ")
        private int TreeBJ;
        @SerializedName("TreeAge")
        private int TreeAge;
        @SerializedName("TreeAgeNum")
        private int TreeAgeNum;
        @SerializedName("CHName")
        private String CHName;

        public String getTreeSpeID() {
            return TreeSpeID;
        }

        public void setTreeSpeID(String TreeSpeID) {
            this.TreeSpeID = TreeSpeID;
        }

        public int getTreeBJ() {
            return TreeBJ;
        }

        public void setTreeBJ(int TreeBJ) {
            this.TreeBJ = TreeBJ;
        }

        public int getTreeAge() {
            return TreeAge;
        }

        public void setTreeAge(int TreeAge) {
            this.TreeAge = TreeAge;
        }

        public int getTreeAgeNum() {
            return TreeAgeNum;
        }

        public void setTreeAgeNum(int TreeAgeNum) {
            this.TreeAgeNum = TreeAgeNum;
        }

        public String getCHName() {
            return CHName;
        }

        public void setCHName(String CHName) {
            this.CHName = CHName;
        }

        public Tree_age convertToTreeAge() {
            return new Tree_age(null, CHName, TreeBJ, TreeAge, TreeAgeNum);
        }

    }
}
