package com.xabaizhong.treemonistor.service.service_notice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.Activity_login;
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

import static android.media.RingtoneManager.getDefaultUri;

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

                notice();
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

    private static final int NOTIFICATION_ID_LIVE = 832;
    private static final int NOTIFICATION_ID = 832;
    int messageNum = 0;

    private void notice() {/*
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(getApplicationContext(), Activity_login.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        Notification.Builder mBuilder = new Notification.Builder(this);

        String title = "Push测试";
        mBuilder.setContentTitle(title)
                .setTicker(title)
                .setNumber(++messageNum)
                .setContentText("https://233.tv/over140")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.drawable.function_expressed)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(PendingIntent
                        .getBroadcast(
                                getApplicationContext(),
                                NOTIFICATION_ID_LIVE,
                                intent,
                                PendingIntent.FLAG_UPDATE_CURRENT))
                .setDeleteIntent(PendingIntent
                        .getBroadcast(getApplicationContext(), NOTIFICATION_ID_LIVE, new Intent(intent), 0));

        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();
        String[] events = new String[6];
// Sets a title for the Inbox in expanded layout
        inboxStyle.setBigContentTitle("Event tracker details:");
        inboxStyle.setSummaryText("summary-text");

// Moves events into the expanded layout
        for (int i = 0; i < events.length; i++) {
            inboxStyle.addLine(events[i]);
            inboxStyle.setSummaryText("summary-text" + i);
            inboxStyle.setBigContentTitle("summary-title" + i);
        }
// Moves the expanded layout object into the notification object.
        mNotificationManager.notify(NOTIFICATION_ID_LIVE, mBuilder.build());*/
        int requestID = (int) System.currentTimeMillis();
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Uri alarmSound = getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mNotificationManager = (NotificationManager) getApplication().getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(getApplicationContext(), Activity_login.class);

//**add this line**
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

//**edit this line to put requestID as requestCode**
        PendingIntent contentIntent = PendingIntent.getActivity(this, requestID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
        mBuilder.setSmallIcon(R.drawable.ic_fragment_expert_bug)
                .setContentTitle("My Notification")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("big text"))
                .setContentText("").setAutoCancel(true);
        mBuilder.setSound(alarmSound);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }


    Observer<Long> observer;
    Observable<Long> observable;


    private void run() {

        timer.schedule(task, 1000, 3000);
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
        Log.i(TAG, "onDestroy: service ");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        run();
        return super.onStartCommand(intent, flags, startId);
    }
}
