package com.xabaizhong.treemonistor.activity.expert_zd.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.base_data.Activity_pic_vp;
import com.xabaizhong.treemonistor.activity.expert.Activity_species;
import com.xabaizhong.treemonistor.activity.expert_zd.Activity_expert_zd_detail;
import com.xabaizhong.treemonistor.base.Fragment_base;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.service.AsyncTaskRequest;
import com.xabaizhong.treemonistor.service.model.ResultMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class Fragment_Expert_Species extends Fragment_base {


    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.edit1)
    EditText edit1;
    Unbinder unbinder;
    @BindView(R.id.show_pic)
    Button showPic;


    Bean bean;
    @BindView(R.id.pb_layout)
    RelativeLayout pbLayout;


    public static Fragment_Expert_Species instance(Bean bean) {
        Fragment_Expert_Species f = new Fragment_Expert_Species();
        f.bean = bean;
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expert_species, null);
        unbinder = ButterKnife.bind(this, view);
//        Log.i(TAG, "instance: "+bean.getResult().getPicList());
        initialView();
        return view;
    }

    String[] leafArray = new String[]{"","椭圆状", "心形", "掌形", "扇形", "菱形", "披针形", "卵形", "圆形", "针形", "鳞形", "匙形", "三角形"};
    String[] leafColorArray = new String[]{"其他", "绿色", "红色", "黄色", "蓝色"};
    String[] flowerArray = new String[]{"","乔木花卉", " 灌木花卉", "藤本花卉"};
    String[] flowerColorArray = new String[]{"其他", "红色", "橙色", "黄色", "绿色", "蓝色", "紫色", "黑色", "褐色", "白色", "粉红色"};
    String[] fruitArray = new String[]{"","单果", " 聚合果", " 复果"};
    String[] fruitColorArray = new String[]{"其他", "白色", "红色", "绿色", "紫色", "黄色", "粉色", "褐色", "黑色"};

    private void initialView() {

        pbLayout.setOnClickListener(null);
        Bean.ResultBean resultBean = bean.getResult();
        text1.setText("鉴定编号\t" + resultBean.getTID() + "\n" +
                "叶      \t" + leafArray[resultBean.getLeafShape()] + "\n" +
                "叶颜色  \t" + leafColorArray[resultBean.getLeafColor()] + "\n" +
                "花      \t" + flowerArray[resultBean.getFlowerType()] + "\n" +
                "花颜色  \t" + flowerColorArray[resultBean.getFlowerColor()] + "\n" +
                "果实    \t" + fruitArray[resultBean.getFruitType()] + "\n" +
                "果实颜色\t" + fruitColorArray[resultBean.getFruitColor()] + "\n");
        if (resultBean.getPicPath() == null || resultBean.getPicPath().size() == 0) {
            showPic.setVisibility(View.INVISIBLE);
        } else {
            showPic.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.submit, R.id.show_pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
                Log.i(TAG, "onViewClicked: ");
                request();
                break;
            case R.id.show_pic:
                Intent i = new Intent(getActivity(), Activity_pic_vp.class);
                ArrayList<String> picList = new ArrayList<>();
                picList.addAll(bean.getResult().getPicPath());
                i.putStringArrayListExtra("picList", picList);
                startActivity(i);
                break;
        }
    }

    private String getStringDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());

    }

    AsyncTaskRequest asyncTaskRequest;

    private void request() {
        /*  <tid>string</tid>
      <userId>string</userId>
      <date>string</date>
      <result>string</result>
      <checkType>int</checkType>*/
        Map<String, Object> map = new HashMap<>();
        map.put("tid", bean.getResult().getTID());
        map.put("userId", sharedPreferences.getString(UserSharedField.USERID,""));
        map.put("date", getStringDate());
        map.put("checkType", 0);
        map.put("result", edit1.getText().toString());

        pbLayout.setVisibility(View.VISIBLE);
        asyncTaskRequest = AsyncTaskRequest.instance("UploadTreeInfo", "AuthenticateResultInfo")
                .setCallBack(new AsyncTaskRequest.CallBack() {
                    @Override
                    public void execute(String s) {
                        pbLayout.setVisibility(View.INVISIBLE);
                        if (s == null) {
                            showToast("请检查网络连接是否异常");
                            return;
                        }
                        Observable.just(s).observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe(new Consumer<String>() {
                                    @Override
                                    public void accept(String s) throws Exception {
                                        ResultMessage rm = new Gson().fromJson(s, ResultMessage.class);
                                        if (rm.getError_code() == 0) {
                                            showToast("上传成功");
                                            getActivity().finish();
                                        } else {
                                            showToast(rm.getMessage());
                                        }
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        showToast("解析失败");
                                    }
                                });
                    }
                }).setParams(map)
                .create();

    }


    public static class Bean {


        /**
         * message : sus
         * error_code : 0
         * result : {"TID":2,"TreeID":"61072600150","LeafShape":0,"LeafColor":0,"FlowerType":0,"FlowerColor":0,"FruitType":0,"FruitColor":0,"PicPath":"~/Image/UpTreeFeature/61072600150","AreaID":"610726","IsCheck":1,"CheckID":"2"}
         */

        @SerializedName("message")
        private String message;
        @SerializedName("error_code")
        private int errorCode;
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

        public ResultBean getResult() {
            return result;
        }

        public void setResult(ResultBean result) {
            this.result = result;
        }

        public static class ResultBean {
            /**
             * TID : 2
             * TreeID : 61072600150
             * LeafShape : 0
             * LeafColor : 0
             * FlowerType : 0
             * FlowerColor : 0
             * FruitType : 0
             * FruitColor : 0
             * PicPath : ~/Image/UpTreeFeature/61072600150
             * AreaID : 610726
             * IsCheck : 1
             * CheckID : 2
             */

            @SerializedName("TID")
            private String TID;
            @SerializedName("TreeID")
            private String TreeID;
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
            @SerializedName("picPath")
            private List<String> PicPath;
            @SerializedName("AreaID")
            private String AreaID;
            @SerializedName("IsCheck")
            private int IsCheck;
            @SerializedName("CheckID")
            private String CheckID;

            public String getTID() {
                return TID;
            }

            public void setTID(String TID) {
                this.TID = TID;
            }

            public String getTreeID() {
                return TreeID;
            }

            public void setTreeID(String TreeID) {
                this.TreeID = TreeID;
            }

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

            public List<String> getPicPath() {
                return PicPath;
            }

            public void setPicPath(List<String> PicPath) {
                this.PicPath = PicPath;
            }

            public String getAreaID() {
                return AreaID;
            }

            public void setAreaID(String AreaID) {
                this.AreaID = AreaID;
            }

            public int getIsCheck() {
                return IsCheck;
            }

            public void setIsCheck(int IsCheck) {
                this.IsCheck = IsCheck;
            }

            public String getCheckID() {
                return CheckID;
            }

            public void setCheckID(String CheckID) {
                this.CheckID = CheckID;
            }
        }
    }


}
