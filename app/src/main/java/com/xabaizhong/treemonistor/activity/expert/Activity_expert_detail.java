package com.xabaizhong.treemonistor.activity.expert;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.myview.C_info_gather_item1;
import com.xabaizhong.treemonistor.service.WebserviceHelper;

import junit.runner.Version;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * 专家鉴定  详情页
 */
public class Activity_expert_detail extends Activity_base implements C_info_gather_item1.Mid_CallBack {

    String tid;
    int type;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.text3)
    TextView text3;
    @BindView(R.id.layout)
    LinearLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tid = getIntent().getStringExtra("tid");
        type = getIntent().getIntExtra("type", -1);
        if (tid == null) {
            finish();
            return;
        }
        setContentView(R.layout.activity_expert_detail);
        ButterKnife.bind(this);
        initialView();
        initialData();
    }

    private void initialData() {
        /* <tid>string</tid>
      <UserID>string</UserID>*/
        final Map<String, Object> params = new HashMap<>();
        params.put("tid", tid);
        params.put("UserID", sharedPreferences.getString(UserSharedField.USERID, ""));
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        String result = WebserviceHelper.GetWebService("DataQuerySysOther", "checkResult", params);
                        if (result == null) {
                            e.onError(new RuntimeException("error"));
                        } else {
                            e.onNext(result);
                        }
                        e.onComplete();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        Log.i(TAG, "onNext: " + value);
                        ResultMessage rm = new Gson().fromJson(value, ResultMessage.class);
                        if (rm.getErrorCode() == 0) {
                            fillData(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void fillData(String value) {

        switch (type) {
            case 0:
                treeSpecies(value);
                break;
            case 3:
                treeIll(value);
                break;
            case 4:
                treeBug(value);
                break;
            default:
                break;
        }
    }
    // 初始化 字符资源
    private void treeBug(String value) {
        Bug bug = new Gson().fromJson(value, Bug.class);
        List<String> season = new ArrayList<>();
        List<String> part = new ArrayList<>();
        List<String> classic = new ArrayList<>();
        season.add(0, "其他");
        season.add(1, "春季");
        season.add(2, "夏季");
        season.add(3, "秋季");
        season.add(4, "冬季");
        part.add(0, "其他");
        part.add(1, "叶子");
        part.add(2, "树干");
        part.add(3, "根");
        part.add(4, "果实");
        classic.add(0, "咀嚼式");
        classic.add(1, "刺吸式");
        classic.add(2, "其他");
        layout.addView(getView("鉴定编号", tid));
        layout.addView(getView("季节", season.get(bug.getBean().getDiscoverySeason())));
        layout.addView(getView("虫害部位", part.get(bug.getBean().getPart())));
        layout.addView(getView("虫害类型", classic.get(bug.getBean().getTreeType())));
        //专家信息 +鉴定信息
        if (bug.isChecked()) {
            layout.addView(getView("鉴定结果", bug.getExpert().getResult()));
            layout.addView(getView("鉴定时间", bug.getExpert().getDate()));
            layout.addView(getView("专家", bug.getExpert().getRealName()));
            telNum = bug.getExpert().getUserTel();
            layout.addView(getListenerView("手机", bug.getExpert().getUserTel()));
        } else {
            layout.addView(getView("是否鉴定", "未鉴定"));
        }
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

    private View getListenerView(String left, String mid) {
        if (inflater == null) {
            inflater = LayoutInflater.from(this);
        }

        View view = inflater.inflate(R.layout.c_view, null);
        C_info_gather_item1 cv = (C_info_gather_item1) view.findViewById(R.id.cv);

        cv.setCallback_mid(this);
        cv.setLeftText(left);
        cv.setText(mid);
        return view;
    }

    private void treeIll(String value) {
        TreeIll treeIll = new Gson().fromJson(value, TreeIll.class);
        String[] part = new String[]{"其他", "叶", "枝干", "果实", "嫩枝或枝稍", "干基或根部"};
        ResultMessage<TreeIllDetail>.ExpertBean expert = treeIll.getExpert();
        text1.setText("鉴定编号\t" + tid + "\n" +
                "叶：" + part[treeIll.getBean().getPart()]
        );
        layout.addView(getView("鉴定编号", tid));
        layout.addView(getView("叶", part[treeIll.getBean().getPart()]));
        if (treeIll.isChecked()) {
//            text2.setText("鉴定时间：" + expert.getDate() + "\n" +
//                    "专家：" + expert.getRealName() + "\n" +
//                    "手机：" + expert.getUserTel()
//
//            );
//            text3.setText("鉴定结果：" + expert.getResult());
            layout.addView(getView("鉴定结果", expert.getResult()));
            layout.addView(getView("鉴定时间", expert.getDate()));
            layout.addView(getView("专家", expert.getRealName()));
            telNum = expert.getUserTel();
            layout.addView(getListenerView("手机", expert.getUserTel()));
        } else {
//            text2.setText("未鉴定");
            layout.addView(getView("是否鉴定", "未鉴定"));
        }

    }

    private static final int REQUEST_CODE_CALL = 818;
    private void checkCallPermissionAndDial(){
        if(Build.VERSION.SDK_INT> Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE},REQUEST_CODE_CALL);
            }else{
                makeDial();
            }
        }else{
            makeDial();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_CALL && grantResults[0] == PERMISSION_GRANTED) {
            makeDial();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    String telNum ="";
    private void makeDial(){
        if("".equals(telNum.trim()))
            return;
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+telNum));
        startActivity(intent);
    }

    // 初始化 树种字符串资源
    private void treeSpecies(String value) {
        String[] leafArray = new String[]{"其他", "椭圆状", "心形", "掌形", "扇形", "菱形", "披针形", "卵形", "圆形", "针形", "鳞形", "匙形", "三角形"};
        String[] leafColorArray = new String[]{"其他", "绿色", "红色", "黄色", "蓝色"};
        String[] flowerArray = new String[]{"其他", "乔木花卉", " 灌木花卉", "藤本花卉"};
        String[] flowerColorArray = new String[]{"其他", "红色", "橙色", "黄色", "绿色", "蓝色", "紫色", "黑色", "褐色", "白色", "粉红色"};
        String[] fruitArray = new String[]{"其他", "单果", " 聚合果", " 复果"};
        String[] fruitColorArray = new String[]{"其他", "白色", "红色", "绿色", "紫色", "黄色", "粉色", "褐色", "黑色"};
        TreeSpeciesRm treeSpeciesRm = new Gson().fromJson(value, TreeSpeciesRm.class);
        TreeSpecialDetail bean = treeSpeciesRm.getBean();
//        text1.setText("鉴定编号\t" + tid + "\n" +
//                getString(R.string.two_item_t,
//                        "叶：", leafArray[bean.getLeafShape()]) + "\n" +
//                getString(R.string.two_item_t,
//                        "叶颜色：", leafColorArray[bean.getLeafColor()]) + "\n" +
//                "花：" + flowerArray[bean.getFlowerType()] + "\n" +
//                "花颜色：" + flowerColorArray[bean.getFlowerColor()] + "\n" +
//                "果实：" + fruitArray[bean.getFruitType()] + "\n" +
//                "果实颜色：" + fruitColorArray[bean.getFruitColor()] + "\n"
//        );
        layout.addView(getView("鉴定编号", tid));
        layout.addView(getView("叶", leafArray[bean.getLeafShape()]));
        layout.addView(getView("叶颜色", leafColorArray[bean.getLeafColor()]));
        layout.addView(getView("花", flowerArray[bean.getFlowerType()]));
        layout.addView(getView("花颜色", flowerColorArray[bean.getFlowerColor()]));
        layout.addView(getView("果实", fruitArray[bean.getFruitType()]));
        layout.addView(getView("果实颜色", fruitColorArray[bean.getFruitColor()]));
        ResultMessage<TreeSpecialDetail>.ExpertBean expert = treeSpeciesRm.getExpert();
        if (treeSpeciesRm.isChecked()) {
            layout.addView(getView("鉴定结果", expert.getResult()));
            layout.addView(getView("鉴定时间", expert.getDate()));
            layout.addView(getView("专家", expert.getRealName()));
            telNum = expert.getUserTel();
            layout.addView(getListenerView("手机", expert.getUserTel()));
        } else {
            layout.addView(getView("是否鉴定", "未鉴定"));
        }
    }

    private void initialView() {

    }

    @Override
    public void onClickListener(View et) {
        checkCallPermissionAndDial();
    }


    // 一些 beans  用于 解析 json
    static class ResultMessage<T> {

        /**
         * message : suc
         * error_code : 0
         * beanType : 0
         * bean : {"LeafShape":6,"LeafColor":4,"FlowerType":1,"FlowerColor":5,"FruitType":2,"FruitColor":6}
         * expert : {"UserID":"czy","RealName":"chengziyang","UserTel":"13888888888","date":"2017-05-16 17:49:41","result":"678"}
         * checked : true
         */

        @SerializedName("message")
        private String message;
        @SerializedName("error_code")
        private int errorCode;
        @SerializedName("beanType")
        private int beanType;
        @SerializedName("bean")
        private T bean;
        @SerializedName("expert")
        private ExpertBean expert;
        @SerializedName("checked")
        private boolean checked;

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

        public int getBeanType() {
            return beanType;
        }

        public void setBeanType(int beanType) {
            this.beanType = beanType;
        }

        public T getBean() {
            return bean;
        }

        public void setBean(T bean) {
            this.bean = bean;
        }

        public ExpertBean getExpert() {
            return expert;
        }

        public void setExpert(ExpertBean expert) {
            this.expert = expert;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }


        public class ExpertBean {
            /**
             * UserID : czy
             * RealName : chengziyang
             * UserTel : 13888888888
             * date : 2017-05-16 17:49:41
             * result : 678
             */

            @SerializedName("UserID")
            private String UserID;
            @SerializedName("RealName")
            private String RealName;
            @SerializedName("UserTel")
            private String UserTel;
            @SerializedName("date")
            private String date;
            @SerializedName("result")
            private String result;

            public String getUserID() {
                return UserID;
            }

            public void setUserID(String UserID) {
                this.UserID = UserID;
            }

            public String getRealName() {
                return RealName;
            }

            public void setRealName(String RealName) {
                this.RealName = RealName;
            }

            public String getUserTel() {
                return UserTel;
            }

            public void setUserTel(String UserTel) {
                this.UserTel = UserTel;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getResult() {
                return result;
            }

            public void setResult(String result) {
                this.result = result;
            }
        }
    }

    static class TreeSpecialDetail {
        /**
         * LeafShape : 6
         * LeafColor : 4
         * FlowerType : 1
         * FlowerColor : 5
         * FruitType : 2
         * FruitColor : 6
         */

        @SerializedName("LeafShape")
        private int LeafShape;
        @SerializedName("LeafColor")
        private int LeafColor;
        @SerializedName("FlowerType")
        private int FlowerType;
        @SerializedName("FlowerColor")
        private int FlowerColor;
        @SerializedName("FruitType")
        private int FruitType;
        @SerializedName("FruitColor")
        private int FruitColor;

        public int getLeafShape() {
            return LeafShape;
        }

        public void setLeafShape(int LeafShape) {
            this.LeafShape = LeafShape;
        }

        public int getLeafColor() {
            return LeafColor;
        }

        public void setLeafColor(int LeafColor) {
            this.LeafColor = LeafColor;
        }

        public int getFlowerType() {
            return FlowerType;
        }

        public void setFlowerType(int FlowerType) {
            this.FlowerType = FlowerType;
        }

        public int getFlowerColor() {
            return FlowerColor;
        }

        public void setFlowerColor(int FlowerColor) {
            this.FlowerColor = FlowerColor;
        }

        public int getFruitType() {
            return FruitType;
        }

        public void setFruitType(int FruitType) {
            this.FruitType = FruitType;
        }

        public int getFruitColor() {
            return FruitColor;
        }

        public void setFruitColor(int FruitColor) {
            this.FruitColor = FruitColor;
        }
    }

    static class TreeIllDetail {

        /**
         * Part : 5
         * Feature1 : 0
         * Feature2 : 0
         */

        @SerializedName("Part")
        private int Part;
        @SerializedName("Feature1")
        private int Feature1;
        @SerializedName("Feature2")
        private int Feature2;

        public int getPart() {
            return Part;
        }

        public void setPart(int Part) {
            this.Part = Part;
        }

        public int getFeature1() {
            return Feature1;
        }

        public void setFeature1(int Feature1) {
            this.Feature1 = Feature1;
        }

        public int getFeature2() {
            return Feature2;
        }

        public void setFeature2(int Feature2) {
            this.Feature2 = Feature2;
        }
    }

    static class TreeSpeciesRm extends ResultMessage<TreeSpecialDetail> {
    }

    static class TreeIll extends ResultMessage<TreeIllDetail> {
    }

    static class Bug {


        /**
         * message : suc
         * error_code : 0
         * beanType : 4
         * bean : {"Part":0,"TreeType":0,"DiscoverySeason":1}
         * expert : {"UserID":"610102001","RealName":"张三","UserTel":"12345678910","date":"2017-06-08 09:35:09","result":"215145"}
         * checked : true
         */

        @SerializedName("message")
        private String message;
        @SerializedName("error_code")
        private int errorCode;
        @SerializedName("beanType")
        private int beanType;
        @SerializedName("bean")
        private BeanBean bean;
        @SerializedName("expert")
        private ExpertBean expert;
        @SerializedName("checked")
        private boolean checked;

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

        public int getBeanType() {
            return beanType;
        }

        public void setBeanType(int beanType) {
            this.beanType = beanType;
        }

        public BeanBean getBean() {
            return bean;
        }

        public void setBean(BeanBean bean) {
            this.bean = bean;
        }

        public ExpertBean getExpert() {
            return expert;
        }

        public void setExpert(ExpertBean expert) {
            this.expert = expert;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public static class BeanBean {
            /**
             * Part : 0
             * TreeType : 0
             * DiscoverySeason : 1
             */

            @SerializedName("Part")
            private int Part;
            @SerializedName("TreeType")
            private int TreeType;
            @SerializedName("DiscoverySeason")
            private int DiscoverySeason;

            public int getPart() {
                return Part;
            }

            public void setPart(int Part) {
                this.Part = Part;
            }

            public int getTreeType() {
                return TreeType;
            }

            public void setTreeType(int TreeType) {
                this.TreeType = TreeType;
            }

            public int getDiscoverySeason() {
                return DiscoverySeason;
            }

            public void setDiscoverySeason(int DiscoverySeason) {
                this.DiscoverySeason = DiscoverySeason;
            }
        }

        public static class ExpertBean {
            /**
             * UserID : 610102001
             * RealName : 张三
             * UserTel : 12345678910
             * date : 2017-06-08 09:35:09
             * result : 215145
             */

            @SerializedName("UserID")
            private String UserID;
            @SerializedName("RealName")
            private String RealName;
            @SerializedName("UserTel")
            private String UserTel;
            @SerializedName("date")
            private String date;
            @SerializedName("result")
            private String result;

            public String getUserID() {
                return UserID;
            }

            public void setUserID(String UserID) {
                this.UserID = UserID;
            }

            public String getRealName() {
                return RealName;
            }

            public void setRealName(String RealName) {
                this.RealName = RealName;
            }

            public String getUserTel() {
                return UserTel;
            }

            public void setUserTel(String UserTel) {
                this.UserTel = UserTel;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getResult() {
                return result;
            }

            public void setResult(String result) {
                this.result = result;
            }
        }
    }
}
