package com.xabaizhong.treemonistor.dbhelper;

import com.xabaizhong.treemonistor.entity.Pest;

import java.util.List;

/**
 * Created by admin on 2017/3/17.
 */

public class PestHelper {

    private List<RECORDSBean> RECORDS;

    public List<RECORDSBean> getRECORDS() {
        return RECORDS;
    }

    public void setRECORDS(List<RECORDSBean> RECORDS) {
        this.RECORDS = RECORDS;
    }

    public static class RECORDSBean {
        /**
         * pest_name : 活辣子
         * pest_type : 1
         * pest_explain : 一种躯体像树叶颜色的有毒虫子。幼虫肥短，蛞蝓状。无腹足，代以吸盘。行动时不是爬行而是滑行。幼虫体色鲜艳，人碰到虫身上的有毒刺毛就会被蛰，并引起皮疹，被扎后有疼、痒、辛、辣、麻、热等感觉，可伴随长时间肿胀。
         * harm_peculiarity : 以植物为食。在卵圆形的茧中化蛹，茧附著在叶间
         * step : 可以喷洒1500倍液的氯氰菊酯或1000倍液的辛硫磷乳油防治。
         */

        private String pest_name;
        private int pest_type;
        private String pest_explain;
        private String harm_peculiarity;
        private String step;

        public String getPest_name() {
            return pest_name;
        }

        public void setPest_name(String pest_name) {
            this.pest_name = pest_name;
        }

        public int getPest_type() {
            return pest_type;
        }

        public void setPest_type(int pest_type) {
            this.pest_type = pest_type;
        }

        public String getPest_explain() {
            return pest_explain;
        }

        public void setPest_explain(String pest_explain) {
            this.pest_explain = pest_explain;
        }

        public String getHarm_peculiarity() {
            return harm_peculiarity;
        }

        public void setHarm_peculiarity(String harm_peculiarity) {
            this.harm_peculiarity = harm_peculiarity;
        }

        public String getStep() {
            return step;
        }

        public void setStep(String step) {
            this.step = step;
        }
        public Pest toPest(){
            return new Pest(null,pest_name,pest_type,pest_explain,harm_peculiarity,step);
        }
    }
}
