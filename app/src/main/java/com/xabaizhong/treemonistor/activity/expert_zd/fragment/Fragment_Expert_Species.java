package com.xabaizhong.treemonistor.activity.expert_zd.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.xabaizhong.treemonistor.service.AsyncTaskRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class Fragment_Expert_Species extends Fragment {


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
        View view = inflater.inflate(R.layout.fragment_expert_species, container);
        unbinder = ButterKnife.bind(this, view);
        initialView();
        return view;
    }

    private void initialView() {
        pbLayout.setOnClickListener(null);
        Bean.ResultBean resultBean = bean.getResult();
        text1.setText("type1:" + resultBean.getA() + "\n" +
                "type2:" + resultBean.getB() + "\n" +
                "type3:" + resultBean.getC() + "\n" +
                "type4:" + resultBean.getD() + "\n");
        if (resultBean.getPicList() == null && resultBean.getPicList().size() == 0) {
            showPic.setVisibility(View.VISIBLE);
        } else {
            showPic.setVisibility(View.INVISIBLE);
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

                if (asyncTaskRequest == null) {
//                    request();
                }
                break;
            case R.id.show_pic:
                Intent i = new Intent(getActivity(), Activity_pic_vp.class);
                i.putStringArrayListExtra("picList", bean.getResult().getPicList());
                startActivity(i);
                break;
        }
    }

    AsyncTaskRequest asyncTaskRequest;

    private void request() {
        /*<UserID>string</UserID>
      <TID>int</TID>
      <AreaID>string</AreaID>*/
        Map<String, Object> map = new HashMap<>();
        map.put("UserID", "");
        map.put("TID", "");
        map.put("AreaID", "");

        pbLayout.setVisibility(View.VISIBLE);
        asyncTaskRequest = AsyncTaskRequest.instance("","")
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
         * result : {"a":"a","b":"a","c":"a","d":"a","picList":[]}
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
             * a : a
             * b : a
             * c : a
             * d : a
             * picList : []
             */

            @SerializedName("a")
            private String a;
            @SerializedName("b")
            private String b;
            @SerializedName("c")
            private String c;
            @SerializedName("d")
            private String d;
            @SerializedName("picList")
            private ArrayList<String> picList;

            public String getA() {
                return a;
            }

            public void setA(String a) {
                this.a = a;
            }

            public String getB() {
                return b;
            }

            public void setB(String b) {
                this.b = b;
            }

            public String getC() {
                return c;
            }

            public void setC(String c) {
                this.c = c;
            }

            public String getD() {
                return d;
            }

            public void setD(String d) {
                this.d = d;
            }

            public ArrayList<String> getPicList() {
                return picList;
            }

            public void setPicList(ArrayList<String> picList) {
                this.picList = picList;
            }
        }
    }


}
