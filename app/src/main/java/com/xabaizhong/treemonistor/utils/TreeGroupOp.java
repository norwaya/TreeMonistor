package com.xabaizhong.treemonistor.utils;

import android.util.Log;

import com.xabaizhong.treemonistor.service.ApiService;
import com.xabaizhong.treemonistor.service.RetrofitUtil;
import com.xabaizhong.treemonistor.service.model.ResultMessage;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by admin on 2017/3/16.
 */

public class TreeGroupOp {
    String TAG = "OP";
    private static TreeGroupOp treeGroupOp = null;

    private TreeGroupOp() {

    }

    public static TreeGroupOp Instance() {
        if (treeGroupOp == null)
            synchronized (TreeGroupOp.class) {
                if (treeGroupOp == null)
                    treeGroupOp = new TreeGroupOp();
            }
        return treeGroupOp;
    }

    String json;

    public TreeGroupOp setJson(String strJson) {
//        json = RequestBody.create(MediaType.parse("text/plain"), strJson);
        json = strJson;

        return this;
    }

    Map<String, RequestBody> map;

    public TreeGroupOp setFiles(List<File> list) {
//        packFile(list);
        return this;
    }

    private void packFile(List<File> list) {
        if (list == null)
            return;
        map = new HashMap<>();
        for (File file : list
                ) {
            if (file.getName().equals(".nomedia"))
                continue;
            RequestBody body = RequestBody.create(MediaType.parse("image/png"), file);
            map.put("photos\"; filename=\"icon.png", body);
        }

    }


//    public void op() {
//
//        RetrofitUtil.instance().create(ApiService.class).urlAndJson("pro01/upload.do", json)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Observer<ResultMessage>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(ResultMessage value) {
//                        Log.d(TAG, "onNext: " + value.getMessage());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e(TAG, "onError: ", e);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG, "onComplete: ");
//                    }
//                });
//    }


}
