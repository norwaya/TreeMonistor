package com.xabaizhong.treemonistor.activity.expert_zd;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_zd_detail);
        ButterKnife.bind(this);
        type = getIntent().getIntExtra("type", -1);
        initialFragment();
    }

    private void initialFragment() {
        switch (type) {
            case 0:
                request();
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }
    private void request(){
        Map<String, Object> map = new HashMap<>();
        map.put("UserID", "");
        map.put("TID", "");
        map.put("AreaID", "");
        AsyncTaskRequest.instance("DataQuerySys", "TreelAuthDelInfo")
                .setCallBack(new AsyncTaskRequest.CallBack() {
                    @Override
                    public void execute(String s) {
                        String result = "{\n" +
                                "    \"message\": \"sus\",\n" +
                                "    \"error_code\": \"0\",\n" +
                                "    \"result\":\n" +
                                "   \t\t{\n" +
                                "   \t\t\t\"a\":\"a\",\n" +
                                "   \t\t\t\"b\":\"a\",\n" +
                                "   \t\t\t\"c\":\"a\",\n" +
                                "   \t\t\t\"d\":\"a\",\n" +
                                "    \t}\n" +
                                "}";
                        Fragment_Expert_Species.Bean bean = new Gson().fromJson(result, Fragment_Expert_Species.Bean.class);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_replace, Fragment_Expert_Species.instance(bean)).commit();
                    }
                }).setParams(map)
                .create();
    }

    /*String result = "{\n" +
            "    \"message\": \"sus\",\n" +
            "    \"error_code\": \"0\",\n" +
            "    \"result\":\n" +
            "   \t\t{\n" +
            "   \t\t\t\"a\":\"a\",\n" +
            "   \t\t\t\"b\":\"a\",\n" +
            "   \t\t\t\"c\":\"a\",\n" +
            "   \t\t\t\"d\":\"a\",\n" +
            "    \t}\n" +
            "}";*/
}
