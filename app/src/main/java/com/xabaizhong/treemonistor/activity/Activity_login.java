package com.xabaizhong.treemonistor.activity;

import android.content.ComponentName;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.service.WebserviceHelper;
import com.xabaizhong.treemonistor.service.model.User;
import com.xabaizhong.treemonistor.service.response.LoginResultMessage;
import com.xabaizhong.treemonistor.utils.InputVerification;


import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/3/3.
 */

public class Activity_login extends Activity_base implements View.OnFocusChangeListener {

    /*@BindView(R.id.area)
    EditText area;
     @BindView(R.id.iv_area_close)
    ImageView ivAreaClose;

    */

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.pwd)
    EditText pwd;
    @BindView(R.id.layout)
    RelativeLayout layout;

    @BindView(R.id.iv_name_close)
    ImageView ivNameClose;
    @BindView(R.id.iv_eye)
    ImageView ivEye;
    @BindView(R.id.btn_login)
    Button btnLogin;

    boolean flag = false;
    @BindView(R.id.pb)
    ProgressBar pb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        name.setText("610000");
        pwd.setText("admin");
        initObserver();
        initView();
    }


    private void initObserver() {

    }

    private void initView() {
        pb.setVisibility(View.INVISIBLE);

        /*ivAreaClose.setVisibility(View.INVISIBLE);*/
        ivNameClose.setVisibility(View.INVISIBLE);
        initEye(false);


        /*area.setOnFocusChangeListener(this);*/
        pwd.setOnFocusChangeListener(this);
        name.setOnFocusChangeListener(this);

        addTextViewWatch();
    }

    private void addTextViewWatch() {

        /*area.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (area.hasFocus() && !TextUtils.isEmpty(area.getText())) {
                    ivAreaClose.setVisibility(View.VISIBLE);
                } else {
                    ivAreaClose.setVisibility(View.INVISIBLE);
                }
            }
        });*/
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (name.hasFocus() && !TextUtils.isEmpty(name.getText())) {
                    ivNameClose.setVisibility(View.VISIBLE);
                } else {
                    ivNameClose.setVisibility(View.INVISIBLE);
                }
            }
        });
        pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (pwd.hasFocus() && !TextUtils.isEmpty(pwd.getText())) {
                    ivEye.setVisibility(View.VISIBLE);
                } else {
                    ivEye.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @OnClick({/*R.id.iv_area_close,*/ R.id.iv_name_close, R.id.iv_eye, R.id.btn_login})
    public void onClick(View view) {
        Log.i(TAG, "onClick: ");
        switch (view.getId()) {
           /* case R.id.iv_area_close:
                area.setText("");
                break;*/
            case R.id.iv_name_close:
                name.setText("");
                break;
            case R.id.iv_eye:
                if (!flag) {
                    pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                flag = !flag;
                initEye(flag);
                pwd.postInvalidate();
                if (pwd.hasFocus()) {
                    pwd.setSelection(pwd.getText().length());
                }
                break;
            case R.id.btn_login:
                pb.setVisibility(View.VISIBLE);
                attemptLogin();
                break;
        }
    }

    private void initEye(boolean invisible) {
        if (invisible) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ivEye.setImageDrawable(getDrawable(R.drawable.eye));
            } else {
                ivEye.setImageDrawable(getResources().getDrawable(R.drawable.eye));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ivEye.setImageDrawable(getDrawable(R.drawable.eye_close));
            } else {
                ivEye.setImageDrawable(getResources().getDrawable(R.drawable.eye_close));
            }
        }
    }


    private void attemptLogin() {
        if (!verification()) {
            showToast("error");
        }
        login();
    }

    /**
     * @return true if username and pwd is ok
     */
    private boolean verification() {
        if (InputVerification.isNull(name) || InputVerification.isNull(pwd))
            return false;
        return true;
    }

    private Map<String, String> getLoginInfo() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("UserName", name.getText().toString());
        map.put("PassWord", pwd.getText().toString());
        return map;
    }

    AsyncTask asyncTask;

    private void login() {
        asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    return WebserviceHelper.GetWebService(
                            "Login", "login", getLoginInfo());
                } catch (ConnectException e) {
                    e.printStackTrace();
                    return "-1";
                }
            }

            @Override
            protected void onPostExecute(String s) {
                pb.setVisibility(View.INVISIBLE);
                if (s == null || "-1".equals(s)) {
                    Log.i(TAG, "onPostExecute: " + s);
                } else {
                    Log.i(TAG, "onPostExecute: " + s);
                    LoginResultMessage loginResult = new Gson().fromJson(s, LoginResultMessage.class);
                    Log.i(TAG, "onPostExecute: " + loginResult.getResult());
                    writerUserToFile(loginResult.getResult());

                }
            }
        }.execute();
    }


    private void writerUserToFile(User user) {
        Log.i(TAG, "writerUserToFile: " + user.toString());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(UserSharedField.USERID, user.getUser_id());
        editor.putString(UserSharedField.ROLEID, user.getRole_id());
        editor.putString(UserSharedField.AREAID, user.getArea_id());
        editor.putString(UserSharedField.REALNAME, user.getReal_name());

        editor.apply();
        editor.commit();
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.name:
                if (hasFocus && !TextUtils.isEmpty(name.getText())) {
                    ivNameClose.setVisibility(View.VISIBLE);
                } else {
                    ivNameClose.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.pwd:
                if (hasFocus && !TextUtils.isEmpty(pwd.getText())) {
                    ivEye.setVisibility(View.VISIBLE);
                } else {
                    ivEye.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (asyncTask != null && !asyncTask.isCancelled()) {
            asyncTask.cancel(true);
        }
    }
}
