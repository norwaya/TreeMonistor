package com.xabaizhong.treemonistor.activity.expert_zd;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.expert_zd.fragment.Fragment_Expert_Species;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.service.AsyncTaskRequest;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/24 0024.
 */

public class Activity_expert_zd_detail extends Activity_base {
    @BindView(R.id.fragment_replace)
    FrameLayout fragmentReplace;
    int type = 0;
    int tid ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_zd_detail);
        ButterKnife.bind(this);
        type = getIntent().getIntExtra("type", -1);
        tid = getIntent().getIntExtra("tid",-1);
        Log.i(TAG, "onCreate: " + type + "\t" + tid);
        initialFragment();
    }

    private void initialFragment() {
        request();
    }
    private void request(){
        Map<String, Object> map = new HashMap<>();
        map.put("Type", type);
        map.put("Tid", tid);
        AsyncTaskRequest.instance("CheckUp","ExpertLostResult")
                .setCallBack(new AsyncTaskRequest.CallBack() {
                    @Override
                    public void execute(String s) {
                        Log.i(TAG, "execute+++: "+s);
                        if(s==null){
                            showToast("internet error");
                            return;
                        }
                        Fragment_Expert_Species.Bean bean = new Gson().fromJson(s, Fragment_Expert_Species.Bean.class);
                        if (bean.getErrorCode() == 0) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_replace, Fragment_Expert_Species.instance(bean)).commit();
                        }
                    }
                }).setParams(map)
                .create();
    }

}
