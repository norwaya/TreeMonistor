package com.xabaizhong.treemonistor.activity.query_treeOrGroup;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.base.App;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.entity.AreaCode;
import com.xabaizhong.treemonistor.entity.AreaCodeDao;
import com.xabaizhong.treemonistor.service.WebserviceHelper;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 2017/2/28.
 */

public class Activity_tree_detailInfo extends Activity_base {
    String treeId;
    @BindView(R.id.text1)
    TextView text1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_detail_info);
        ButterKnife.bind(this);
        treeId = getIntent().getStringExtra("treeId");
        query();

    }

    AsyncTask asyncTask;

    private void query() {
        asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    return WebserviceHelper.GetWebService(
                            "DataQuerySys", "TreeDelInfo", getParms());
                } catch (ConnectException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                Log.i(TAG, "onPostExecute: " + s);
                if (s == null) {
                    showToast("获取古树信息失败");
                    return;
                }

                Observable.just(s)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                new Consumer<String>() {
                                    @Override
                                    public void accept(String s) throws Exception {
                                        ResultMessage rm = new Gson().fromJson(s, ResultMessage.class);
                                        if (rm.getErrorCode() == 0) {
                                            text1.setText(getTreeInfo(rm));
                                        } else {
                                            showToast(rm.getMessage());
                                        }
                                    }
                                },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        showToast("解析信息失败");
                                    }
                                });
            }
        }.execute();
    }

    /* <UserID>string</UserID>
      <TreeType>int</TreeType>
      <TreeID>string</TreeID>
      <AreaID>string</AreaID>*/
    private Map<String, Object> getParms() {

        /* <UserID>string</UserID>
      <TreeType>int</TreeType>
      <TreeID>string</TreeID>
      <AreaID>string</AreaID>*/

        Map<String, Object> map = new HashMap<>();
        map.put("UserID", sharedPreferences.getString(UserSharedField.USERID, ""));
        map.put("TreeType", 0);
        map.put("TreeID", treeId);
        map.put("AreaID", sharedPreferences.getString(UserSharedField.AREAID, ""));
        return map;
    }

    private String getAreaName(String areaId) {
        AreaCodeDao areaCodeDao = ((App) getApplication()).getDaoSession().getAreaCodeDao();
        List<AreaCode> list = areaCodeDao.queryBuilder().where(AreaCodeDao.Properties.AreaId.eq(areaId)).build().list();
        if (list.size() > 0) {
            return list.get(0).getMergerName();
        }
        return "";
    }

    public String getTreeInfo(ResultMessage rm) {
        return "古树编号：" + rm.treeid + "\n" +
                "位置：" + Activity_tree_detailInfo.this.getAreaName(rm.areaid) + "\n" +
                "地址：" + rm.result.town + rm.result.village + rm.result.smallname + "\n" +
                "树高(m)：" + rm.result.getTreeheight() + "\n" +
                "树高(m)：" + rm.result.getTreeheight() + "\n" +
                "胸径(cm)：" + rm.result.getTreeDBH() + "\n" +
                "东西冠幅：" + rm.result.getCrownew() + "\n" +
                "南北冠幅：" + rm.result.getCrownew() + "\n" +
                "记录人：" + rm.result.getUserID();
    }

    static class ResultMessage {


        /**
         * message : sus
         * error_code : 0
         * treeid : 61011200010
         * areaid : 610112
         * userid : test1
         * treetype : 0
         * result : {"treeid":"61011200010","treearea":0,"treetype":2,"treespeid":"T00025","town":"2","village":"1","smallname":"丈八一路SOHO东南107米","ordinate":"108.89134199999988","abscissa":"34.201390077101095","specialcode":0,"treeheight":14.22,"treeDBH":14.11,"crownavg":21.1,"crownew":14.55,"crownns":14.44,"managementunit":"2828","managementpersion":"J2i2","treehistory":"2k2k","grownspace":"2","special":"2","growth":"3","enviorment":"2","slopepos":"4","enviorfactor":"Wkk2","specstatdesc":"Kwk2","specdesc":"K2k2","explain":"","realage":22.14,"guessage":14.55,"evevation":1.2,"Slope":"5","Protected":"BH12","Recovery":"YH12","Owner":"2","TreeLevel":"2","TreeStatus":"2","UserID":"test1","RecordTime":"2017/4/27 16:45:10","IVST":"0","TypeID":0,"Emphasis":0,"Audit":0,"AreaID":"610112"}
         */

        @SerializedName("message")
        private String message;
        @SerializedName("error_code")
        private int errorCode;
        @SerializedName("treeid")
        private String treeid;
        @SerializedName("areaid")
        private String areaid;
        @SerializedName("userid")
        private String userid;
        @SerializedName("treetype")
        private int treetype;
        @SerializedName("result")
        private ResultBean result;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }

        public String getTreeid() {
            return treeid;
        }

        public void setTreeid(String treeid) {
            this.treeid = treeid;
        }

        public String getAreaid() {
            return areaid;
        }

        public void setAreaid(String areaid) {
            this.areaid = areaid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public int getTreetype() {
            return treetype;
        }

        public void setTreetype(int treetype) {
            this.treetype = treetype;
        }

        public ResultBean getResult() {
            return result;
        }

        public void setResult(ResultBean result) {
            this.result = result;
        }

        public static class ResultBean {
            /**
             * treeid : 61011200010
             * treearea : 0
             * treetype : 2
             * treespeid : T00025
             * town : 2
             * village : 1
             * smallname : 丈八一路SOHO东南107米
             * ordinate : 108.89134199999988
             * abscissa : 34.201390077101095
             * specialcode : 0
             * treeheight : 14.22
             * treeDBH : 14.11
             * crownavg : 21.1
             * crownew : 14.55
             * crownns : 14.44
             * managementunit : 2828
             * managementpersion : J2i2
             * treehistory : 2k2k
             * grownspace : 2
             * special : 2
             * growth : 3
             * enviorment : 2
             * slopepos : 4
             * enviorfactor : Wkk2
             * specstatdesc : Kwk2
             * specdesc : K2k2
             * explain :
             * realage : 22.14
             * guessage : 14.55
             * evevation : 1.2
             * Slope : 5
             * Protected : BH12
             * Recovery : YH12
             * Owner : 2
             * TreeLevel : 2
             * TreeStatus : 2
             * UserID : test1
             * RecordTime : 2017/4/27 16:45:10
             * IVST : 0
             * TypeID : 0
             * Emphasis : 0
             * Audit : 0
             * AreaID : 610112
             */

            @SerializedName("treeid")
            private String treeid;
            @SerializedName("treearea")
            private int treearea;
            @SerializedName("treetype")
            private int treetype;
            @SerializedName("treespeid")
            private String treespeid;
            @SerializedName("town")
            private String town;
            @SerializedName("village")
            private String village;
            @SerializedName("smallname")
            private String smallname;
            @SerializedName("ordinate")
            private String ordinate;
            @SerializedName("abscissa")
            private String abscissa;
            @SerializedName("specialcode")
            private int specialcode;
            @SerializedName("treeheight")
            private double treeheight;
            @SerializedName("treeDBH")
            private double treeDBH;
            @SerializedName("crownavg")
            private double crownavg;
            @SerializedName("crownew")
            private double crownew;
            @SerializedName("crownns")
            private double crownns;
            @SerializedName("managementunit")
            private String managementunit;
            @SerializedName("managementpersion")
            private String managementpersion;
            @SerializedName("treehistory")
            private String treehistory;
            @SerializedName("grownspace")
            private String grownspace;
            @SerializedName("special")
            private String special;
            @SerializedName("growth")
            private String growth;
            @SerializedName("enviorment")
            private String enviorment;
            @SerializedName("slopepos")
            private String slopepos;
            @SerializedName("enviorfactor")
            private String enviorfactor;
            @SerializedName("specstatdesc")
            private String specstatdesc;
            @SerializedName("specdesc")
            private String specdesc;
            @SerializedName("explain")
            private String explain;
            @SerializedName("realage")
            private double realage;
            @SerializedName("guessage")
            private double guessage;
            @SerializedName("evevation")
            private double evevation;
            @SerializedName("Slope")
            private String Slope;
            @SerializedName("Protected")
            private String Protected;
            @SerializedName("Recovery")
            private String Recovery;
            @SerializedName("Owner")
            private String Owner;
            @SerializedName("TreeLevel")
            private String TreeLevel;
            @SerializedName("TreeStatus")
            private String TreeStatus;
            @SerializedName("UserID")
            private String UserID;
            @SerializedName("RecordTime")
            private String RecordTime;
            @SerializedName("IVST")
            private String IVST;
            @SerializedName("TypeID")
            private int TypeID;
            @SerializedName("Emphasis")
            private int Emphasis;
            @SerializedName("Audit")
            private int Audit;
            @SerializedName("AreaID")
            private String AreaID;

            public String getTreeid() {
                return treeid;
            }

            public void setTreeid(String treeid) {
                this.treeid = treeid;
            }

            public int getTreearea() {
                return treearea;
            }

            public void setTreearea(int treearea) {
                this.treearea = treearea;
            }

            public int getTreetype() {
                return treetype;
            }

            public void setTreetype(int treetype) {
                this.treetype = treetype;
            }

            public String getTreespeid() {
                return treespeid;
            }

            public void setTreespeid(String treespeid) {
                this.treespeid = treespeid;
            }

            public String getTown() {
                return town;
            }

            public void setTown(String town) {
                this.town = town;
            }

            public String getVillage() {
                return village;
            }

            public void setVillage(String village) {
                this.village = village;
            }

            public String getSmallname() {
                return smallname;
            }

            public void setSmallname(String smallname) {
                this.smallname = smallname;
            }

            public String getOrdinate() {
                return ordinate;
            }

            public void setOrdinate(String ordinate) {
                this.ordinate = ordinate;
            }

            public String getAbscissa() {
                return abscissa;
            }

            public void setAbscissa(String abscissa) {
                this.abscissa = abscissa;
            }

            public int getSpecialcode() {
                return specialcode;
            }

            public void setSpecialcode(int specialcode) {
                this.specialcode = specialcode;
            }

            public double getTreeheight() {
                return treeheight;
            }

            public void setTreeheight(double treeheight) {
                this.treeheight = treeheight;
            }

            public double getTreeDBH() {
                return treeDBH;
            }

            public void setTreeDBH(double treeDBH) {
                this.treeDBH = treeDBH;
            }

            public double getCrownavg() {
                return crownavg;
            }

            public void setCrownavg(double crownavg) {
                this.crownavg = crownavg;
            }

            public double getCrownew() {
                return crownew;
            }

            public void setCrownew(double crownew) {
                this.crownew = crownew;
            }

            public double getCrownns() {
                return crownns;
            }

            public void setCrownns(double crownns) {
                this.crownns = crownns;
            }

            public String getManagementunit() {
                return managementunit;
            }

            public void setManagementunit(String managementunit) {
                this.managementunit = managementunit;
            }

            public String getManagementpersion() {
                return managementpersion;
            }

            public void setManagementpersion(String managementpersion) {
                this.managementpersion = managementpersion;
            }

            public String getTreehistory() {
                return treehistory;
            }

            public void setTreehistory(String treehistory) {
                this.treehistory = treehistory;
            }

            public String getGrownspace() {
                return grownspace;
            }

            public void setGrownspace(String grownspace) {
                this.grownspace = grownspace;
            }

            public String getSpecial() {
                return special;
            }

            public void setSpecial(String special) {
                this.special = special;
            }

            public String getGrowth() {
                return growth;
            }

            public void setGrowth(String growth) {
                this.growth = growth;
            }

            public String getEnviorment() {
                return enviorment;
            }

            public void setEnviorment(String enviorment) {
                this.enviorment = enviorment;
            }

            public String getSlopepos() {
                return slopepos;
            }

            public void setSlopepos(String slopepos) {
                this.slopepos = slopepos;
            }

            public String getEnviorfactor() {
                return enviorfactor;
            }

            public void setEnviorfactor(String enviorfactor) {
                this.enviorfactor = enviorfactor;
            }

            public String getSpecstatdesc() {
                return specstatdesc;
            }

            public void setSpecstatdesc(String specstatdesc) {
                this.specstatdesc = specstatdesc;
            }

            public String getSpecdesc() {
                return specdesc;
            }

            public void setSpecdesc(String specdesc) {
                this.specdesc = specdesc;
            }

            public String getExplain() {
                return explain;
            }

            public void setExplain(String explain) {
                this.explain = explain;
            }

            public double getRealage() {
                return realage;
            }

            public void setRealage(double realage) {
                this.realage = realage;
            }

            public double getGuessage() {
                return guessage;
            }

            public void setGuessage(double guessage) {
                this.guessage = guessage;
            }

            public double getEvevation() {
                return evevation;
            }

            public void setEvevation(double evevation) {
                this.evevation = evevation;
            }

            public String getSlope() {
                return Slope;
            }

            public void setSlope(String Slope) {
                this.Slope = Slope;
            }

            public String getProtected() {
                return Protected;
            }

            public void setProtected(String Protected) {
                this.Protected = Protected;
            }

            public String getRecovery() {
                return Recovery;
            }

            public void setRecovery(String Recovery) {
                this.Recovery = Recovery;
            }

            public String getOwner() {
                return Owner;
            }

            public void setOwner(String Owner) {
                this.Owner = Owner;
            }

            public String getTreeLevel() {
                return TreeLevel;
            }

            public void setTreeLevel(String TreeLevel) {
                this.TreeLevel = TreeLevel;
            }

            public String getTreeStatus() {
                return TreeStatus;
            }

            public void setTreeStatus(String TreeStatus) {
                this.TreeStatus = TreeStatus;
            }

            public String getUserID() {
                return UserID;
            }

            public void setUserID(String UserID) {
                this.UserID = UserID;
            }

            public String getRecordTime() {
                return RecordTime;
            }

            public void setRecordTime(String RecordTime) {
                this.RecordTime = RecordTime;
            }

            public String getIVST() {
                return IVST;
            }

            public void setIVST(String IVST) {
                this.IVST = IVST;
            }

            public int getTypeID() {
                return TypeID;
            }

            public void setTypeID(int TypeID) {
                this.TypeID = TypeID;
            }

            public int getEmphasis() {
                return Emphasis;
            }

            public void setEmphasis(int Emphasis) {
                this.Emphasis = Emphasis;
            }

            public int getAudit() {
                return Audit;
            }

            public void setAudit(int Audit) {
                this.Audit = Audit;
            }

            public String getAreaID() {
                return AreaID;
            }

            public void setAreaID(String AreaID) {
                this.AreaID = AreaID;
            }
        }
    }
}
