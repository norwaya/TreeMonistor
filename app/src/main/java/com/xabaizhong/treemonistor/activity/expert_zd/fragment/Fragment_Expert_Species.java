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

import com.google.gson.annotations.SerializedName;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.base_data.Activity_pic_vp;
import com.xabaizhong.treemonistor.base.Fragment_base;
import com.xabaizhong.treemonistor.service.AsyncTaskRequest;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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

    private void initialView() {

        pbLayout.setOnClickListener(null);
        Bean.ResultBean resultBean = bean.getResult();
        text1.setText("编号:\t" + resultBean.getTID() + "\n" +
                "area :\t" + resultBean.getAreaID() + "\n" +
                "花颜色: \t" + resultBean.getFlowerColor() + "\n" +
                "花: \t" + resultBean.getFlowerType() + "\n" +
                "叶颜色: \t" + resultBean.getLeafColor() + "\n" +
                "叶: \t" + resultBean.getLeafShape() + "\n" +
                "果实: \t" + resultBean.getFruitType() + "\n" +
                "果实颜色:" + resultBean.getFruitColor() + "\n");
//        if (resultBean.getPicPath() == null && resultBean.getPicList().size() == 0) {
//            showPic.setVisibility(View.VISIBLE);
//        } else {
//            showPic.setVisibility(View.INVISIBLE);
//        }
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

                if (asyncTaskRequest == null) {
                    request();
                }
                break;
            case R.id.show_pic:
//                Intent i = new Intent(getActivity(), Activity_pic_vp.class);
//                i.putStringArrayListExtra("picList", bean.getResult().getPicList());
//                startActivity(i);
                break;
        }
    }

    AsyncTaskRequest asyncTaskRequest;

    private void request() {
        /*<UserID>string</UserID>
      <TID>int</TID>
      <AreaID>string</AreaID>*/
        Map<String, Object> map = new HashMap<>();
        map.put("Type", "");
        map.put("Tid", "");
        map.put("AreaID", "");

        pbLayout.setVisibility(View.VISIBLE);
        asyncTaskRequest = AsyncTaskRequest.instance("UploadTreeInfo", "AuthenticateResultInfo")
                .setCallBack(new AsyncTaskRequest.CallBack() {
                    @Override
                    public void execute(String s) {

                        pbLayout.setVisibility(View.INVISIBLE);
                        asyncTaskRequest = null;
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
            private int TID;
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
            @SerializedName("PicPath")
            private String PicPath;
            @SerializedName("AreaID")
            private String AreaID;
            @SerializedName("IsCheck")
            private int IsCheck;
            @SerializedName("CheckID")
            private String CheckID;

            public int getTID() {
                return TID;
            }

            public void setTID(int TID) {
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

            public String getPicPath() {
                return PicPath;
            }

            public void setPicPath(String PicPath) {
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
