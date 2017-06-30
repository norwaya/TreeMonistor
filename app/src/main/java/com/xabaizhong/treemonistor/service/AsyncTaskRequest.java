package com.xabaizhong.treemonistor.service;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.service.model.SpeciesResult;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  封装的AsyncTaskRequest ，回调接口 只需要 穿进去 callback 方法即可；
 */
public class AsyncTaskRequest {

    private String interfaceName;
    private String methodName;


    public static AsyncTaskRequest instance(String interfaceName, String methodName) {
        AsyncTaskRequest a = new AsyncTaskRequest();
        a.setInterfaceName(interfaceName);
        a.setMethodName(methodName);
        return a;
    }

    private AsyncTask asyncTask = null;
    private Map<String, Object> map;
    public AsyncTaskRequest create(String tag) {
        Log.i("AsyncTaskRequest", "create: "+tag);
        return create();
    }

    /**
     * 把webservice  请求 使用 AsyncTaskRequest 封装起来
     * @return
     */
    public AsyncTaskRequest create() {
        if (callBack == null) {
            throw new RuntimeException("callBack interface is not implemented");
        }
        asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    return WebserviceHelper.GetWebService(
                            interfaceName, methodName, map);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                if (callBack != null) {
                    callBack.execute(s);
                }
            }
        }.execute();
        return this;
    }

    private CallBack callBack;

    public AsyncTaskRequest setCallBack(CallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    public AsyncTaskRequest setParams(Map<String, Object> map) {
        this.map = map;
        return this;
    }


    private void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }


    private void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public boolean isCancelled() {
        return asyncTask == null || asyncTask.isCancelled();
    }

    public void cancel() {
        if (asyncTask != null && !asyncTask.isCancelled()) {
            asyncTask.cancel(true);
            asyncTask = null;
        }
    }

    public interface CallBack {
        void execute(String s);
    }
}
