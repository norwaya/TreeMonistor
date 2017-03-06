package com.xabaizhong.treemonistor.base;

import android.app.Application;

import com.xabaizhong.treemonistor.entity.DaoMaster;
import com.xabaizhong.treemonistor.entity.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by admin on 2017/3/4.
 */

public class App extends Application {

    public static final boolean ENCRYPTED = false;

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
