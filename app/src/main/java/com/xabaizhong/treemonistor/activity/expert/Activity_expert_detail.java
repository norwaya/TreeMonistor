package com.xabaizhong.treemonistor.activity.expert;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.service.WebserviceHelper;

import java.util.HashMap;
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

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class Activity_expert_detail extends Activity_base {

    String tid;
    int type;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.text3)
    TextView text3;

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
                        fillData(value);
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
            default:
                break;
        }
    }

    private void treeIll(String value) {
        TreeIll treeIll = new Gson().fromJson(value, TreeIll.class);
        String[] part = new String[]{"其他", "叶", "枝干", "果实", "嫩枝或枝稍", "干基或根部"};
        ResultMessage<TreeIllDetail>.ExpertBean expert = treeIll.getExpert();
        text1.setText("鉴定编号\t" + tid + "\n" +
                "叶：" + part[treeIll.getBean().getPart()]
        );
        if (treeIll.isChecked()) {
            text2.setText("鉴定时间：" + expert.getDate() + "\n" +
                    "专家：" + expert.getRealName() + "\n" +
                    "手机：" + expert.getUserTel()

            );
            text3.setText("鉴定结果：" + expert.getResult());
        } else {
            text2.setText("未鉴定");
        }
    }

    private void treeSpecies(String value) {
        String[] leafArray = new String[]{"其他", "椭圆状", "心形", "掌形", "扇形", "菱形", "披针形", "卵形", "圆形", "针形", "鳞形", "匙形", "三角形"};
        String[] leafColorArray = new String[]{"其他", "绿色", "红色", "黄色", "蓝色"};
        String[] flowerArray = new String[]{"其他", "乔木花卉", " 灌木花卉", "藤本花卉"};
        String[] flowerColorArray = new String[]{"其他", "红色", "橙色", "黄色", "绿色", "蓝色", "紫色", "黑色", "褐色", "白色", "粉红色"};
        String[] fruitArray = new String[]{"其他", "单果", " 聚合果", " 复果"};
        String[] fruitColorArray = new String[]{"其他", "白色", "红色", "绿色", "紫色", "黄色", "粉色", "褐色", "黑色"};
        TreeSpeciesRm treeSpeciesRm = new Gson().fromJson(value, TreeSpeciesRm.class);
        TreeSpecialDetail bean = treeSpeciesRm.getBean();
        text1.setText("鉴定编号\t" + tid + "\n" +
                getString(R.string.two_item_t, "叶：", leafArray[bean.getLeafShape()]) + "\n" +
                getString(R.string.two_item_t, "叶颜色：", leafColorArray[bean.getLeafColor()]) + "\n" +
                "花：" + flowerArray[bean.getFlowerType()] + "\n" +
                "花颜色：" + flowerColorArray[bean.getFlowerColor()] + "\n" +
                "果实：" + fruitArray[bean.getFruitType()] + "\n" +
                "果实颜色：" + fruitColorArray[bean.getFruitColor()] + "\n"
        );
        ResultMessage<TreeSpecialDetail>.ExpertBean expert = treeSpeciesRm.getExpert();
        if (treeSpeciesRm.isChecked()) {
            text2.setText("鉴定时间：" + expert.getDate() + "\n" +
                    "专家：" + expert.getRealName() + "\n" +
                    "手机：" + expert.getUserTel()

            );
            text3.setText("鉴定结果：" + expert.getResult());
        } else {
            text2.setText("未鉴定");
        }
    }

    private void initialView() {

    }

    class ResultMessage<T> {

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

    class TreeSpecialDetail {
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

    class TreeIllDetail {

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

    class TreeSpeciesRm extends ResultMessage<TreeSpecialDetail> {
    }

    class TreeIll extends ResultMessage<TreeIllDetail> {
    }
}
