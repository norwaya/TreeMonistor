package com.xabaizhong.treemonistor.service.service_notice;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by admin on 2017/3/21.
 */

public class NoticeService extends Service {
    String TAG = "notice-service";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: service -> onbind");
        return null;
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        Log.i(TAG, "unbindService: service-> unbind service");
    }
}
