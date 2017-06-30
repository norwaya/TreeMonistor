package com.xabaizhong.treemonistor.service;

/**
 * Created by admin on 2017/3/7.
 */

import android.content.Context;
import android.location.LocationManager;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.BDNotifyListener;//假如用到位置提醒功能，需要import该类
import com.baidu.location.Poi;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * 单例  定位服务只有一个实例
 */
public class LocService {
    private static  LocService locService;

    private LocService(Context context) {
        this.context = context;
    }

    public static LocService instance(Context context) {
        if (locService == null)
            synchronized (LocService.class) {
                if (locService == null)
                    locService = new LocService(context);
            }
        return locService;

    }

    public void release() {

        this.context = null;
    }

    private Context context;

}
