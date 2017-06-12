package com.xabaizhong.treemonistor.activity.query_treeOrGroup;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.base_data.Activity_pic_vp;
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

public class Activity_tree_group_detailInfo extends Activity_base {
    String treeId;
    String date;

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
                            "DataQuerySys", "TreeDelInfo1", getParams());
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

                    }

                    @Override
                    public void onNext(String value) {
                        ResultMessage rm = new Gson().fromJson(value, ResultMessage.class);
                        if (rm.getErrorCode() == 0) {
                            getTreeInfo(rm);
                        } else {
                            showToast(rm.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        disposable = null;
                    }
                });
    }

    private Map<String, Object> getParams() {

        /* <UserID>string</UserID>
      <TreeType>int</TreeType>
      <TreeID>string</TreeID>
      <AreaID>string</AreaID>*/

        Map<String, Object> map = new HashMap<>();
        map.put("UserID", sharedPreferences.getString(UserSharedField.USERID, ""));
        map.put("TreeType", 1);
        map.put("Date", date);
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

    public void getTreeInfo(ResultMessage rm) {
        ResultMessage.ResultBean tree = rm.result;
        addView("古树群编号", tree.getTreeListID());
        addView("位置", Activity_tree_group_detailInfo.this.getAreaName(rm.areaid));
        addView("详细信息", tree.getPlaceName());
        addView("日期", tree.RecordTime);
        addView("主要树种", tree.getMainTreeName());
        addView("树种类", tree.getTreeSpelNum());
        addView("四至界限", tree.getSZJX());
        addView("面积", tree.getArea() + "");
        addView("古树数量", tree.getGSTreeNum() + "");
        addView("平均高度", "" + tree.getAverageHeight());
        addView("平均胸径", "" + tree.getAverageDiameter());
        addView("平均年龄", "" + tree.getAverageAge());
        addView("郁闭度", "" + tree.getYBDInfo());
        addView("海拔", "" + tree.getEvevation());
        String aspect = tree.getAspect();
        if (aspect.matches("\\d+")) {
            aspect = getResources().getStringArray(R.array.aspect)[Integer.parseInt(aspect)];
        }
        addView("坡向", "" + aspect);
        String slope = tree.getSlope();
        if (slope.matches("\\d+")) {
            slope = getResources().getStringArray(R.array.slope)[Integer.parseInt(slope)];
        }
        addView("坡度", "" + slope);
        String soilStr = tree.getSoilName();
        if (soilStr.matches("\\d+")) {
            soilStr = getResources().getStringArray(R.array.soil)[Integer.parseInt(soilStr)];
        }
        addView("土壤", "" + soilStr);
        addView("土壤厚度", "" + tree.getSoilHeight());
        addView("下木种类", "" + tree.getXiaMuType());
        addView("下木密度", "" + tree.getXiaMuDensity());
        addView("地被物种类", "" + tree.getDBWType());
        addView("地被物密度", "" + tree.getDBWDensity());
        addView("管理单位", "" + tree.getManagementUnit());
        addView("状态", "" + tree.getManagementState());
        addView("科", "" + tree.getAimsFamily());
        addView("属", "" + tree.getAimsBelong());
        addView("人为经营情况", "" + tree.getRWJYInfo());
        addView("建议", "" + tree.getSuggest());
        addView("记录人", "" + tree.getRecordPerson());
        addPicView(rm.getResult());
        /**
         * ManagementUnit : 西安索菲特人民大厦
         * ManagementState : 根深叶茂
         * AimsTree : 油橄榄
         * AimsFamily : 木犀科
         * AimsBelong : 木犀榄属
         * RWJYInfo : 根深叶茂，树冠完整，主干通直，无伤痕
         * Suggest : (NULL)
         * Explain : ~/Image/TreeGSQInfo/610000/610100/610102/61010201
         * RecordPerson : 李缠生
         * RecordTime : 2016/6/27 0:00:00
         * UserID : 610102001
         */
        /*int grownIndex = Integer.parseInt(tree.getGrowth()) - 1;
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
        String aspect= getResources().getStringArray(R.array.aspect)[aspectIndex];

        int slopeIndex = Integer.parseInt(tree.getSlope());
        String slope = getResources().getStringArray(R.array.slope)[slopeIndex];

        int slopePosIndex = Integer.parseInt(tree.getSlopepos());
        String slopePos = getResources().getStringArray(R.array.slope_pos)[slopePosIndex];

        int soilIndex = Integer.parseInt(tree.getSoil());
        String soil=getResources().getStringArray(R.array.soil)[soilIndex];

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


        return "古树编号：" + rm.treeid + "\n" +
                "位置：" + Activity_tree_group_detailInfo.this.getAreaName(rm.areaid) + "\n" +
                "地址：" + rm.result.town + rm.result.village + rm.result.smallname + "\n" +
                "树高(m)：" + rm.result.getTreeheight() + "\n" +
                "树高(m)：" + rm.result.getTreeheight() + "\n" +
                "胸径(cm)：" + rm.result.getTreeDBH() + "\n" +
                "经度：" + rm.result.getOrdinate() + "\n" +
                "纬度：" + rm.result.getAbscissa() + "\n" +
                "树龄：" + rm.result.getRealage() + "(估测树龄:"+rm.result.getGuessage()+")"+ "\n" +
                "生长势：" + grownth + "\n" +
                "生长环境：" + environment + "\n" +
                "状况：" + status + "\n" +
                "散生群状：" + special + "\n" +
                "古树标识：" + gsbz + "\n" +
                "权属：" + owner + "\n" +
                "生长场所：" + growSpace + "\n" +
                "生长地：" + treeArea + "\n" +
                "坡向：" + aspect + "\n" +
                "坡位：" + slopePos + "\n" +
                "坡度：" + slope + "\n" +
                "土壤：" + soil + "\n" +
                "保护现状：" + protect + "\n" +
                "养护现状：" + recovery + "\n" +
                "管护人/单位：" + rm.result.getManagementunit() + tree.getManagementpersion() + "\n" +
                "记录人：" + rm.result.getUserID();*/
    }

    private void addPicView(final Activity_tree_group_detailInfo.ResultMessage.ResultBean bean) {
        View view = getView("图片", bean.getPicInfo() != null ? bean.getPicInfo().size() + "" : "0");
        C_info_gather_item1 cv = (C_info_gather_item1) view.findViewById(R.id.cv);
        cv.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                Log.i(TAG, "onClickListener: pic");
                Intent i = new Intent(Activity_tree_group_detailInfo.this, Activity_pic_vp.class);
                i.putStringArrayListExtra("picList", bean.getPicInfo());
                startActivity(i);
            }
        });
        layout.addView(view);
    }

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
        Log.i(TAG, "getView: " + view);
        return view;
    }

    static class ResultMessage {

        /**
         * message : sus
         * error_code : 0
         * treeid : 61010201
         * areaid : 610102
         * userid : 610102001
         * treetype : 1
         * result : {"TreeListID":"61010201","PlaceName":"索菲特人民大厦　","MainTreeName":"油橄榄　","TreeSpelNum":"1","SZJX":"西安索菲特人民大厦后楼东西两侧","Area":0,"GSTreeNum":18,"AverageHeight":4.5,"AverageDiameter":35,"AverageAge":55,"YBDInfo":0,"Evevation":"410:410","Aspect":"无坡向","Slope":"0","SoilName":"黄壤","SoilHeight":0,"XiaMuType":"(NULL)","XiaMuDensity":"0","DBWType":"(NULL)","DBWDensity":"0","ManagementUnit":"西安索菲特人民大厦","ManagementState":"根深叶茂","AimsTree":"油橄榄","AimsFamily":"木犀科","AimsBelong":"木犀榄属","RWJYInfo":"根深叶茂，树冠完整，主干通直，无伤痕","Suggest":"(NULL)","Explain":"~/Image/TreeGSQInfo/610000/610100/610102/61010201","RecordPerson":"李缠生","RecordTime":"2016/6/27 0:00:00","UserID":"610102001"}
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
             * TreeListID : 61010201
             * PlaceName : 索菲特人民大厦
             * MainTreeName : 油橄榄
             * TreeSpelNum : 1
             * SZJX : 西安索菲特人民大厦后楼东西两侧
             * Area : 0
             * GSTreeNum : 18
             * AverageHeight : 4.5
             * AverageDiameter : 35
             * AverageAge : 55
             * YBDInfo : 0
             * Evevation : 410:410
             * Aspect : 无坡向
             * Slope : 0
             * SoilName : 黄壤
             * SoilHeight : 0
             * XiaMuType : (NULL)
             * XiaMuDensity : 0
             * DBWType : (NULL)
             * DBWDensity : 0
             * ManagementUnit : 西安索菲特人民大厦
             * ManagementState : 根深叶茂
             * AimsTree : 油橄榄
             * AimsFamily : 木犀科
             * AimsBelong : 木犀榄属
             * RWJYInfo : 根深叶茂，树冠完整，主干通直，无伤痕
             * Suggest : (NULL)
             * Explain : ~/Image/TreeGSQInfo/610000/610100/610102/61010201
             * RecordPerson : 李缠生
             * RecordTime : 2016/6/27 0:00:00
             * UserID : 610102001
             */

            @SerializedName("TreeListID")
            private String TreeListID;
            @SerializedName("PlaceName")
            private String PlaceName;
            @SerializedName("MainTreeName")
            private String MainTreeName;
            @SerializedName("TreeSpelNum")
            private String TreeSpelNum;
            @SerializedName("SZJX")
            private String SZJX;
            @SerializedName("Area")
            private double Area;
            @SerializedName("GSTreeNum")
            private double GSTreeNum;
            @SerializedName("AverageHeight")
            private double AverageHeight;
            @SerializedName("AverageDiameter")
            private double AverageDiameter;
            @SerializedName("AverageAge")
            private double AverageAge;
            @SerializedName("YBDInfo")
            private double YBDInfo;
            @SerializedName("Evevation")
            private String Evevation;
            @SerializedName("Aspect")
            private String Aspect;
            @SerializedName("Slope")
            private String Slope;
            @SerializedName("SoilName")
            private String SoilName;
            @SerializedName("SoilHeight")
            private int SoilHeight;
            @SerializedName("XiaMuType")
            private String XiaMuType;
            @SerializedName("XiaMuDensity")
            private String XiaMuDensity;
            @SerializedName("DBWType")
            private String DBWType;
            @SerializedName("DBWDensity")
            private String DBWDensity;
            @SerializedName("ManagementUnit")
            private String ManagementUnit;
            @SerializedName("ManagementState")
            private String ManagementState;
            @SerializedName("AimsTree")
            private String AimsTree;
            @SerializedName("AimsFamily")
            private String AimsFamily;
            @SerializedName("AimsBelong")
            private String AimsBelong;
            @SerializedName("RWJYInfo")
            private String RWJYInfo;
            @SerializedName("Suggest")
            private String Suggest;
            @SerializedName("Explain")
            private String Explain;
            @SerializedName("RecordPerson")
            private String RecordPerson;
            @SerializedName("RecordTime")
            private String RecordTime;
            @SerializedName("UserID")
            private String UserID;
            @SerializedName("picInfo")
            private ArrayList<String> picInfo;

            public ArrayList<String> getPicInfo() {
                return picInfo;
            }

            public void setPicInfo(ArrayList<String> picInfo) {
                this.picInfo = picInfo;
            }

            public String getTreeListID() {
                return TreeListID;
            }

            public void setTreeListID(String TreeListID) {
                this.TreeListID = TreeListID;
            }

            public String getPlaceName() {
                return PlaceName;
            }

            public void setPlaceName(String PlaceName) {
                this.PlaceName = PlaceName;
            }

            public String getMainTreeName() {
                return MainTreeName;
            }

            public void setMainTreeName(String MainTreeName) {
                this.MainTreeName = MainTreeName;
            }

            public String getTreeSpelNum() {
                return TreeSpelNum;
            }

            public void setTreeSpelNum(String TreeSpelNum) {
                this.TreeSpelNum = TreeSpelNum;
            }

            public String getSZJX() {
                return SZJX;
            }

            public void setSZJX(String SZJX) {
                this.SZJX = SZJX;
            }

            public double getArea() {
                return Area;
            }

            public void setArea(float Area) {
                this.Area = Area;
            }

            public double getGSTreeNum() {
                return GSTreeNum;
            }

            public void setGSTreeNum(int GSTreeNum) {
                this.GSTreeNum = GSTreeNum;
            }

            public double getAverageHeight() {
                return AverageHeight;
            }

            public void setAverageHeight(double AverageHeight) {
                this.AverageHeight = AverageHeight;
            }

            public double getAverageDiameter() {
                return AverageDiameter;
            }

            public void setAverageDiameter(int AverageDiameter) {
                this.AverageDiameter = AverageDiameter;
            }

            public double getAverageAge() {
                return AverageAge;
            }

            public void setAverageAge(int AverageAge) {
                this.AverageAge = AverageAge;
            }

            public double getYBDInfo() {
                return YBDInfo;
            }

            public void setYBDInfo(int YBDInfo) {
                this.YBDInfo = YBDInfo;
            }

            public String getEvevation() {
                return Evevation;
            }

            public void setEvevation(String Evevation) {
                this.Evevation = Evevation;
            }

            public String getAspect() {
                return Aspect;
            }

            public void setAspect(String Aspect) {
                this.Aspect = Aspect;
            }

            public String getSlope() {
                return Slope;
            }

            public void setSlope(String Slope) {
                this.Slope = Slope;
            }

            public String getSoilName() {
                return SoilName;
            }

            public void setSoilName(String SoilName) {
                this.SoilName = SoilName;
            }

            public int getSoilHeight() {
                return SoilHeight;
            }

            public void setSoilHeight(int SoilHeight) {
                this.SoilHeight = SoilHeight;
            }

            public String getXiaMuType() {
                return XiaMuType;
            }

            public void setXiaMuType(String XiaMuType) {
                this.XiaMuType = XiaMuType;
            }

            public String getXiaMuDensity() {
                return XiaMuDensity;
            }

            public void setXiaMuDensity(String XiaMuDensity) {
                this.XiaMuDensity = XiaMuDensity;
            }

            public String getDBWType() {
                return DBWType;
            }

            public void setDBWType(String DBWType) {
                this.DBWType = DBWType;
            }

            public String getDBWDensity() {
                return DBWDensity;
            }

            public void setDBWDensity(String DBWDensity) {
                this.DBWDensity = DBWDensity;
            }

            public String getManagementUnit() {
                return ManagementUnit;
            }

            public void setManagementUnit(String ManagementUnit) {
                this.ManagementUnit = ManagementUnit;
            }

            public String getManagementState() {
                return ManagementState;
            }

            public void setManagementState(String ManagementState) {
                this.ManagementState = ManagementState;
            }

            public String getAimsTree() {
                return AimsTree;
            }

            public void setAimsTree(String AimsTree) {
                this.AimsTree = AimsTree;
            }

            public String getAimsFamily() {
                return AimsFamily;
            }

            public void setAimsFamily(String AimsFamily) {
                this.AimsFamily = AimsFamily;
            }

            public String getAimsBelong() {
                return AimsBelong;
            }

            public void setAimsBelong(String AimsBelong) {
                this.AimsBelong = AimsBelong;
            }

            public String getRWJYInfo() {
                return RWJYInfo;
            }

            public void setRWJYInfo(String RWJYInfo) {
                this.RWJYInfo = RWJYInfo;
            }

            public String getSuggest() {
                return Suggest;
            }

            public void setSuggest(String Suggest) {
                this.Suggest = Suggest;
            }

            public String getExplain() {
                return Explain;
            }

            public void setExplain(String Explain) {
                this.Explain = Explain;
            }

            public String getRecordPerson() {
                return RecordPerson;
            }

            public void setRecordPerson(String RecordPerson) {
                this.RecordPerson = RecordPerson;
            }

            public String getRecordTime() {
                return RecordTime;
            }

            public void setRecordTime(String RecordTime) {
                this.RecordTime = RecordTime;
            }

            public String getUserID() {
                return UserID;
            }

            public void setUserID(String UserID) {
                this.UserID = UserID;
            }
        }
    }
}
