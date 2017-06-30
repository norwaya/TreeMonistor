package com.xabaizhong.treemonistor.activity.base_data;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.base.App;
import com.xabaizhong.treemonistor.entity.Weakness;
import com.xabaizhong.treemonistor.entity.WeaknessDao;
import com.xabaizhong.treemonistor.myview.C_info_gather_item1;
import com.xabaizhong.treemonistor.service.WebserviceHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 树病 详情页面；
 */
public class Activity_base_weakness_detail extends Activity_base {

    long id;
    @BindView(R.id.cname)
    C_info_gather_item1 cname;
    @BindView(R.id.part)
    C_info_gather_item1 part;
    @BindView(R.id.trait)
    C_info_gather_item1 trait;
    @BindView(R.id.featrue)
    C_info_gather_item1 featrue;
    @BindView(R.id.method)
    C_info_gather_item1 method;
    @BindView(R.id.pic)
    C_info_gather_item1 pic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        id = getIntent().getLongExtra("id", -1);
        setContentView(R.layout.activity_base_weakness_detail);
        ButterKnife.bind(this);
        initDb();
        initialView();
    }

    private void initDb() {

        WeaknessDao weaknessDao = ((App) getApplicationContext()).getDaoSession().getWeaknessDao();
        weakness = weaknessDao.load(id);
    }

    Weakness weakness;

    private void initialView() {
        cname.setText(weakness.getName());
        part.setText(weakness.getPartName());
        trait.setText(weakness.getTrait());
        featrue.setText(weakness.getFreature());
        method.setText(weakness.getMethod());
        pic.setText("0");
        pic.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                if ("0".equals(pic.getText())) {
                    return;
                }
                Intent i = new Intent(Activity_base_weakness_detail.this, Activity_pic_vp.class);
                i.putStringArrayListExtra("picList", list);
                startActivity(i);
            }
        });
        requestPic();
    }

    ArrayList<String> list = new ArrayList<>();

    private void requestPic() {
        final Map<String, Object> map = new HashMap<>();
        map.put("TreeillID", weakness.getTid());

        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        String result = WebserviceHelper.GetWebService("Pic", "GetTreeillPic ", map);
                        Log.i(TAG, "subscribe: " + result);
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
                .subscribe(
                        new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                ResultMessage rs = new Gson().fromJson(s, ResultMessage.class);
                                list.addAll(rs.getPiclist());
                                pic.setText(list.size()+"");
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                throwable.printStackTrace();
                                Log.i(TAG, "accept: 解析信息失败");
                            }
                        });
    }
    static class ResultMessage{

        /**
         * message : sus
         * error_code : 0
         * piclist : ["http://192.168.0.118:8055/Image/TreeillInfo/T017/A)C8B{U)PJ)IV09_{(L)MI0.png"]
         */

        @SerializedName("message")
        private String message;
        @SerializedName("error_code")
        private int errorCode;
        @SerializedName("piclist")
        private List<String> piclist;

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

        public List<String> getPiclist() {
            return piclist;
        }

        public void setPiclist(List<String> piclist) {
            this.piclist = piclist;
        }
    }

}
