package com.xabaizhong.treemonistor.activity;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.xabaizhong.treemonistor.myview.ProgressDialogUtil;
import com.xabaizhong.treemonistor.service.AsyncTaskRequest;
import com.xabaizhong.treemonistor.service.WebserviceHelper;
import com.xabaizhong.treemonistor.service.model.User;
import com.xabaizhong.treemonistor.service.response.LoginResultMessage;
import com.xabaizhong.treemonistor.utils.InputVerification;


import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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

        initObserver();
        initView();
    }


    private void initObserver() {

    }

    private void initView() {
        name.setText("610102001");
        pwd.setText("admin");
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

    AsyncTaskRequest asyncTaskRequest = null;
    Dialog dialog;

    private void attemptLogin() {
        dialog = ProgressDialogUtil.getInstance(this).initial("login...", new ProgressDialogUtil.CallBackListener() {
            @Override
            public void callBack(DialogInterface dialog) {
                if (asyncTaskRequest != null) {
                    asyncTaskRequest.cancel();
                    asyncTaskRequest = null;
                }
            }
        });
        asyncTaskRequest = AsyncTaskRequest.instance("Login", "login").setParams(getLoginInfo())
                .setCallBack(new AsyncTaskRequest.CallBack() {
                    @Override
                    public void execute(String s) {
                        Log.i(TAG, "execute: " + s);
                        asyncTaskRequest = null;
                        cancelDialog(dialog);
                        if (s == null) {
                            showToast("请检查您的网络");
                            return;
                        }
                        Observable.just(s)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe(new Consumer<String>() {
                                    @Override
                                    public void accept(String s) throws Exception {
                                        LoginResultMessage loginResult = new Gson().fromJson(s, LoginResultMessage.class);
                                        if (loginResult.getError_code() == 0) {
                                            writerUserToFile(loginResult.getResult());
                                            startActivity(new Intent(Activity_login.this, Activity_main.class));

                                        }
                                        showToast(loginResult.getMessage());
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        showToast("解析失败");
                                    }
                                });
                    }
                }).

                        create();

    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed: pressed");
        if (dialog != null) {
            dialog.dismiss();
            Log.i(TAG, "onBackPressed: cancel dialog");
            dialog = null;
        } else {

            super.onBackPressed();
        }


    }
    private void cancelDialog(Dialog dialog){
        if (dialog != null) {
            dialog.dismiss();
            Log.i(TAG, "onBackPressed: cancel dialog");
            dialog = null;
        }
    }
    /**
     * @return true if username and pwd is ok
     */
    private boolean verification() {
        if (InputVerification.isNull(name) || InputVerification.isNull(pwd))
            return false;
        return true;
    }

    private Map<String, Object> getLoginInfo() {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("UserID", name.getText().toString());
        map.put("Password", pwd.getText().toString());

        return map;
    }

   /* AsyncTask asyncTask;

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
                    Log.i(TAG, "onPostExecute: " + loginResult.getMessage());
                    if (loginResult.getError_code() == 0) {
                        writerUserToFile(loginResult.getResult());
                        finish();
                    }

                }
            }
        }.execute();
    }
*/

    private void writerUserToFile(User user) {
        Log.i(TAG, "writerUserToFile: " + user.toString());

        Set<String> set = new HashSet<>();
        set.addAll(user.getRoleID());

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(UserSharedField.USERID, user.getUserID());
        editor.putStringSet(UserSharedField.ROLEID, set);
        editor.putString(UserSharedField.AREAID, user.getAreaID());
        editor.putString(UserSharedField.REALNAME, user.getRealName());

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
        if (asyncTaskRequest != null) {
            asyncTaskRequest.cancel();
        }
    }
}
