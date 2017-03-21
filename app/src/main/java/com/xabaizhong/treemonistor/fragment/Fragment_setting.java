package com.xabaizhong.treemonistor.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.Activity_login;
import com.xabaizhong.treemonistor.activity.Activity_login2;
import com.xabaizhong.treemonistor.base.Fragment_base;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        initView();
        return view;
    }

    private void initView() {
        informationS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i(TAG, "onCheckedChanged: "+isChecked);
            }
        });

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
