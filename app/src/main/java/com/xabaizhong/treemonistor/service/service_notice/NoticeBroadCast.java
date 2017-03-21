package com.xabaizhong.treemonistor.service.service_notice;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import static android.content.Context.BIND_AUTO_CREATE;
import static com.xabaizhong.treemonistor.service.service_notice.NoticeBroadCast.ReceiveConstant.BIND_SERVICE;
import static com.xabaizhong.treemonistor.service.service_notice.NoticeBroadCast.ReceiveConstant.RECEIVER_ACTION;
import static com.xabaizhong.treemonistor.service.service_notice.NoticeBroadCast.ReceiveConstant.RECEIVER_INTENT;
import static com.xabaizhong.treemonistor.service.service_notice.NoticeBroadCast.ReceiveConstant.SERVICE_ACTION;
import static com.xabaizhong.treemonistor.service.service_notice.NoticeBroadCast.ReceiveConstant.UNBIND_SERVICE;

/**
 * Created by admin on 2017/3/21.
 */

public class NoticeBroadCast extends BroadcastReceiver {
    String TAG = "NoticeBroadCast";
    Context context;
    boolean first = true;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (first) {
            initSercice();
            first = false;
        }
        if (intent.getAction().equals(RECEIVER_ACTION)) {
            this.context = context;
            String str = intent.getStringExtra(RECEIVER_INTENT);
            switch (str) {
                case BIND_SERVICE:
                    bindService();
                    break;
                case UNBIND_SERVICE:
                    break;
            }
        }
    }

    Intent serviceIntent;
    ServiceConnection serviceConn;

    private void initSercice() {
        serviceIntent = new Intent();
        serviceIntent.setAction(SERVICE_ACTION);
        serviceConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i(TAG, "onServiceConnected: ");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.i(TAG, "onServiceDisconnected: ");
            }
        };
    }

    private void bindService() {
        context.bindService(serviceIntent, serviceConn, BIND_AUTO_CREATE);
    }
    private void unbindService() {
        context.unbindService(serviceConn);
    }

    public interface ReceiveConstant {
        String SERVICE_ACTION = "monitor-service";
        String RECEIVER_ACTION = "monitor-receiver";
        String RECEIVER_INTENT = "receiver-intent";
        String BIND_SERVICE = "bind-service";
        String UNBIND_SERVICE = "unbind-service";

    }
}
