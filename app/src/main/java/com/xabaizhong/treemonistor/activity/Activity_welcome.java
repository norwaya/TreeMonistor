package com.xabaizhong.treemonistor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

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

public class Activity_welcome extends Activity_base {
    @BindView(R.id.activity_welcome_iv)
    ImageView activityWelcomeIv;
    @BindView(R.id.activity_welcome_btn)
    Button btn;

    final static int SECOND = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        init();
        initImage();

    }

    private void init() {
        btn.setText(3 + "");
    }

    private void initImage() {

        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {
                btn.setText(getString(R.string.welcome_hint,value));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                startActivity(new Intent(Activity_welcome.this, Activity_main.class));
                finish();
            }
        };
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {

//                        getApplicationContext().getFilesDir().listFiles();
                        e.onNext(SECOND);
                        for (int i = SECOND; i > 0; i--) {
                            Thread.sleep(1000);
                            e.onNext(i - 1);
                        }
                        e.onComplete();

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);

    }

    @OnClick(R.id.activity_welcome_btn)
    public void onClick() {

    }

    @Override
    public void onBackPressed() {
    }
}
