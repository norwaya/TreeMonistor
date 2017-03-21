package com.xabaizhong.treemonistor.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.Activity_login;
import com.xabaizhong.treemonistor.activity.Activity_main;
import com.xabaizhong.treemonistor.base.Fragment_base;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.xabaizhong.treemonistor.activity.Activity_main.ReceiveConstant.RECEIVER_ACTION;
import static com.xabaizhong.treemonistor.activity.Activity_main.ReceiveConstant.RECEIVER_INTENT;
import static com.xabaizhong.treemonistor.activity.Activity_main.ReceiveConstant.RECEIVER_INTENT_BIND_SERVICE;
import static com.xabaizhong.treemonistor.activity.Activity_main.ReceiveConstant.RECEIVER_INTENT_UNBIND_SERVICE;
import static com.xabaizhong.treemonistor.contant.Contant.KV.NOTICE_PUSH;

/**
 * Created by admin on 2017/2/24.
 */

public class Fragment_setting extends Fragment_base {


    @BindView(R.id.user_tv)
    TextView userTv;
    @BindView(R.id.information_s)
    Switch informationS;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);
        initNotice();
        initView();
        return view;
    }

    private void initNotice() {

        boolean noticeS = sharedPreferences.getBoolean(NOTICE_PUSH, true);
        informationS.setChecked(noticeS);

    }

    private void initView() {
        informationS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent i = new Intent();
                i.setAction(RECEIVER_ACTION);
                if(isChecked){
                    i.putExtra(RECEIVER_INTENT, RECEIVER_INTENT_BIND_SERVICE);
                }else{
                    i.putExtra(RECEIVER_INTENT, RECEIVER_INTENT_UNBIND_SERVICE);
                }
                getContext().sendBroadcast(i);
                changeNoticeSwitch(isChecked);

            }
        });
    }
    private void changeNoticeSwitch(boolean s){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(NOTICE_PUSH, s);
        editor.apply();
        editor.commit();
    }

    @OnClick({R.id.user_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_tv:
                startActivity(new Intent(getActivity(), Activity_login.class));
                break;
            case R.id.information_s:
                break;
        }
    }
}
