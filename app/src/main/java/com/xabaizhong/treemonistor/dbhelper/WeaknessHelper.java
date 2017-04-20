package com.xabaizhong.treemonistor.dbhelper;

import com.xabaizhong.treemonistor.entity.Weakness;

import java.util.List;

/**
 * Created by admin on 2017/3/17.
 */

public class WeaknessHelper {

    private List<RECORDSBean> RECORDS;

    public List<RECORDSBean> getRECORDS() {
        return RECORDS;
    }

    public void setRECORDS(List<RECORDSBean> RECORDS) {
        this.RECORDS = RECORDS;
    }

    public static class RECORDSBean {
        /**
         * illness_name : 槐树腐烂病
         * illness_symptom : 苗木感病后在绿色树皮上初期出现黄褐色水渍状病斑，近圆形，逐渐扩展为梭形，中央稍凹陷。如病斑环绕树的主干，上部即行枯死，未环绕主干的当年多能愈合。
         * harm_peculiarity : 此病主要危害槐树、龙爪槐的苗木和幼树的树皮，多危害主干下部，造成树势衰弱，以至死亡。这种病害主要分布于河北、河南、江苏、天津及北京。
         * step : 加强肥、水等养护管理，特别是新移栽的幼苗、幼树，根部不要暴露时间太长，要及时浇水，促使树木生长健壮，防止叶蝉产卵，注意保护各种伤口，防止或减少病菌侵染。早春树干涂白(生石灰5千克，硫磺粉1．5千克，水36千克)，防止病菌侵染。
         */

        private String illness_name;
        private String illness_symptom;
        private String harm_peculiarity;
        private String step;

        public String getIllness_name() {
            return illness_name;
        }

        public void setIllness_name(String illness_name) {
            this.illness_name = illness_name;
        }

        public String getIllness_symptom() {
            return illness_symptom;
        }

        public void setIllness_symptom(String illness_symptom) {
            this.illness_symptom = illness_symptom;
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

        public Weakness toWeakness() {
//            return new Weakness(null, illness_name, illness_symptom, harm_peculiarity, step);
            return null;
        }

    }
}
