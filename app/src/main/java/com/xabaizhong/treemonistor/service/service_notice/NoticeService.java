package com.xabaizhong.treemonistor.service.service_notice;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.Activity_login2;
import com.xabaizhong.treemonistor.activity.Activity_main;
import com.xabaizhong.treemonistor.activity.Activity_welcome;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 2017/3/21.
 */

public class NoticeService extends Service {
    String TAG = "notice-service";
    TimerTask task;
    Timer timer;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                observable.subscribe(observer);
            }
        };
    }


    void init() {
        observer = new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Long value) {
                Log.i(TAG, "onBind: service run \t" + value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
            }
        };
        observable = Observable
                .create(new ObservableOnSubscribe<Long>() {
                    @Override
                    public void subscribe(ObservableEmitter<Long> e) throws Exception {
                        e.onNext(System.nanoTime());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: service -> onbind");
        run();
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        stop();
        return super.onUnbind(intent);
    }


    Observer<Long> observer;
    Observable<Long> observable;


    private void run() {

        timer.schedule(task,1000, 30000);
//        observable.subscribe(observer);
    }

    private void stop() {
        if (timer != null)
            timer.cancel();
    }

    @Override
    public void onDestroy() {
        stop();
        super.onDestroy();

    }
}
