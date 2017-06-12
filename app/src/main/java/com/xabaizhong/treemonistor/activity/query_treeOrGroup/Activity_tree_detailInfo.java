package com.xabaizhong.treemonistor.activity.query_treeOrGroup;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.base_data.Activity_pic_vp;
import com.xabaizhong.treemonistor.activity.monitor_query.Activity_monitor_query_date_pics;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.base.App;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.entity.AreaCode;
import com.xabaizhong.treemonistor.entity.AreaCodeDao;
import com.xabaizhong.treemonistor.myview.C_info_gather_item1;
import com.xabaizhong.treemonistor.service.WebserviceHelper;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 2017/2/28.
 */

public class Activity_tree_detailInfo extends Activity_base {
    String treeId;
    String date;
    //    @BindView(R.id.text1)
//    TextView text1;
    @BindView(R.id.layout)
    LinearLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_detail_info);
        ButterKnife.bind(this);
        treeId = getIntent().getStringExtra("treeId");
        date = getIntent().getStringExtra("date");
        query();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }

    Disposable disposable;

    private void query() {
        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                String result = null;
                try {
                    result = WebserviceHelper.GetWebService(
                            "DataQuerySys", "TreeDelInfo1", getParms());
                } catch (Exception ex) {
                    e.onError(ex);
                }
                if (result == null) {
                    e.onError(new RuntimeException("返回为空"));
                } else {
                    e.onNext(result);
                }
                e.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(String value) {
                        Log.i(TAG, "onNext: " + value);
                        ResultMessage rm = new Gson().fromJson(value, ResultMessage.class);
                        if (rm.getErrorCode() == 0) {
                            getTreeInfo(rm);
//                                            text1.setText(getTreeInfo(rm));
                        } else {
                            showToast(rm.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        disposable = null;
                    }
                });
    }

    /* <UserID>string</UserID>
      <TreeType>int</TreeType>
      <TreeID>string</TreeID>
      <AreaID>string</AreaID>*/
    private Map<String, Object> getParms() {
        Map<String, Object> map = new HashMap<>();
        map.put("UserID", sharedPreferences.getString(UserSharedField.USERID, ""));
        map.put("TreeType", 0);
        map.put("TreeID", treeId);
        map.put("Date", date);
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

    public void getTreeInfo(ResultMessage rm) {
        ResultMessage.ResultBean tree = rm.result;
        int grownIndex = Integer.parseInt(tree.getGrowth()) - 1;
        String grownth = getResources().getStringArray(R.array.growth)[grownIndex];

        int environmentIndex = Integer.parseInt(tree.getEnviorment()) - 1;
        String environment = getResources().getStringArray(R.array.environment)[environmentIndex];

        int statusIndex = Integer.parseInt(tree.getTreeStatus()) - 1;
        String status = getResources().getStringArray(R.array.status)[statusIndex];

        int specialIndex = Integer.parseInt(tree.getSpecial());
        String special = getResources().getStringArray(R.array.special)[specialIndex];

        int gsbzIndex = tree.getTreetype();
        String gsbz = getResources().getStringArray(R.array.gsbz)[gsbzIndex];

        int ownIndex = Integer.parseInt(tree.getOwner()) - 1;
        String owner = getResources().getStringArray(R.array.owner)[ownIndex];

        int aspectIndex = Integer.parseInt(tree.getAspect());
        String aspect = getResources().getStringArray(R.array.aspect)[aspectIndex];

        int slopeIndex = Integer.parseInt(tree.getSlope());
        String slope = getResources().getStringArray(R.array.slope)[slopeIndex];

        int slopePosIndex = Integer.parseInt(tree.getSlopepos());
        String slopePos = getResources().getStringArray(R.array.slope_pos)[slopePosIndex];

        int soilIndex = Integer.parseInt(tree.getSoil());
        String soil = getResources().getStringArray(R.array.soil)[soilIndex];

        int grownSpaceIndex = Integer.parseInt(tree.getGrownspace());
        String growSpace = getResources().getStringArray(R.array.grown_space)[grownSpaceIndex];

        int treeAreaIndex = tree.getTreearea();
        String treeArea = getResources().getStringArray(R.array.tree_area)[treeAreaIndex];

        String protecteArrayIndex = tree.getProtected();
        StringBuilder protecteSbu = new StringBuilder();
        String[] protectArray = getResources().getStringArray(R.array.protect);
        Pattern proPattern = Pattern.compile("\\d");
        Matcher proM = proPattern.matcher(protecteArrayIndex);
        while (proM.find()) {
            final int i = Integer.parseInt(proM.group());
            protecteSbu.append(protectArray[i - 1]).append(",");
        }
        String protect = protecteSbu.toString();

        String recoveryArrayIndex = tree.getRecovery();
        StringBuilder recoverySbu = new StringBuilder();
        String[] recoveryArray = getResources().getStringArray(R.array.recovery);
        Pattern recPattern = Pattern.compile("\\d");
        Matcher recM = recPattern.matcher(recoveryArrayIndex);
        while (recM.find()) {
            final int i = Integer.parseInt(recM.group());
            recoverySbu.append(recoveryArray[i - 1]).append(",");
        }

        String recovery = recoverySbu.toString();
        addView("古树编号：", rm.treeid);
        addView("位置：", Activity_tree_detailInfo.this.getAreaName(rm.areaid));
        addView("地址：", rm.result.town + rm.result.village + rm.result.smallname);
        addView("日期", rm.result.RecordTime);
        addView("树高(m)：", rm.result.getTreeheight() + "");
        addView("胸径(cm)：", rm.result.getTreeDBH() + "");
        addView("经度：", rm.result.getOrdinate());
        addView("纬度：", rm.result.getAbscissa());
        addView("树龄：", rm.result.getRealage() + "(估测树龄:" + rm.result.getGuessage() + ")");
        addView("生长势：", grownth);
        addView("生长环境：", environment);
        addView("状况：", status);
        addView("散生群状：", special);
        addView("古树标识：", gsbz);
        addView("权属：", owner);
        addView("生长场所：", growSpace);
        addView("生长地：", treeArea);
        addView("坡向：", aspect);
        addView("坡位：", slopePos);
        addView("坡度：", slope);
        addView("土壤：", soil);
        addView("保护现状：", protect);
        addView("养护现状：", recovery);
        addView("管护人/单位：", rm.result.getManagementunit() + tree.getManagementpersion());
        addView("记录人：", rm.result.getUserID());
        addPicView(rm.result);
    }

    private void addPicView(final ResultMessage.ResultBean bean) {
        View view = getView("图片", bean.getPicInfo() != null ? bean.getPicInfo().size() + "" : "0");
        C_info_gather_item1 cv = (C_info_gather_item1) view.findViewById(R.id.cv);
        cv.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                Log.i(TAG, "onClickListener: pic");
                Intent i = new Intent(Activity_tree_detailInfo.this, Activity_pic_vp.class);
                i.putStringArrayListExtra("picList", bean.getPicInfo());
                startActivity(i);
            }
        });
        layout.addView(view);
    }

//    int index = 0;
   /* LinearLayout linearLayout;

    private void initLinearLayout() {
        linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
    }*/

    private void addView(String left, String mid) {
        layout.addView(getView(left, mid));
    }


    LayoutInflater inflater;

    private View getView(String left, String mid) {
        if (inflater == null) {
            inflater = LayoutInflater.from(this);
        }

        View view = inflater.inflate(R.layout.c_view, null);
        C_info_gather_item1 cv = (C_info_gather_item1) view.findViewById(R.id.cv);
        cv.setLeftText(left);
        cv.setText(mid);
        return view;
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
            @SerializedName("soil")
            private String soil;
            @SerializedName("aspect")
            private String aspect;
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
            @SerializedName("picInfo")
            private ArrayList<String> picInfo;

            public ArrayList<String> getPicInfo() {
                return picInfo;
            }

            public void setPicInfo(ArrayList<String> picInfo) {
                this.picInfo = picInfo;
            }

            public String getSoil() {
                return soil;
            }

            public void setSoil(String soil) {
                this.soil = soil;
            }

            public String getAspect() {
                return aspect;
            }

            public void setAspect(String aspect) {
                this.aspect = aspect;
            }

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
