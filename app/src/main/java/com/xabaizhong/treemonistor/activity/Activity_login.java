package com.xabaizhong.treemonistor.activity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 2017/3/3.
 */

public class Activity_login extends Activity_base implements View.OnFocusChangeListener {

    @BindView(R.id.center)
    RelativeLayout center;
    @BindView(R.id.area)
    EditText area;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.pwd)
    EditText pwd;
    @BindView(R.id.layout)
    RelativeLayout layout;
    @BindView(R.id.iv_area_close)
    ImageView ivAreaClose;
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

    Observer<Object> observer;

    private void initObserver() {
        observer = new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object value) {
                showToast("login suc");
                pb.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Activity_login.this.finish();
            }
        };

    }

    private void initView() {
        pb.setVisibility(View.INVISIBLE);

        ivAreaClose.setVisibility(View.INVISIBLE);
        ivNameClose.setVisibility(View.INVISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivEye.setImageDrawable(getDrawable(R.drawable.eye_close));
        } else {
            ivEye.setImageDrawable(getResources().getDrawable(R.drawable.eye_close));
        }


        area.setOnFocusChangeListener(this);
        pwd.setOnFocusChangeListener(this);
        name.setOnFocusChangeListener(this);
    }

    @OnClick({R.id.iv_area_close, R.id.iv_name_close, R.id.iv_eye, R.id.btn_login})
    public void onClick(View view) {
        Log.i(TAG, "onClick: ");
        switch (view.getId()) {
            case R.id.iv_area_close:
                area.setText("");
                break;
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
                pwd.postInvalidate();
                break;
            case R.id.btn_login:
                pb.setVisibility(View.VISIBLE);
                login();
                break;
        }
    }

    private void login() {
        if(!verification()){
            showToast("error");
        }

        Observable
                .create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(ObservableEmitter<Object> e) throws Exception {
                        Thread.sleep(2000);
                        e.onNext(0);
                        Thread.sleep(1000);
                        e.onComplete();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    private boolean verification() {
        return true;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.area:
                if (hasFocus && !TextUtils.isEmpty(area.getText())) {
                    ivAreaClose.setVisibility(View.VISIBLE);
                }else{
                    ivAreaClose.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.name:
                if (hasFocus && !TextUtils.isEmpty(name.getText())) {
                    ivNameClose.setVisibility(View.VISIBLE);
                }else{
                    ivNameClose.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.pwd:
                if (hasFocus && !TextUtils.isEmpty(pwd.getText())) {
                    ivEye.setVisibility(View.VISIBLE);
                }else{
                    ivEye.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }
}
