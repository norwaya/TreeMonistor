package com.xabaizhong.treemonistor.activity.base_data;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.base.App;
import com.xabaizhong.treemonistor.entity.Pest;
import com.xabaizhong.treemonistor.entity.PestDao;
import com.xabaizhong.treemonistor.myview.C_info_gather_item1;
import com.xabaizhong.treemonistor.service.WebserviceHelper;

import java.util.ArrayList;
import java.util.HashMap;
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
 * Created by admin on 2017/3/17.
 */

public class Activity_base_pest_detail extends Activity_base {
    long id;
    @BindView(R.id.cname)
    C_info_gather_item1 cname;
    @BindView(R.id.type)
    C_info_gather_item1 type;
    @BindView(R.id.explain)
    C_info_gather_item1 explain;
    @BindView(R.id.harm)
    C_info_gather_item1 harm;
    @BindView(R.id.pic)
    C_info_gather_item1 pic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getLongExtra("id", -1);
        setContentView(R.layout.activity_base_pest_detail);
        ButterKnife.bind(this);
        initDb();
        initialView();
    }

    Pest pest;

    private void initDb() {
        PestDao pestDao = ((App) getApplicationContext()).getDaoSession().getPestDao();
        pest = pestDao.load(id);
    }

    private void initialView() {
        cname.setText(pest.getHexapodname());
        type.setText(pest.getHexapodType() == 0 ? "咀嚼式" : "刺吸式");
        explain.setText(pest.getTrait());
        harm.setText(pest.getMethod());
        pic.setText("0");
        pic.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                if ("0".equals(pic.getText())) {
                    return;
                }
                Intent i = new Intent(Activity_base_pest_detail.this, Activity_pic_vp.class);
                i.putStringArrayListExtra("picList", list);
                startActivity(i);
            }
        });
//        requestPic();


    }

    ArrayList<String> list = new ArrayList<>();

    private void requestPic() {
        final Map<String, Object> map = new HashMap<>();
        map.put("", "");
        map.put("", "");
        map.put("", "");

        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        String result = WebserviceHelper.GetWebService("", "", map);
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
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                throwable.printStackTrace();
                                showToast("解析信息失败");
                            }
                        });
    }
}
