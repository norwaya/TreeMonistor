package com.xabaizhong.treemonistor.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.Activity_login;
import com.xabaizhong.treemonistor.activity.Activity_userInfo;
import com.xabaizhong.treemonistor.base.Fragment_base;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.myview.CircleImageView;
import com.xabaizhong.treemonistor.service.WebserviceHelper;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

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
    @BindView(R.id.user_unlogin)
    RelativeLayout userUnlogin;
    @BindView(R.id.user_icon)
    CircleImageView userIcon;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_real_name)
    TextView tvRealName;
    @BindView(R.id.user_logined)
    RelativeLayout userLogined;
    @BindView(R.id.information_s)
    Switch informationS;

    private static final String UNLOGIND = "unlogin";

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        String name = sharedPreferences.getString(UserSharedField.USERID, UNLOGIND);
        Log.d(TAG, "onResume: " + name);
        if (UNLOGIND.equals(name)) {
            userUnlogin.setVisibility(View.VISIBLE);
            userLogined.setVisibility(View.GONE);
        } else {
            userUnlogin.setVisibility(View.GONE);
            userLogined.setVisibility(View.VISIBLE);
            validateUserInfo();
        }


    }

    private void validateUserInfo() {
        String user_name = sharedPreferences.getString(UserSharedField.USERID, "");
        String real_name = sharedPreferences.getString(UserSharedField.REALNAME, "");
        if (tvUserName.getText().toString().equals(user_name))
            return;
        else {
//            getUserInfo();
            tvUserName.setText(user_name);
            tvRealName.setText(getString(R.string.real_name, real_name));
        }
    }

    AsyncTask asyncTask;

    private void getUserInfo() {
        asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    return WebserviceHelper.GetWebService(
                            "Login", "UserDetInfo", requestMap());
                } catch (ConnectException e) {
                    e.printStackTrace();
                    return "-1";
                }
            }

            @Override
            protected void onPostExecute(String s) {
                Log.d(TAG, "onPostExecute: " + s);
            }
        }.execute();
    }

    private Map<String, String> requestMap() {
        Map<String, String> map = new HashMap<>();
        String userid = sharedPreferences.getString(UserSharedField.USERID, "null");
        map.put("UserID", userid);
        return map;
    }


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
                if (isChecked) {
                    i.putExtra(RECEIVER_INTENT, RECEIVER_INTENT_BIND_SERVICE);
                } else {
                    i.putExtra(RECEIVER_INTENT, RECEIVER_INTENT_UNBIND_SERVICE);
                }
                getContext().sendBroadcast(i);
                changeNoticeSwitch(isChecked);

            }
        });


    }

    private void changeNoticeSwitch(boolean s) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(NOTICE_PUSH, s);
        editor.apply();
        editor.commit();
    }

    @OnClick({R.id.user_unlogin, R.id.user_logined})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_unlogin:
                startActivity(new Intent(getActivity(), Activity_login.class));
                break;
            case R.id.user_logined:
                startActivity(new Intent(getActivity(), Activity_userInfo.class));
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
