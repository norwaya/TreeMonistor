package com.xabaizhong.treemonistor.activity.expert_zd.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
public class Fragment_Expert_Ill extends Fragment_base {


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


    public static Fragment_Expert_Ill instance(Bean bean) {
        Fragment_Expert_Ill f = new Fragment_Expert_Ill();
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
    String[] part = new String[]{"其他","叶", "枝干", "果实", "嫩枝或枝稍", "干基或根部"};

    private void initialView() {

        pbLayout.setOnClickListener(null);
        Bean.ResultBean resultBean = bean.getResult();
        text1.setText("鉴定编号\t" + resultBean.getTID() + "\n" +
                "部位      \t" + part[resultBean.getPart()]);
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
        map.put("checkType", 3);
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
             * TID : 61011120170518153344878
             * TreeID : 61011120170518153344878
             * Part : 20
             * Feature1 : 1
             * Feature2 : 2
             * picPath : ["http://192.168.0.118:8055/Image/UpTreeillfeature/61011120170518153344878/1.jpg"]
             * AreaID : 610111
             * Explain : shuoming
             */

            @SerializedName("TID")
            private String TID;
            @SerializedName("TreeID")
            private String TreeID;
            @SerializedName("Part")
            private int Part;
            @SerializedName("Feature1")
            private int Feature1;
            @SerializedName("Feature2")
            private int Feature2;
            @SerializedName("AreaID")
            private String AreaID;
            @SerializedName("Explain")
            private String Explain;
            @SerializedName("picPath")
            private List<String> picPath;

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

            public String getAreaID() {
                return AreaID;
            }

            public void setAreaID(String AreaID) {
                this.AreaID = AreaID;
            }

            public String getExplain() {
                return Explain;
            }

            public void setExplain(String Explain) {
                this.Explain = Explain;
            }

            public List<String> getPicPath() {
                return picPath;
            }

            public void setPicPath(List<String> picPath) {
                this.picPath = picPath;
            }
        }
    }


}
