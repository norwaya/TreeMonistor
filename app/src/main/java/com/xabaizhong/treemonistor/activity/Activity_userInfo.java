package com.xabaizhong.treemonistor.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.service.WebserviceHelper;
import com.xabaizhong.treemonistor.service.model.User;
import com.xabaizhong.treemonistor.service.response.LoginResultMessage;

import java.io.Serializable;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/6 0006.
 */

public class Activity_userInfo extends Activity_base {
    @BindView(R.id.user_detail)
    TextView userDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        getUserInfo();
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
                User user = new Gson().fromJson(s,User.class);
                userDetail.setText(user.toString());
            }
        }.execute();
    }

    private Map<String, String> requestMap() {
        Map<String, String> map = new HashMap<>();
        String userid = sharedPreferences.getString(UserSharedField.USERID,"null");
        map.put("UserID",userid);
        return map;
    }

}
