package com.xabaizhong.treemonistor.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.base.App;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.entity.AreaCode;
import com.xabaizhong.treemonistor.entity.AreaCodeDao;
import com.xabaizhong.treemonistor.myview.C_info_gather_item1;
import com.xabaizhong.treemonistor.service.WebserviceHelper;
import com.xabaizhong.treemonistor.service.model.User;
import com.xabaizhong.treemonistor.service.response.LoginResultMessage;

import java.io.Serializable;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/6 0006.
 */

public class Activity_userInfo extends Activity_base {
    @BindView(R.id.layout)
    LinearLayout layout;

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
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                if (s == null)
                    return;
                Log.i(TAG, "onPostExecute: " + s);
                LoginResultMessage loginResultMessage = new Gson().fromJson(s, LoginResultMessage.class);
                if (loginResultMessage.getError_code() == 0) {
                    User result = loginResultMessage.getResult();
                    initInfo(result);
                } else {
                    addView("错误信息", "获取用户信息失败");
                }
            }
        }.execute();
    }

    private String getAreaName(String areaId) {
        AreaCodeDao areaCodeDao = ((App) getApplication()).getDaoSession().getAreaCodeDao();
        List<AreaCode> list = areaCodeDao.queryBuilder().where(AreaCodeDao.Properties.AreaId.eq(areaId)).build().list();
        if (list.size() > 0) {
            return list.get(0).getMergerName();
        }
        return "";
    }

    private void initInfo(User result) {
        addView("地理位置", getAreaName(result.getAreaID()));
        addView("账号", result.getUserID());
        addView("姓名", result.getRealName());
    }

    private void addView(String left, String mid) {
        layout.addView(getView(left, mid));
    }


    LayoutInflater inflater;

    private View getView(String left, String mid) {
        if (inflater == null) {
            inflater = LayoutInflater.from(this);
        }

        View view = inflater.inflate(R.layout.c_view, null);
        C_info_gather_item1 cv = (C_info_gather_item1) view.findViewById(R.id.cv);
        cv.setLeftText(left);
        cv.setText(mid);
        Log.i(TAG, "getView: " + view);
        return view;
    }

    private Map<String, Object> requestMap() {
        Map<String, Object> map = new HashMap<>();
        String userid = sharedPreferences.getString(UserSharedField.USERID, "null");
        map.put("UserID", userid);
        map.put("Type", 1);
        return map;
    }

}
