package com.xabaizhong.treemonistor.base;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import com.baidu.mapapi.SDKInitializer;
import com.xabaizhong.treemonistor.entity.DaoMaster;
import com.xabaizhong.treemonistor.entity.DaoSession;
import com.xabaizhong.treemonistor.service.LocationService;

import org.greenrobot.greendao.database.Database;

/**
 * Created by admin on 2017/3/4.
 */

public class App extends Application {

    public static final boolean ENCRYPTED = false;

    private DaoSession daoSession;

    public LocationService locationService;
    public Vibrator mVibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());

        // green dao init
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }
}
